package no.hvl.dat110.udpsockets;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPSender {

    // host name and port with whom to communicate
    private static final String RECEIVER_HOST = "localhost";
    private static final int RECEIVER_PORT = 8081;

    public static void main(String[] args) {

        byte[] data = {1, 2, 3, 4};

        System.out.println("UDPSender starting");
        // create socket
        try (DatagramSocket socket = new DatagramSocket()) {

            // create a datagram
            InetAddress address = InetAddress.getByName(RECEIVER_HOST);
            DatagramPacket datagram = new DatagramPacket(data, data.length, address, RECEIVER_PORT);

            System.out.println("UDPSender sending");
            socket.send(datagram);
        }
        catch (Exception ex) {
            System.out.println("UDPSender: " + ex.getMessage());
            ex.printStackTrace();
        }
        finally {
            System.out.println("UDPSender stopped");
        }
    }
}
