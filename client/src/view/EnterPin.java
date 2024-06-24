package view;

import javax.swing.*;
import java.awt.*;

public class EnterPin {
    private JFrame frame;
    private JPanel mainPanal;
    private JLabel lblPin;
    private JTextField textField;
    private JButton btnEnter;

    public EnterPin(){
        init();
    }

    public void init(){
        frame = new JFrame("Pin");
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(getContentPane());
        frame.setVisible(true);
    }

    public JPanel getContentPane(){
        mainPanal = new JPanel();
        mainPanal.setLayout(new GridLayout(3, 1));

        lblPin = new JLabel("Enter Pin");
        textField = new JTextField("");
        btnEnter = new JButton("check");

        Font font = new Font("Arial", Font.BOLD, 18);

        lblPin.setFont(font);
        textField.setFont(font);
        btnEnter.setFont(font);

        mainPanal.add(lblPin);
        mainPanal.add(textField);
        mainPanal.add(btnEnter);
        return mainPanal;
    }

    public static void main(String[] args) {
        EnterPin enterPin = new EnterPin();
    }


}
