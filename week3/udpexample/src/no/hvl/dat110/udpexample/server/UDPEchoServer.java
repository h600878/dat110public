package no.hvl.dat110.udpexample.server;

import java.io.*;
import java.net.*;

import no.hvl.dat110.udpexample.system.Configuration;

public class UDPEchoServer {

    private final DatagramSocket serverSocket;

    public UDPEchoServer(int serverport) throws SocketException {
        serverSocket = new DatagramSocket(serverport);
    }

    public void process() {

        byte[] recvbuf = new byte[Configuration.MAXTEXTLEN]; // Mottaker buffer

        // Siden dette er en UDP-server, så må vi lage en datagram (pakke) for å motta data
        DatagramPacket request = new DatagramPacket(recvbuf, recvbuf.length);

        try {

            serverSocket.receive(request); // Venter på input fra klient

            String intext = new String(request.getData()); // Lager en ny string basert på bytes fra request

            System.out.println("SERVER RECEIVED: " + intext);

            String outtext = intext.toUpperCase();

            byte[] msg = outtext.getBytes(); // Konverterer string tilbake til bytes

            InetAddress ipaddress = request.getAddress();
            int port = request.getPort();

            // Oppretter en respons pakke
            DatagramPacket response = new DatagramPacket(msg, msg.length, ipaddress, port);

            System.out.println("SERVER SENDING:  " + outtext);

            // Sender respons pakken
            serverSocket.send(response);
        }
        catch (IOException ex) {
            System.out.println("UDPServer: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void stop() {
        serverSocket.close();
    }
}
