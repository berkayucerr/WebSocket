package com.example.otr;
import javax.sound.sampled.*;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class AudioRecorder {
    private static final AudioFormat format = new AudioFormat(16000, 16, 1, true, false); // mono ve little-endian

    public static void main(String[] args) {
        TargetDataLine microphone;
        Socket socket;
        OutputStream out;

        try {
            // Ses giriş veri hattı
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            microphone = (TargetDataLine) AudioSystem.getLine(info);
            microphone.open(format);
            microphone.start();

            // Sunucuya bağlan
            socket = new Socket("localhost", 1453);
            out = socket.getOutputStream();

            System.out.println("Recording and sending audio data...");

            // Ses verisini oku ve sokete gönder
            byte[] buffer = new byte[4096];
            int bytesRead;
            while (true) {
                bytesRead = microphone.read(buffer, 0, buffer.length);
                out.write(buffer, 0, bytesRead);
            }
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }
}
