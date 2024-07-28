package com.example.otr;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

public class Sender {
    public static void main(String[] args) {
        Socket serverSocket;
        DataOutputStream dataOutputStream;
        String message = "test";
        try {
            serverSocket = new Socket("localhost", 1453);  // Sunucuya bağlan
            dataOutputStream = new DataOutputStream(serverSocket.getOutputStream());
            dataOutputStream.writeUTF(message);  // Mesaj gönder
            serverSocket.close();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
