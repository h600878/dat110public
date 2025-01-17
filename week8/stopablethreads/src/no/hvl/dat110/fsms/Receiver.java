package no.hvl.dat110.fsms;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import no.hvl.dat110.fsms.FSMState;

import no.hvl.dat110.stopablethreads.Stopable;

public class Receiver extends Stopable {

    /**
     * Tilstanden til mottaker
     */
    private FSMState state;

    /**
     * Kø for events som skal behandles
     */
    private final LinkedBlockingQueue<ReceiverEvent> eventqueue;

    public Receiver() {
        super("Receiver");
        state = FSMState.CLOSED;
        eventqueue = new LinkedBlockingQueue<>();
    }

    /**
     * Events to be processed by this protocol entity
     */
    public void recv_open() {
        eventqueue.add(ReceiverEvent.RECV_OPEN);
    }

    public void recv_data() {
        eventqueue.add(ReceiverEvent.RECV_DATA);
    }

    public void recv_close() {
        eventqueue.add(ReceiverEvent.RECV_CLOSE);
    }

    /**
     * Fetch event from the event queue (if any)
     */
    private ReceiverEvent getNextEvent() {

        ReceiverEvent event = null;

        try {
            event = eventqueue.poll(2, TimeUnit.SECONDS);
        }
        catch (InterruptedException ex) {
            System.out.println("Receiver - doProcess " + ex.getMessage());
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
     * Processing in the closed state
     */
    public void doClosed() {

        ReceiverEvent event = getNextEvent();

        if (event != null) {

            System.out.println("Receiver[" + state + "]" + "(" + event + ")");

            if (event == ReceiverEvent.RECV_OPEN) {
                state = FSMState.OPEN;
                System.out.println("Receiver: CLOSED -> OPEN");
            }
        }
    }

    /**
     * Processing in the open state
     */
    public void doOpen() {

        ReceiverEvent event = getNextEvent();

        if (event != null) {

            System.out.println("Receiver[" + state + "]" + "(" + event + ")");

            switch (event) {
                case RECV_DATA -> dlv_data();
                case RECV_CLOSE -> {
                    state = FSMState.CLOSED;
                    System.out.println("Receiver: OPEN -> CLOSED");
                }
            }
        }
    }

    /**
     * Actions that can be performed by this protocol entity
     */
    private int i = 1;

    public void dlv_data() {
        System.out.println("Receiver[" + state + "](DLV_DATA)[" + i + "]");
        i++;
    }
}
