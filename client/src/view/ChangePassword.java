package view;

import controller.ChangePasswordController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static controller.ChangePasswordController.createVisibilityToggleButton;

public class ChangePassword {
    public JFrame frame;
    public JLabel lblOldPassword;
    public JPasswordField txtOldPassword;
    public JToggleButton btnToggleOldPassword;
    public JLabel lblPassword;
    public JPasswordField txtPassword;
    public JToggleButton btnTogglePassword;
    public JLabel lblPasswordConfirm;
    public JPasswordField txtPasswordConfirm;
    public JToggleButton btnTogglePasswordConfirm;
    public JButton btnChange;

    public ChangePassword() {
        init();
    }

    private void init() {
        frame = new JFrame("Change Password");
        frame.setSize(350, 200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));
        frame.add(getContentPane(), BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private JPanel getContentPane() {
        JPanel mainPanel = new JPanel(new BorderLayout(5, 5));

        JPanel inputPanel = new JPanel(new GridLayout(3, 3, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        lblOldPassword = new JLabel("Old Password:");
        txtOldPassword = new JPasswordField(10);
        btnToggleOldPassword = createVisibilityToggleButton(txtOldPassword);
        lblPassword = new JLabel("New Password:");
        txtPassword = new JPasswordField(10);
        btnTogglePassword = createVisibilityToggleButton(txtPassword);
        lblPasswordConfirm = new JLabel("Confirm Password:");
        txtPasswordConfirm = new JPasswordField(10);
        btnTogglePasswordConfirm = createVisibilityToggleButton(txtPasswordConfirm);

        addRow(inputPanel, lblOldPassword, txtOldPassword, btnToggleOldPassword);
        addRow(inputPanel, lblPassword, txtPassword, btnTogglePassword);
        addRow(inputPanel, lblPasswordConfirm, txtPasswordConfirm, btnTogglePasswordConfirm);

        btnChange = new JButton("Change");
        btnChange.setFont(new Font("Arial", Font.BOLD, 14));
        btnChange.setBorder(BorderFactory.createEmptyBorder(7, 5, 7, 5));

        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(btnChange, BorderLayout.SOUTH);

        return mainPanel;
    }

    private void addRow(JPanel panel, JLabel label, JPasswordField passwordField, JToggleButton toggleButton) {
        label.setFont(new Font("Arial", Font.BOLD, 12));
        passwordField.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(label);
        panel.add(passwordField);
        panel.add(toggleButton);
    }


    public void setVisible(boolean b) {
        frame.setVisible(b);
    }

    public static void main(String[] args) {
        new ChangePassword();
    }
}
