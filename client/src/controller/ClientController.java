package controller;

import javax.swing.JOptionPane;
import configs.Configs;
import model.Client;
import view.AddressIP;

public class ClientController {
    private Client client;
    private AddressIP addressIP;

    public ClientController() {
        initialize();
    }

    public void initialize() {
        addressIP = new AddressIP();
        addressIP.init();
        addressIP.getAccepButton().addActionListener(e -> {
            String ip = addressIP.getTextField().getText();
            try {
                client = new Client(ip, Configs.SERVER_PORT);
                if (client.connect()) {
                    JOptionPane.showMessageDialog(null, "Connection successful to: " + ip);
                    new LoginController(client);
                    addressIP.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Connection failed to: " + ip);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Connection error: " + ex.getMessage());
            }
        });
    }

    public static void main(String[] args) {
        new ClientController();
    }
}
