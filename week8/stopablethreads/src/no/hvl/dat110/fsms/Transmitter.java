package no.hvl.dat110.fsms;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import no.hvl.dat110.stopablethreads.*;

public class Transmitter extends Stopable {

    /**
     * Tilstanden til transmitter
     */
    private FSMState state;

    /**
     * Kø for events som skal behandles
     */
    private final LinkedBlockingQueue<TransmitterEvent> eventqueue;

    /**
     * Referanse til mottaker
     */
    private final Receiver receiver;

    public Transmitter(Receiver receiver) {
        super("Transmitter");
        this.state = FSMState.CLOSED;
        this.receiver = receiver;
        eventqueue = new LinkedBlockingQueue<>();
    }

    /**
     * Events to be processed by this protocol entity
     */
    public void do_open() {
        eventqueue.add(TransmitterEvent.DO_OPEN);
    }

    public void do_send() {
        eventqueue.add(TransmitterEvent.DO_SEND);
    }

    public void do_close() {
        eventqueue.add(TransmitterEvent.DO_CLOSE);
    }

    /**
     * Fetch event from the event queue (if any)
     */
    private TransmitterEvent getNextEvent() {

        TransmitterEvent event = null;

        try {

            // Henter første element i køen, venter opp til 2 sekunder på at det skal dukke opp noe
            event = eventqueue.poll(2, TimeUnit.SECONDS);

        }
        catch (InterruptedException ex) {
            System.out.println("Transmitter - doProcess " + ex.getMessage());
            ex.printStackTrace();
        }

        return event;
    }

    /**
     * Main processing loop
     */
    @Override
    public void doProcess() {

        switch (state) {
            case CLOSED -> doClosed();
            case OPEN -> doOpen();
        }
    }

    /**
     * Processing in the Closed state
     */
    public void doClosed() {

        TransmitterEvent event = getNextEvent();

        if (event != null) {

            System.out.println("Transmitter[" + state + "]" + "(" + event + ")");
            if (event == TransmitterEvent.DO_OPEN) {
                send_open();
                state = FSMState.OPEN;
                System.out.println("Transmitter: CLOSED -> OPEN");
            }
        }
    }

    /**
     * Processing in the Open state
     */
    public void doOpen() {

        TransmitterEvent event = getNextEvent();

        if (event != null) {

            System.out.println("Transmitter[" + state + "]" + "(" + event + ")");

            switch (event) {
                case DO_SEND -> send_data();
                case DO_CLOSE -> {
                    send_close();
                    state = FSMState.CLOSED;
                    System.out.println("Transmitter: OPEN -> CLOSED");
                }
            }
        }
    }

    /**
     * Actions to that this protocol entity may perform
     */
    public void send_open() {
        receiver.recv_open();
    }

    public void send_data() {
        receiver.recv_data();
    }

    public void send_close() {
        receiver.recv_close();
    }

}
