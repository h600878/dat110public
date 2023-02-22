package no.hvl.dat110.udp.multiplexing.tests;

import no.hvl.dat110.udp.multiplexing.ReceiverProcess;
import no.hvl.dat110.udp.multiplexing.SenderProcess;
import org.junit.Test;

import java.net.SocketException;
import java.net.UnknownHostException;

public class RunUDPExample {

    private static final String TESTHOST = "localhost";
    private static final String TESTPORT = "8080";

    @Test
    public void test() {

        System.out.println("UDP example starting ...");

        String[] senderargs = {TESTHOST, TESTPORT};
        String[] receiverargs = {TESTPORT};

        Runnable sp = () -> SenderProcess.main(senderargs);
        Runnable rp = () -> ReceiverProcess.main(receiverargs);

        Thread tsender = new Thread(sp);
        Thread treceiver = new Thread(rp);

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

        System.out.println("UDP example stopped");

    }
}
