package controller;

import model.Client;
import utils.RSAHash;
import view.RegisterForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class RegisterController {
    private final RegisterForm registerFormGUI;
    Client client1;
    DataOutputStream dos;
    DataInputStream dis;

    public RegisterController(Client client) {
        this.registerFormGUI = new RegisterForm();
        this.registerFormGUI.init();
        this.client1= client;
        this.dos = client.getDos();
        this.dis = client.getDis();
        this.registerFormGUI.btnSignUp.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                try {
                    String username = registerFormGUI.txtUsername.getText();
                    String password = registerFormGUI.txtPassword.getText();
                    int cost = Integer.parseInt(registerFormGUI.txtCost.getText());


                    if (username.isEmpty() || password.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Please enter all information!");
                    } else {
                        String request = register(username, RSAHash.encrypt(password), cost);
                        if (request.equals("Sign up success")) {
                            EventQueue.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    client.disconnect();
                                    try {
                                        JOptionPane.showMessageDialog(null, request);
                                        client1 = new Client(client.getIp(), client.getPort());
                                        if (client1.connect()) {
                                            registerFormGUI.setVisible(false);
                                            new LoginController(client1);
                                        }
                                        else {
                                            JOptionPane.showMessageDialog(null, "Failed to connect to server");
                                        }
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            });
                        } else {
                            JOptionPane.showMessageDialog(null, request);
                        }
                    }
                }catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });


        this.registerFormGUI.btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                new Thread(() -> {
                    registerFormGUI.setVisible(false);
                    new LoginController(client);
                }).start();

            }
        });

        registerFormGUI.chkShowPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (registerFormGUI.chkShowPassword.isSelected()) {
                    registerFormGUI.txtPassword.setEchoChar((char)0);
                }
                else {
                    registerFormGUI.txtPassword.setEchoChar('*');
                }
            }
        });
    }


    public String register(String username, String password, int cost) {
        try{
            dos.writeUTF("Sign up");
            dos.writeUTF(username);
            dos.writeUTF(password);
            dos.writeInt(cost);
            dos.flush();
            return dis.readUTF();
        }catch (Exception e) {
            e.printStackTrace();
            return "Network error: Log in fail";
        }
    }
}
