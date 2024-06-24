package controller;

import view.EnterRecharge;
import view.EnterWithdraw;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class RechargeController {
    public EnterRecharge recharge;
    public DataInputStream dis;
    public DataOutputStream dos;
    public int balance;
    public RechargeController( DataOutputStream dos, DataInputStream dis) {
        this.dos = dos;
        this.dis = dis;
        init();
    }
    public void init() {
        recharge = new EnterRecharge();
        recharge.setVisible(true);
        recharge.txtEnterMoney.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (recharge.txtEnterMoney.getText().equals("just digit number > 0")) {
                    recharge.txtEnterMoney.setText("");
                    recharge.txtEnterMoney.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (recharge.txtEnterMoney.getText().isEmpty()) {
                    recharge.txtEnterMoney.setForeground(Color.GRAY);
                    recharge.txtEnterMoney.setText("just digit number > 0");
                }
            }
        });

        recharge.btnEnter.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!validate()) {
                    JOptionPane.showMessageDialog(null, "please enter valid input");
                }
                else {
                    int money = Integer.parseInt(recharge.txtEnterMoney.getText());
                    try {
                        dos.writeUTF("recharge money");
                        dos.flush();
                        dos.writeInt(money);
                        dos.flush();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }


        });
    }


    public boolean validate() {
        String input = recharge.txtEnterMoney.getText();
        if (input.isEmpty()) {
            return false;
        }
        return input.matches("\\d+");
    }


    public JFrame getFrame() {
        return recharge.frame;
    }

    public void setVisible(boolean b) {
        recharge.frame.setVisible(b);
    }

}

