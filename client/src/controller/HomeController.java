package controller;

import model.Client;
import view.Home;

import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class HomeController {
    Thread receive;
    public Home home;
    public String username;
    public int balance;
    Client client;
    public DataOutputStream dos;
    public DataInputStream dis;
    TransferController transfer;
    RechargeController recharge;
    WithdrawController withdraw;
    ChangePasswordController changePassword;

    public HomeController(String username, int balance) {
        this.username = username;
        this.balance = balance;
        init();
    }

    public HomeController(Client client, String username, int balance, DataOutputStream dos, DataInputStream dis) throws Exception {
        this.username = username;
        this.balance = balance;
        this.dos = dos;
        this.dis = dis;
        this.client = client;
        receive = new Thread(new Receive(client, username, dos, dis, this));
        receive.start();
        init();
    }

    public void updateBalance(int money) {
        home.lblBalance.setText("Balance: " + money + "$");
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setVisible(boolean b) {
        home.setVisible(b);
    }

    public void init() {
        home = new Home();
        home.setVisible(true);
        home.lblUsername.setText("Username: " + username);
        home.lblBalance.setText("Balance: " + balance + "$");

        home.frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    dos.writeUTF("Log out");
                    dos.flush();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        home.btnChangePass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePassword = new ChangePasswordController(HomeController.this, dos, dis);
                changePassword.setVisible(true);
            }
        });

        home.btnWithdraw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(() -> {
                    withdraw = new WithdrawController(HomeController.this, dos, dis);
                    withdraw.setVisible(true);
                }).start();
            }
        });

        home.btnTransfer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(() -> {
                    transfer = new TransferController(dos, dis, HomeController.this);
                    transfer.setVisible(true);
                }).start();
            }
        });

        home.btnRecharge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(() -> {
                    recharge = new RechargeController(dos, dis);
                    recharge.setVisible(true);
                }).start();
            }
        });
    }

    public static void main(String[] args) {
        new HomeController("hoan", 1000);
    }
}
