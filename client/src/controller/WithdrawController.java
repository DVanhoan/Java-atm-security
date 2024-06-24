package controller;

import view.EnterWithdraw;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class WithdrawController {
    public EnterWithdraw withdraw;
    public DataInputStream dis;
    public DataOutputStream dos;
    HomeController home;
    public WithdrawController(HomeController home, DataOutputStream dos, DataInputStream dis) {
        this.dos = dos;
        this.home = home;
        this.dis = dis;
        init();
    }
    public void init() {
        withdraw = new EnterWithdraw();
        withdraw.setVisible(true);
        withdraw.txtEnterMoney.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (withdraw.txtEnterMoney.getText().equals("just digit number > 0")) {
                    withdraw.txtEnterMoney.setText("");
                    withdraw.txtEnterMoney.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (withdraw.txtEnterMoney.getText().isEmpty()) {
                    withdraw.txtEnterMoney.setForeground(Color.GRAY);
                    withdraw.txtEnterMoney.setText("just digit number > 0");
                }
            }
        });

        withdraw.btnEnter.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!validate()) {
                    JOptionPane.showMessageDialog(null, "please enter valid input");
                }
                else {
                    int money = Integer.parseInt(withdraw.txtEnterMoney.getText());
                    //xoa di ky tu $
                    String balanceText = home.home.getLblBalance().getText();
                    balanceText = balanceText.replace("Balance: ", "").replace("$", "");
                    int balance = Integer.parseInt(balanceText);
                    if (money > balance) {
                        JOptionPane.showMessageDialog(null, "Not enough money");
                    } else {
                        try {
                            dos.writeUTF("withdraw money");
                            dos.flush();
                            dos.writeInt(money);
                            dos.flush();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
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
        String input = withdraw.txtEnterMoney.getText();
        if (input.isEmpty()) {
            return false;
        }
        return input.matches("\\d+");
    }

    public JFrame getFrame() {
        return withdraw.frame;
    }

    public void setVisible(boolean b) {
        withdraw.frame.setVisible(b);
    }
}

