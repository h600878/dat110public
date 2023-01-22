package no.hvl.dat110.threading.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class DisplayDeviceServer {

    private static final int PORT = 8082, COUNT = 10;

    public static void main(String[] args) {

        System.out.println("Display device server starting ... ");

        DatagramPacket request = new DatagramPacket(new byte[1024], 1024);

        try (DatagramSocket datagramSocket = new DatagramSocket(PORT)) {

            for (int i = 0; i < COUNT; i++) {
                datagramSocket.receive(request);

                String text = new String(request.getData()).trim();
                System.out.println("TEMP: " + text + " C");
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Display device server stopping ... ");
    }

}
