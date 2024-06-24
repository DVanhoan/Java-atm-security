package view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EnterTransfer {
    public JFrame frame;
    public JLabel lblEnterAccount;
    public JTextField txtEnterAccount;
    public JLabel lblEnterMoney;
    public JTextField txtEnterMoney;
    public JButton btnEnter;
    public EnterTransfer() {
        init();
    }

    public void init() {
        frame = new JFrame("Transfer Money");
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(getContentPane());
        frame.setVisible(true);
    }


    public JPanel getContentPane() {
        JPanel mainPanal = new JPanel();
        mainPanal.setLayout(new GridLayout(2, 1));

        lblEnterAccount = new JLabel("Enter Account");
        txtEnterAccount = new JTextField("");

        lblEnterMoney = new JLabel("Enter Money");
        txtEnterMoney = new JTextField("");

        btnEnter = new JButton("Transfer");


        Font font = new Font("Arial", Font.BOLD, 14);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2));
        inputPanel.add(lblEnterAccount);
        inputPanel.add(txtEnterAccount);
        inputPanel.add(lblEnterMoney);
        inputPanel.add(txtEnterMoney);

        lblEnterAccount.setFont(font);
        txtEnterAccount.setFont(font);
        lblEnterMoney.setFont(font);
        txtEnterMoney.setFont(font);
        btnEnter.setFont(font);

        mainPanal.add(inputPanel);
        mainPanal.add(btnEnter);

        return mainPanal;
    }

    public void setVisible(boolean b) {
        frame.setVisible(b);
    }

    public static void main(String[] args) {
        new EnterTransfer();
    }
}