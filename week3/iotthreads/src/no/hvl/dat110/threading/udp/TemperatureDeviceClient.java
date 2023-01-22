package no.hvl.dat110.threading.udp;

import no.hvl.dat110.threading.TemperatureSensor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class TemperatureDeviceClient {

    private static final int PORT = 8082, COUNT = 10;
    private static final String HOST = "localhost";

    public static void main(String[] args) {

        System.out.println("Temperature device client starting ... ");

        for (int i = 0; i < COUNT; i++) {
            new Thread(TemperatureDeviceClient::getTempAndSend).start();
        }

        System.out.println("Temperature device client stopping ... ");
    }

    private static void getTempAndSend() {
        TemperatureSensor sensor = new TemperatureSensor();

        byte[] temp = String.valueOf(sensor.read()).getBytes();

        try (DatagramSocket socket = new DatagramSocket()) {

            DatagramPacket request = new DatagramPacket(temp, temp.length, InetAddress.getByName(HOST), PORT);

            socket.send(request);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
