package view;

import javax.swing.*;


import java.awt.*;

public class Home extends JFrame {
    public JFrame frame;
    public JPanel mainPanel, panelInfo, panelNorth, panelHeader, panelButton;
    public JLabel lblUsername, lblBalance, lblHello;
    public JButton btnWithdraw, btnTransfer, btnRecharge, btnChangePass;

    public Home() {
        init();
    }

    public void init(){
        frame = new JFrame();
        frame.setTitle("ATM");
        frame.setSize(400, 500);
        frame.add(getContainer());
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public JPanel getContainer() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 1));

        Font font = new Font("Arial", Font.BOLD, 18);
        Font font2 = new Font("Arial", Font.PLAIN, 13);

        panelNorth = new JPanel();
        panelNorth.setLayout(new BorderLayout());

        panelHeader = new JPanel();
        panelHeader.setLayout(new GridLayout(2, 1));
        panelHeader.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        lblHello = new JLabel("Hello, welcome to Hoan ATM", JLabel.CENTER);

        panelInfo = new JPanel();
        panelInfo.setLayout(new GridLayout(2, 1));
        panelInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        lblUsername = new JLabel("Username: ");
        lblBalance = new JLabel("Balance: ");


        panelInfo.add(lblUsername);
        panelInfo.add(lblBalance);


        panelHeader.add(lblHello);
        panelNorth.add(panelHeader, BorderLayout.CENTER);
        panelNorth.add(panelInfo, BorderLayout.SOUTH);



        panelButton = new JPanel();
        panelButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelButton.setLayout(new GridLayout(2, 2, 5, 5));

        btnWithdraw = new JButton("Withdraw");
        btnTransfer = new JButton("Transfer");
        btnRecharge = new JButton("Recharge");
        btnChangePass = new JButton("Change Password");


        btnWithdraw.setFont(font2);
        btnTransfer.setFont(font2);
        btnRecharge.setFont(font2);
        btnChangePass.setFont(font2);
        lblBalance.setFont(font);
        lblUsername.setFont(font);
        lblHello.setFont(font);


        btnWithdraw.setIcon(new ImageIcon("src\\image\\rut.png"));
        btnTransfer.setIcon(new ImageIcon("src\\image\\gui.png"));
        btnRecharge.setIcon(new ImageIcon("src\\image\\nap.png"));
        btnChangePass.setIcon(new ImageIcon("src\\image\\pass.png"));

        panelButton.add(btnWithdraw);
        panelButton.add(btnTransfer);
        panelButton.add(btnRecharge);
        panelButton.add(btnChangePass);


        mainPanel.add(panelNorth, BorderLayout.NORTH);
        mainPanel.add(panelButton, BorderLayout.CENTER);
        return mainPanel;
    }

    public void setVisible(boolean b) {
        frame.setVisible(b);
    }

    public JLabel getLblBalance() {
        return lblBalance;
    }

    public static void main(String[] args) {
        Home home = new Home();
        home.setVisible(true);
    }
}
