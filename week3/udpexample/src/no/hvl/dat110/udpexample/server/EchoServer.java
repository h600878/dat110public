package no.hvl.dat110.udpexample.server;

import java.net.SocketException;
import java.net.UnknownHostException;

import no.hvl.dat110.udpexample.system.Configuration;

public class EchoServer {

    public static void main(String[] args) throws SocketException {

        int serverport = Configuration.SERVERPORT;

        // EchoServer <port>
        if (args.length > 0) {
            serverport = Integer.parseInt(args[0]);
        }

        System.out.println("UDP Server starting ... #" + serverport);

        UDPEchoServer udpserver = new UDPEchoServer(serverport);

        while (true) {
            udpserver.process();
        }
    }
}
