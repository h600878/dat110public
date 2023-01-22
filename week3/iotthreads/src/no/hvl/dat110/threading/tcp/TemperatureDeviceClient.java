package no.hvl.dat110.threading.tcp;

import no.hvl.dat110.threading.TemperatureSensor;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

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

        try (Socket socket = new Socket(HOST, PORT)) {

            DataOutputStream outToServer = new DataOutputStream(socket.getOutputStream());

            outToServer.write(Integer.toString(sensor.read()).getBytes());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
