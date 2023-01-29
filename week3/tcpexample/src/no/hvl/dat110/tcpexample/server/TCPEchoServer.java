package no.hvl.dat110.tcpexample.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPEchoServer {

    private final ServerSocket welcomeSocket;
    private static final Random random = new Random();
    private static final ExecutorService executor = Executors.newFixedThreadPool(4);

    public TCPEchoServer(ServerSocket welcomeSocket) {
        this.welcomeSocket = welcomeSocket;
    }

    public void process() {

        try {

            System.out.println("SERVER ACCEPTING");

            // Akspeterer en klient og returnerer en socket for kommunikasjon
            Socket connectionSocket = welcomeSocket.accept();

            BufferedReader inFromClient = new BufferedReader(
                    new InputStreamReader(connectionSocket.getInputStream()));

            DataOutputStream outToClient =
                    new DataOutputStream(connectionSocket.getOutputStream());

            final String text = inFromClient.readLine();

            executor.execute(() -> {
                System.out.println("SERVER RECEIVED: " + text);

                String outtext = text.toUpperCase();

                sleep();

                System.out.println("SERVER SENDING: " + outtext);

                try {
                    outToClient.write(outtext.getBytes());
                    outToClient.flush();

                    inFromClient.close();
                    outToClient.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            });
            System.out.println("SERVER CONTINUING");

        }
        catch (IOException ex) {

            System.out.println("TCPServer: " + ex.getMessage());
            ex.printStackTrace();
            System.exit(1);

        }
    }

    private void sleep() {
        int sleep = random.nextInt(10_000) + 5_000;
        try {
            Thread.sleep(sleep); // Sleep for a random time between 5 and 15 seconds
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
