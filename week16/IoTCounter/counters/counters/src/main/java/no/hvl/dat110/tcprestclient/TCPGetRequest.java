package no.hvl.dat110.tcprestclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class TCPGetRequest {

    private static final int PORT = 8080;
    private static final String HOST = "localhost";
    private static final String URI = "/counters";

    public static void main(String[] args) {

        try (Socket s = new Socket(HOST, PORT)) {

            // Construct the GET request
            String httpgetrequest = "GET " + URI + " HTTP/1.1\r\n" + "Accept: application/json\r\n"
                    + "Host: localhost\r\n" + "Connection: close\r\n" + "\r\n";

            // Send the HTTP request
            OutputStream output = s.getOutputStream();

            PrintWriter pw = new PrintWriter(output, false);

            pw.print(httpgetrequest);
            pw.flush();

            // Read the HTTP response
            InputStream in = s.getInputStream();

            Scanner scan = new Scanner(in);
            StringBuilder jsonresponse = new StringBuilder();
            boolean header = true;

            while (scan.hasNext()) {

                String nextline = scan.nextLine();

                if (header) {
                    System.out.println(nextline);
                }
                else {
                    jsonresponse.append(nextline);
                }

                // Simplified approach to identifying start of body: the empty line
                if (nextline.isEmpty()) {
                    header = false;
                }

            }

            System.out.println(jsonresponse);

            scan.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
