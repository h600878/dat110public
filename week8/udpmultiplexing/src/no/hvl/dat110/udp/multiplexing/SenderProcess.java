package no.hvl.dat110.udp.multiplexing;

import java.util.Scanner;

public class SenderProcess {

    public static void main(String[] args) {

        if (args.length != 2) {
            throw new RuntimeException("usage: SenderProcess <remotehost> <remoteport>");
        }

        String host = args[0];
        int port = Integer.parseInt(args[1]);

        UDPSender sender = null;

        try {
            sender = new UDPSender(host, port);
        }
        catch (Exception ex) {

            System.out.println("SenderProcess " + ex.getMessage());
            ex.printStackTrace();
        }

        System.out.println("SenderProcess to " + host + ":" + port);

        String message;
        Scanner input;

        do {

            System.out.print("!");

            input = new Scanner(System.in);

            message = input.nextLine();

            assert sender != null;
            sender.send(message.getBytes());

        } while (!message.equals(""));

        input.close();

        sender.close();

        System.out.println("SenderProcess terminate");

    }
}
