package no.hvl.dat110.udpexample.client;

import java.io.*;
import java.net.*;

public class UDPEchoClient {

    private final DatagramSocket clientSocket;
    private final InetAddress serveradr;
    private final int port;

    public UDPEchoClient(String server, int port) throws SocketException, UnknownHostException {

        clientSocket = new DatagramSocket();
        serveradr = InetAddress.getByName(server);
        this.port = port;

    }

    public String convert(String text) {

        // Vi må konvertere string til bytes for å kunne sende over nettverket
        byte[] msg = text.getBytes();
        byte[] recvbuffer = new byte[msg.length];

        String outtext = null;

        try {
            // Oppretter en pakke for å sende data, og definerer hvilken adresse og port
            DatagramPacket request = new DatagramPacket(msg, msg.length, serveradr, port);

            // Sender pakken
            clientSocket.send(request);

            // Oppretter en respons pakke
            DatagramPacket response = new DatagramPacket(recvbuffer, recvbuffer.length);

            // Venter på respons fra tjener
            clientSocket.receive(response);

            outtext = new String(recvbuffer);

        }
        catch (IOException ex) {
            System.out.println("UDPEchoClient: " + ex.getMessage());
            ex.printStackTrace();
        }

        return outtext;

    }

    public void stop() {

        if (clientSocket != null) {
            clientSocket.close();
        }

    }
}
