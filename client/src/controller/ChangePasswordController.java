package controller;

import utils.RSAHash;
import view.ChangePassword;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class ChangePasswordController {
    public ChangePassword changePassword;
    public DataInputStream dis;
    public DataOutputStream dos;
    public HomeController home;

    public ChangePasswordController(HomeController home, DataOutputStream dos, DataInputStream dis) {
        this.dos = dos;
        this.home = home;
        this.dis = dis;
        init();
    }

    public void init() {
        changePassword = new ChangePassword();
        changePassword.setVisible(true);

        changePassword.btnChange.addActionListener(e -> {
            String oldPassword = new String(changePassword.txtOldPassword.getPassword());
            String newPassword = new String(changePassword.txtPassword.getPassword());
            String newPasswordConfirm = new String(changePassword.txtPasswordConfirm.getPassword());

            if (oldPassword.isEmpty() || newPassword.isEmpty() || newPasswordConfirm.isEmpty()) {
                JOptionPane.showMessageDialog(changePassword.frame, "Please enter all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!newPassword.equals(newPasswordConfirm)) {
                JOptionPane.showMessageDialog(changePassword.frame, "New password and confirm password do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    dos.writeUTF("Change password");
                    dos.writeUTF(RSAHash.encrypt(oldPassword));
                    dos.writeUTF(RSAHash.encrypt(newPassword));
                    dos.flush();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }



    public static JToggleButton createVisibilityToggleButton(JPasswordField passwordField) {
        JToggleButton toggleButton = new JToggleButton("Show");
        toggleButton.addActionListener(e -> {
            if (toggleButton.isSelected()) {
                passwordField.setEchoChar((char) 0);
                toggleButton.setText("Hide");
            } else {
                passwordField.setEchoChar('*');
                toggleButton.setText("Show");
            }
        });
        return toggleButton;
    }

    public void setVisible(boolean b) {
        changePassword.setVisible(b);
    }
}
