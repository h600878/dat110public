package no.hvl.dat110.threading.tcp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class DisplayDeviceServer {

    private static final int PORT = 8082, COUNT = 10;

    public static void main(String[] args) {

        System.out.println("Display device server starting ... ");

        try (ServerSocket welcomeSocket = new ServerSocket(PORT)) {

            for (int i = 0; i < COUNT; i++) {
                Socket connectionSocket = welcomeSocket.accept();
                BufferedReader input = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

                String text = input.readLine();
                System.out.println("TEMP: " + text + " C");
                input.close();
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Display device server stopping ... ");
    }

}
