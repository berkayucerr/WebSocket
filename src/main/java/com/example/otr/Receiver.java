package com.example.otr;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Receiver {
    private static final AudioFormat format = new AudioFormat(16000, 16, 1, true, false); // mono ve little-endian

    public static void main(String[] args) {

        ServerSocket serverSocket;
        Socket clientSocket;
        InputStream in;

        try {
            // Sunucu soketini başlat
            serverSocket = new ServerSocket(1453);
            System.out.println("Server is listening on port 1453...");
            clientSocket = serverSocket.accept();
            System.out.println(clientSocket.getInetAddress());
            System.out.println(clientSocket.getPort());
            System.out.println("Client connected.");

            // İstemciden veri al
            in = clientSocket.getInputStream();

            // Ses verisini oynatma hattına yönlendir
            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
            SourceDataLine speakers = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
            speakers.open(format);
            speakers.start();

            // Veriyi okuyup hoparlöre yaz
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                speakers.write(buffer, 0, bytesRead);
            }

            // Kaynakları kapat
            speakers.drain();
            speakers.close();
            clientSocket.close();
            serverSocket.close();
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }

    }
}
