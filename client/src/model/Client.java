package model;

import java.io.*;
import java.net.Socket;

import configs.Configs;

public class Client extends Thread {
    private final String host;
    private final int port;
    private Socket socket;
    public DataInputStream dis;
    public DataOutputStream dos;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public boolean connect() {
        try {
            if (socket != null && socket.isConnected() && !socket.isClosed()) {
                return true;
            }
            this.socket = new Socket(this.host, this.port);
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void disconnect() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DataInputStream getDis() {
        return dis;
    }

    public DataOutputStream getDos() {
        return dos;
    }

    public String getIp() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public static void main(String[] args) {
        Client client = new Client("localhost", Configs.SERVER_PORT);
        if (client.connect()) {
            System.out.println("Connected to server.");
        } else {
            System.out.println("Failed to connect to server.");
        }
    }
}
