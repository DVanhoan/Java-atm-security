package controller;

import view.Admin;
import view.DashBord;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminController {
    public Admin admin;
    public ArrayList<JLabel> labels;
    private JLabel selectedLabel;

    public AdminController() {
        init();
    }

    private void init() {
        admin = new Admin();

        labels = new ArrayList<>();
        labels.add(admin.lbldashbord);
        labels.add(admin.lbluser);
        labels.add(admin.lblchat);
        labels.add(admin.lblsetting);

        for (JLabel label : labels) {
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (selectedLabel != null) {
                        selectedLabel.setBackground(null);
                        selectedLabel.setForeground(Color.black);
                    }
                    selectedLabel = label;
                    selectedLabel.setBackground(Color.white);
                    selectedLabel.setForeground(Color.black);
                    handlePanel(selectedLabel);
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    label.setBackground(Color.gray);
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    if (label != selectedLabel) {
                        label.setBackground(null);
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    label.setOpaque(true);
                    if (label != selectedLabel) {
                        label.setBackground(Color.lightGray);
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (label != selectedLabel) {
                        label.setBackground(null);
                    }
                }
            });
        }
        handlePanel(admin.lbldashbord);
    }

    private void handlePanel(JLabel label) {
        JPanel contentPanel = admin.getContentPanel();
        contentPanel.removeAll();
        JPanel newContent = null;
        try {
            switch (label.getText()) {
                case "Dashboard":
                    newContent = new DashBord();
                    break;
                case "User":
                    newContent = new JPanel();
                    break;
                case "Chat":
                    newContent = new JPanel();
                    break;
                case "Setting":
                    newContent = new JPanel();
                    break;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(admin.frame, "Error loading data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (newContent != null) {
            contentPanel.add(newContent, BorderLayout.CENTER);
            contentPanel.revalidate();
            contentPanel.repaint();
        }
    }



    public static void main(String[] args) {
        new AdminController();
    }
}
