package no.hvl.dat110.udp.multiplexing.tests;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;

import static org.junit.Assert.*;

import org.junit.Test;

import no.hvl.dat110.udp.multiplexing.UDPReceiver;
import no.hvl.dat110.udp.multiplexing.UDPSender;

public class TestUDP {

    private static final String TESTHOST = "localhost";
    private static final int TESTPORT = 8080;
    private static final String TESTMESSAGE = "TEST";

    @Test
    public void test() throws SocketException, UnknownHostException {

        UDPSender sender = new UDPSender(TESTHOST, TESTPORT);
        UDPReceiver receiver = new UDPReceiver(TESTPORT);

        Thread tsender = new Thread(() -> {
            sender.send(TESTMESSAGE.getBytes());
            sender.close();
        });

        Thread treceiver = new Thread(() -> {

            byte[] data = new byte[255];
            int len = receiver.receive(data);

            String message = (new String(Arrays.copyOfRange(data, 0, len)));

            assertEquals(message, TESTMESSAGE);
            receiver.close();
        });

        treceiver.start();

        tsender.start();

        try {
            tsender.join();
            treceiver.join();

        }
        catch (InterruptedException ex) {

            System.out.println("Main thread " + ex.getMessage());
            ex.printStackTrace();
        }

        assertTrue(true);
    }
}
