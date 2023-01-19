package no.hvl.dat110.tcpsockets;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPReceiver {

    private static final int PORT = 8081;

    public static void main(String[] args) {

        byte[] data = new byte[4];

        try {
            System.out.println("TCP Receiver starting");

            ServerSocket welcomeSocket = new ServerSocket(PORT);

            Socket connectionSocket = welcomeSocket.accept();

            DataInputStream inFromSender = new DataInputStream(connectionSocket.getInputStream());

            System.out.println("TCP Receiver reading");

            int bytes;
            do {
                bytes = inFromSender.read(data);
                System.out.print("TCP Receiver received: ");
                for (byte b : data) {
                    System.out.print(b);
                }
                System.out.println("\nBytes received: " + bytes);
            } while (bytes != -1);

            inFromSender.close();
            connectionSocket.close();
            welcomeSocket.close();
        }
        catch (IOException ex) {

            System.out.println("TCPServer: " + ex.getMessage());
            ex.printStackTrace();
            System.exit(1);

        }
        System.out.println("TCP Receiver stopping");
    }

}
