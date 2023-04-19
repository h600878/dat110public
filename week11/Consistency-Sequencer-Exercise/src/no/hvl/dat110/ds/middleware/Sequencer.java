package no.hvl.dat110.ds.middleware;


import java.io.Serial;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import no.hvl.dat110.ds.middleware.iface.ProcessInterface;
import no.hvl.dat110.ds.util.Util;

/**
 * @author tdoy
 * <p>
 * Active Replication: Using a single sequencer to provide a total order for all writes propagated to replicas
 * plus using a bounded ordering deviation to initiate when updates should be performed.
 */
public class Sequencer extends UnicastRemoteObject implements ProcessInterface {

    @Serial
    private static final long serialVersionUID = 1L;

    private int nextid = 0;                                    // Unique id (timestamp)
    private final List<Message> queue;                            // Queue for storing the writes from the replicas
    public static final int ORDERINGLIMIT = 4;                // Bounding for ordering deviation. When should sequencer multicast writes?
    private final Map<String, Integer> replicas;                    // List of other processes including self known to this process
    public static final String SEQUENCER = "sequencer";        // Name of this sequencer

    public Sequencer() throws RemoteException {
        queue = new ArrayList<>();
        replicas = Util.getProcessReplicas();
        replicas.remove(SEQUENCER);
    }

    @Override
    public synchronized void onMessageReceived(Message message) throws RemoteException {
        // Increment nextid (time stamp)
        nextid++;

        // Set the nextid as the clock for the message: use setClock
        message.setClock(nextid);
        // Add the message to the queue
        queue.add(message);
        // Check if the ordering limit has been reached.
        // If yes, multicast queue messages to all the replicas by calling the sendQueueMessagesToReplicas
        if (queue.size() >= ORDERINGLIMIT) {

            sendQueueMessagesToReplicas();

            // Reset the queue
            queue.clear();
        }
        // And reset nextid
        nextid = 0;
    }

    private synchronized void sendQueueMessagesToReplicas() throws RemoteException {

        // Iterate over each replica,
        for (var replica : replicas.entrySet()) {
            // Get the port for each process
            int port = replica.getValue();
            String stubId = replica.getKey();
            // Get the process stub: use Util
            ProcessInterface processStub = Util.getProcessStub(stubId, port);

            // Using the stub, call the onReceivedMessage remote method and forward all the messages in the queue to this remote process
            for (Message message : queue) {
                processStub.onMessageReceived(message);
            }
        }

        // Clear the queue when done
        queue.clear();
    }

    @Override
    public synchronized void requestInterest(double interest) throws RemoteException {
        // TODO Auto-generated method stub

    }

    @Override
    public synchronized void requestDeposit(double amount) throws RemoteException {
        // TODO Auto-generated method stub

    }

    @Override
    public synchronized void requestWithdrawal(double amount) throws RemoteException {
        // TODO Auto-generated method stub

    }

    @Override
    public synchronized double getBalance() throws RemoteException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public synchronized List<Message> getQueue() throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public synchronized int getProcessID() throws RemoteException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public synchronized void applyOperation() throws RemoteException {
        // TODO Auto-generated method stub
    }

}
