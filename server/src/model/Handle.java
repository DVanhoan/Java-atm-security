package model;



import service.UserService;
import utils.RSAHash;

import java.io.*;
import java.net.Socket;

class Handle implements Runnable {
    private Socket socket = null;
    private String username;
    private String password;
    private int balance;
    private boolean isLogin;
    private Object lock;
    private DataInputStream dis;
    private DataOutputStream dos;


    public Handle(Socket socket, String username, String password, int balance, boolean isLogin, Object lock) throws IOException {
        this.socket = socket;
        this.username = username;
        this.password = password;
        this.isLogin = isLogin;
        this.balance = balance;
        this.lock = lock;
        this.dis = new DataInputStream(socket.getInputStream());
        this.dos = new DataOutputStream(socket.getOutputStream());
    }

    public Handle(String username, String password, int balance, boolean isLoggedIn, Object lock) {
        this.username = username;
        this.password = password;
        this.isLogin = isLoggedIn;
        this.balance = balance;
        this.lock = lock;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
        try {
            this.dis = new DataInputStream(socket.getInputStream());
            this.dos = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {

        while (true) {
            try {
                if(socket != null){
                    String message = dis.readUTF();

                    if (message.equals("Log out")) {
                        System.out.println(this.username + " log out");
                        this.setLogin(false);
                        dos.writeUTF("Safe to leave");
                        dos.flush();
                        break;
                    }


                    if (message.equals("withdraw money")) {
                        int money = dis.readInt();
                        User user = UserService.checkExitUserByUserName(username).data;

                        System.out.println(username +" withdraw money: " + money);

                        if (money <= 0) {
                            dos.writeUTF("Invalid amount: Amount must be greater than zero.");
                            dos.flush();
                            System.out.println("Invalid amount: Amount must be greater than zero.");
                            return;
                        }

                        int currentBalance = user.getCost();
                        if (money > currentBalance) {
                            System.out.println(currentBalance);
                            dos.writeUTF("Withdrawal failed: Insufficient funds.");
                            dos.flush();
                            System.out.println("Withdrawal failed: Insufficient funds.");
                            return;
                        }
                        UserService.updateUserCost(new User(0, username, password, currentBalance - money));
                        System.out.println(username +" withdraw success" + money);
                        dos.writeUTF("withdraw success");
                        dos.flush();
                        dos.writeInt(currentBalance - money);
                        dos.flush();
                    }


                    if (message.equals("recharge money")) {
                        int money = dis.readInt();
                        User user = UserService.checkExitUserByUserName(username).data;

                        System.out.println(username +" recharge money: " + money);

                        if (money <= 0) {
                            dos.writeUTF("Invalid amount: Amount must be greater than zero.");
                            dos.flush();
                            System.out.println("Invalid amount: Amount must be greater than zero.");
                            return;
                        }

                        int currentBalance = user.getCost();
                        UserService.updateUserCost(new User(0, username, password, currentBalance + money));
                        System.out.println(username + " recharge success" + money);
                        dos.writeUTF("recharge success");
                        dos.flush();
                        dos.writeInt(currentBalance + money);
                        dos.flush();
                    }



                    if (message.equals("Transfer money")) {
                        String username_receiver = dis.readUTF();
                        int money = dis.readInt();
                        User user_sender = UserService.checkExitUserByUserName(username).data;

                        if (money <= 0) {
                            dos.writeUTF("Transfer failed: Invalid amount.");
                            dos.flush();
                            System.out.println("Invalid amount: Amount must be greater than zero.");
                            return;
                        }

                        int senderCurrentBalance = user_sender.getCost();
                        if (money > senderCurrentBalance) {
                            dos.writeUTF("Transfer failed: Insufficient funds.");
                            dos.flush();
                            System.out.println("Transfer failed: Insufficient funds.");
                            return;
                        }

                        User user_receiver = UserService.checkExitUserByUserName(username_receiver).data;
                        if (user_receiver == null) {
                            dos.writeUTF("Transfer failed: Receiver not found.");
                            dos.flush();
                            System.out.println("Transfer failed: Receiver not found.");
                            return;
                        }

                        UserService.updateUserCost(new User(user_sender.getId(), username, password, senderCurrentBalance - money));
                        this.balance = senderCurrentBalance - money;


                        int receiverCurrentBalance = user_receiver.getCost();
                        UserService.updateUserCost(new User(user_receiver.getId(), username_receiver, null, receiverCurrentBalance + money));

                        new Thread(() -> {
                            try {
                                for (Handle handle : Server.clients) {
                                    if (handle.getUsername().equals(username_receiver)) {
                                        handle.getDos().writeUTF("receive money");
                                        handle.getDos().flush();
                                        handle.getDos().writeUTF(username);
                                        handle.getDos().flush();
                                        handle.getDos().writeUTF(String.valueOf(money));
                                        handle.getDos().flush();
                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }).start();

                        dos.flush();
                        dos.writeUTF("send success");
                        dos.flush();
                        dos.writeInt(senderCurrentBalance - money);
                        dos.flush();
                        dos.writeUTF(username_receiver);
                        dos.flush();
                    }


                    if(message.equals("Change password")){
                        String password_hash = dis.readUTF();
                        String newPassword_hash = dis.readUTF();

                        String password = RSAHash.decrypt(password_hash);
                        String newPassword = RSAHash.decrypt(newPassword_hash);

                        if (!password.equals(this.password)) {
                            dos.writeUTF("change password failed");
                            dos.flush();
                            System.out.println("change password failed");
                            return;
                        }else {
                            this.setPassword(newPassword);
                            UserService.updateUserPassword(new User(0, username, newPassword_hash, this.balance));
                            dos.writeUTF("change password success");
                            dos.flush();
                            System.out.println("change password success");
                        }
                    }



                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }






    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public Socket getSocket() {
        return socket;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public Object getLock() {
        return lock;
    }

    public void setLock(Object lock) {
        this.lock = lock;
    }

    public DataInputStream getDis() {
        return dis;
    }

    public void setDis(DataInputStream dis) {
        this.dis = dis;
    }

    public DataOutputStream getDos() {
        return dos;
    }

    public void setDos(DataOutputStream dos) {
        this.dos = dos;
    }
}