package view;

import javax.swing.*;
import java.awt.*;

public class Admin {
    public JFrame frame;
    public JPanel panel, sideBar, content;
    public JLabel lbldashbord, lbluser, lblchat, lblsetting;

    public Admin() {
        init();
    }

    private void init() {
        frame = new JFrame("Admin");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        panel = new JPanel(new BorderLayout());

        setupSideBar();
        panel.add(sideBar, BorderLayout.WEST);


        content = new JPanel(new BorderLayout());
        panel.add(content, BorderLayout.CENTER);

        frame.setContentPane(panel);
        frame.setVisible(true);
    }

    private void setupSideBar() {
        sideBar = new JPanel(new GridLayout(0, 1));
        sideBar.setPreferredSize(new Dimension(155, 600));
        sideBar.setBackground(Color.LIGHT_GRAY);

        lbldashbord = new JLabel("Dashboard", new ImageIcon("src/image/dashbord.png"), JLabel.CENTER);
        lbldashbord.setVerticalTextPosition(JLabel.BOTTOM);
        lbldashbord.setHorizontalTextPosition(JLabel.CENTER);
        sideBar.add(lbldashbord);

        lbluser = new JLabel("User", new ImageIcon("src/image/user.png"), JLabel.CENTER);
        lbluser.setVerticalTextPosition(JLabel.BOTTOM);
        lbluser.setHorizontalTextPosition(JLabel.CENTER);
        sideBar.add(lbluser);

        lblchat = new JLabel("Chat", new ImageIcon("src/image/chat.png"), JLabel.CENTER);
        lblchat.setVerticalTextPosition(JLabel.BOTTOM);
        lblchat.setHorizontalTextPosition(JLabel.CENTER);
        sideBar.add(lblchat);

        lblsetting = new JLabel("Setting", new ImageIcon("src/image/setting.png"), JLabel.CENTER);
        lblsetting.setVerticalTextPosition(JLabel.BOTTOM);
        lblsetting.setHorizontalTextPosition(JLabel.CENTER);
        sideBar.add(lblsetting);
    }

    public JPanel getContentPanel() {
        return content;
    }

    public static void main(String[] args) {
        new Admin();
    }
}
