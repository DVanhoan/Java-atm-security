package view;

import javax.swing.*;
import java.awt.*;

public class EnterWithdraw {
    public JFrame frame;
    public JLabel lblEnterMoney;
    public JTextField txtEnterMoney;
    public JButton btnEnter;

    public EnterWithdraw() {
        init();
    }

    private void init() {
        frame = new JFrame("Withdraw Money");
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(getContentPane());
        frame.setVisible(true);
    }

    private JPanel getContentPane() {
        JPanel mainPanal = new JPanel();
        mainPanal.setLayout(new BorderLayout());

    
        lblEnterMoney = new JLabel("Enter Money");
        txtEnterMoney = new JTextField("just digit number > 0");
        btnEnter = new JButton("Enter");

        btnEnter.setBorder(BorderFactory.createEmptyBorder(16, 10, 16, 10));

        Font font = new Font("Arial", Font.BOLD, 14);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2));
        inputPanel.add(lblEnterMoney);
        inputPanel.add(txtEnterMoney);

        lblEnterMoney.setFont(font);
        txtEnterMoney.setFont(font);
        btnEnter.setFont(font);

        mainPanal.add(inputPanel, BorderLayout.CENTER);
        mainPanal.add(btnEnter, BorderLayout.SOUTH);
        return mainPanal;
    }

    public static void main(String[] args) {
        EnterWithdraw enterWithdraw = new EnterWithdraw();
    }

    public void setVisible(boolean b) {
        frame.setVisible(b);
    }

}
