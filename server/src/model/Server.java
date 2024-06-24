package model;

import configs.Configs;
import service.UserService;
import utils.Util;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import static utils.RSAHash.decrypt;


public class Server{
    private ServerSocket serverSocket = null;
    private Object lock;
    public static ArrayList<Handle> clients = new ArrayList<>();

    public Server(int port) throws Exception {

        try {
            lock = new Object();
            this.loadData();
            serverSocket = new ServerSocket(port);
            System.out.println("Server started at: " + Util.getIPv4());

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println(socket.getInetAddress().getHostAddress() + " connected");

                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                DataInputStream dis = new DataInputStream(socket.getInputStream());


                String request = dis.readUTF();

                if (request.equals("Sign up")) {
                    String username = dis.readUTF();
                    String password = dis.readUTF();
                    int cost = dis.readInt();

                    User user = new User();
                    user.setUsername(username);
                    user.setPassword(password);
                    user.setCost(cost);

                    String password_decrypted = decrypt(password);

                    if(!isExistUser(username)) {
                        boolean check = UserService.insertUser(user).isCheck();
                        if (check) {
                            Handle newHandler = new Handle(socket, username, password_decrypted, cost,false, lock);
                            clients.add(newHandler);

                            dos.writeUTF("Sign up success");
                            dos.flush();

                            this.loadData();
                        }
                        else {
                            dos.writeUTF("Sign up failed");
                            dos.flush();
                        }
                    }else {
                        dos.writeUTF("Username is already exist");
                        dos.flush();
                    }
                }

                if (request.equals("Log in")) {
                    String username_hash = dis.readUTF();
                    String password_hash = dis.readUTF();

                    String username = decrypt(username_hash);
                    String password = decrypt(password_hash);

                    if (isExistUser(username)){
                        for (Handle handler : clients) {
                            if (handler.getUsername().equals(username)) {
                                if (handler.getPassword().equals(password)) {
                                    handler.setSocket(socket);
                                    handler.setLogin(true);
                                    handler.setBalance(handler.getBalance());

                                    dos.writeUTF("Log in success");
                                    dos.flush();

                                    System.out.println("Log in success: " + username);

                                    dos.writeInt(handler.getBalance());
                                    dos.flush();

                                    Thread thread = new Thread(handler);
                                    thread.start();

                                }
                                else {
                                    dos.writeUTF("Password is not correct");
                                    dos.flush();
                                }
                                break;
                            }
                        }
                    }
                    else {
                        dos.writeUTF("This username is not exist");
                        dos.flush();
                    }
                }
            }
        } catch (BindException e) {
            System.out.println("Port already in use. Please close the application using this port or use another port.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("An I/O error occurred when opening the socket.");
            e.printStackTrace();
        }
    }

    private boolean isExistUser(String username) {
        for (Handle handler : clients) {
            if (handler.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }




    private void loadData() {
        try {
            ArrayList<User> users = UserService.getAllUser();
            for (User user : users) {
                String username = user.getUsername();
                String password = decrypt(user.getPassword());
                int cost = user.getCost();
                System.out.println("Load user: " + username + " with password: " + password + " and cost: " + cost);
                try {
                    clients.add(new Handle(username, password, cost, false, lock));
                } catch (Exception ex) {
                    System.err.println("Failed to decrypt password for user: " + username);
                    ex.printStackTrace();
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading user data");
            e.printStackTrace();
        }
    }





    public static void main(String[] args) {
        try {
            Server server = new Server(Configs.SERVER_PORT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
