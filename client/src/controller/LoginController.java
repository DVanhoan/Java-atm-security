package controller;

import model.Client;
import utils.RSAHash;
import view.Home;
import view.LoginForm;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import javax.swing.*;

public class LoginController {
    public Client client;
    private LoginForm loginFormGUI;
    DataOutputStream dos;
    DataInputStream dis;

    public LoginController(Client client) {
        this.client = client;
        this.dos = client.getDos();
        this.dis = client.getDis();
        this.init();
    }

    public void init() {
        loginFormGUI = new LoginForm();
        loginFormGUI.init();
        client.connect();
        loginFormGUI.btnLogin.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String username = loginFormGUI.txtUsername.getText();
                String password = loginFormGUI.txtPassword.getText();

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter all information!");
                } else {
                    try {
                        String result = login(username, password);
                        if (result.equals("Log in success")) {
                            JOptionPane.showMessageDialog(null, result);
                            int balance = dis.readInt();
                            System.out.println("Balance: " + balance);

                            EventQueue.invokeLater(() -> {
                                try {
                                    loginFormGUI.setVisible(false);
                                    HomeController home = new HomeController(client, username, balance, dos, dis);
                                    home.setVisible(true);
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            });
                            loginFormGUI.jFrame.dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, result, "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        loginFormGUI.btnSignUp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loginFormGUI.setVisible(false);
                new Thread(() -> new RegisterController(client)).start();
            }
        });

        loginFormGUI.chkShowPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (loginFormGUI.chkShowPassword.isSelected()) {
                    loginFormGUI.txtPassword.setEchoChar((char) 0);
                } else {
                    loginFormGUI.txtPassword.setEchoChar('*');
                }
            }
        });
    }

    public void setVisible(boolean b) {
        loginFormGUI.setVisible(b);
    }

    public String login(String username, String password) {
        try {
            if (!client.connect()) {
                return "Network error: Unable to connect to server.";
            }
            dos.writeUTF("Log in");
            dos.writeUTF(RSAHash.encrypt(username));
            dos.writeUTF(RSAHash.encrypt(password));
            dos.flush();
            return dis.readUTF();
        } catch (IOException e) {
            System.err.println("IO Exception occurred: " + e.getMessage());
            e.printStackTrace();
            return "Network error: Unable to connect to server.";
        } catch (Exception e) {
            System.err.println("Exception occurred: " + e.getMessage());
            e.printStackTrace();
            return "Error: Login failed due to unexpected error.";
        }
    }
}
