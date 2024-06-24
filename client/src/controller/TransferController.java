package controller;

import view.EnterTransfer;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class TransferController {
    public EnterTransfer transfer;
    public HomeController homecon;
    public DataOutputStream dos;
    public DataInputStream dis;


    public TransferController(DataOutputStream dos, DataInputStream dis, HomeController homecon) {
        this.dos = dos;

        this.dis = dis;
        this.homecon = homecon;
        init();
    }

    public void init() {

        transfer = new EnterTransfer();

        transfer.btnEnter.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!validate()) {
                    JOptionPane.showMessageDialog(null, "please enter valid input");
                }
                else {
                    try {
                        String account = transfer.txtEnterAccount.getText();
                        int money = Integer.parseInt(transfer.txtEnterMoney.getText());
                        String balanceText = homecon.home.getLblBalance().getText();
                        balanceText = balanceText.replace("Balance: ", "").replace("$", "");
                        int balance = Integer.parseInt(balanceText);
                        if (money > balance) {
                            JOptionPane.showMessageDialog(null, "balance not enough");
                        } else {
                            dos.writeUTF("Transfer money");
                            dos.flush();
                            dos.writeUTF(account);
                            dos.flush();
                            dos.writeInt(money);
                            dos.flush();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });


        transfer.txtEnterMoney.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (transfer.txtEnterMoney.getText().equals("just digit number")) {
                    transfer.txtEnterMoney.setText("");
                    transfer.txtEnterMoney.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (transfer.txtEnterMoney.getText().isEmpty()) {
                    transfer.txtEnterMoney.setForeground(Color.GRAY);
                    transfer.txtEnterMoney.setText("just digit number");
                }
            }
        });
    }

    public boolean validate() {
        if (transfer.txtEnterAccount.getText().isEmpty()) {
            return false;
        }
        return !transfer.txtEnterMoney.getText().isEmpty() && transfer.txtEnterMoney.getText().matches("\\d+");
    }


    public void setVisible(boolean b) {
        transfer.setVisible(b);
    }


}
