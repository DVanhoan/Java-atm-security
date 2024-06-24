package controller;

import model.Client;

import javax.swing.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Receive implements Runnable {
    public DataInputStream dis;
    public DataOutputStream dos;
    public HomeController home;
    static String username;
    Client client;
    int count;

    public Receive(Client client ,String username, DataOutputStream dos, DataInputStream dis , HomeController home) throws IOException {
        Receive.username = username;
        this.dos = dos;
        this.dis = dis;
        this.client = client;
        this.home = home;
    }

    public void start() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            while (true) {
                String request = dis.readUTF();
                switch (request) {
                    case "Safe to leave":
                        SwingUtilities.invokeLater(() -> {
                            JOptionPane.showMessageDialog(null, "Safe to leave");
                            home.setVisible(false);
                            client.disconnect();
                            new Thread(() -> {
                                client = new Client(client.getIp(), client.getPort());
                                if (client.connect()){
                                    new LoginController(client);
                                }
                                else {
                                    System.out.println("Failed to connect to server");
                                }
                            }).start();
                        });
                        return;

                    case "withdraw success":
                        int balance = dis.readInt();
                        SwingUtilities.invokeLater(() -> {
                            home.updateBalance(balance);
                            JOptionPane.showMessageDialog(home.home, "Withdraw success\nBalance: " + balance);
                            home.withdraw.setVisible(false);
                        });
                        break;

                    case "receive money":
                        String sender = dis.readUTF();
                        int money = Integer.parseInt(dis.readUTF());
                        SwingUtilities.invokeLater(() -> {
                            home.setBalance(home.getBalance() + money);
                            home.updateBalance(home.getBalance());
                            JOptionPane.showMessageDialog(home.home, sender + " sent you: " + money);
                        });
                        break;

                    case "send success":
                        int newBalance = dis.readInt();
                        String recipient = dis.readUTF();
                        SwingUtilities.invokeLater(() -> {
                            home.updateBalance(newBalance);
                            JOptionPane.showMessageDialog(home.home, "Send success\nBalance: " + newBalance + "\nRecipient: " + recipient);
                            home.transfer.setVisible(false);
                        });
                        break;

                    case "Transfer failed: Receiver not found.":
                        SwingUtilities.invokeLater(() -> {
                            JOptionPane.showMessageDialog(home.home, "Transfer failed: Receiver not found.");
                        });
                        break;

                    case "recharge success":
                        int nbalance = dis.readInt();
                        SwingUtilities.invokeLater(() -> {
                            home.updateBalance(nbalance);
                            JOptionPane.showMessageDialog(home.home, "Recharge success \nBalance: " + nbalance);
                            home.recharge.setVisible(false);
                        });
                        break;

                    case "Invalid amount: Amount must be greater than zero.":
                        JOptionPane.showMessageDialog(home.home, "Amount must be greater than zero.");
                        break;

                    case "change password success":
                        home.changePassword.setVisible(false);
                        int result = JOptionPane.showConfirmDialog(home.home, "Change password success \nPlease login again");
                        if (result == JOptionPane.OK_OPTION) {
                            home.setVisible(false);
                            client.disconnect();
                            new Thread(() -> {
                                client = new Client(client.getIp(), client.getPort());
                                if (client.connect()){
                                    new LoginController(client);
                                }
                                else {
                                    System.out.println("Failed to connect to server");
                                }
                            }).start();
                            return;
                        }
                        break;

                        
                    case "change password failed":
                        count++;
                        if (count > 3){
                            JOptionPane.showMessageDialog(home.home, "Wrong password. Safe to leave");
                            home.changePassword.setVisible(false);
                            home.setVisible(false);
                            client.disconnect();
                            new Thread(() -> {
                                client = new Client(client.getIp(), client.getPort());
                                if (client.connect()){
                                    new LoginController(client);
                                }
                                else {
                                    System.out.println("Failed to connect to server");
                                }
                            }).start();
                            return;
                        }
                        else {
                            JOptionPane.showMessageDialog(home.home, "Wrong password. \n you have " + (3 - count) + " more try");
                        }
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
