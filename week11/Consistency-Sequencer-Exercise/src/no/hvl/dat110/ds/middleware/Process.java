package no.hvl.dat110.ds.middleware;

import no.hvl.dat110.ds.middleware.iface.OperationType;
import no.hvl.dat110.ds.middleware.iface.ProcessInterface;
import no.hvl.dat110.ds.util.Util;

import java.io.Serial;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * @author tdoy
 * For demo/teaching purpose at dat110 class
 */
public class Process extends UnicastRemoteObject implements ProcessInterface {

    @Serial
    private static final long serialVersionUID = 1L;

    private final List<Message> queue;                    // Queue for this process
    private final int processID;                            // Id of this process
    private double balance = 1000;                    // Default balance (each replica has the same). Our goal is to keep the balance consistent
    private final Map<String, Integer> replicas;            // List of other processes including self known to this process

    protected Process(int id) throws RemoteException {
        super();
        processID = id;
        queue = new ArrayList<>();
        replicas = Util.getProcessReplicas();
    }

    private void updateDeposit(double amount) throws RemoteException {
        balance += amount;
    }

    private void updateInterest(double interest) throws RemoteException {
        double intvalue = balance * interest;
        balance += intvalue;
    }

    private void updateWithdrawal(double amount) throws RemoteException {
        balance -= amount;
    }

    /**
     * Sort the queue by the clock (unique time stamped given by the sequencer)
     */
    private void sortQueue() {
        queue.sort(Comparator.comparingInt(Message::getClock));
    }

    /**
     * Client initiated method
     *
     * @param interest Interest rate
     */
    @Override
    public void requestInterest(double interest) throws RemoteException {

        // Make a new message instance and set the following:
        Message message = new Message();
        // Set the type of message - interest
        message.setOptype(OperationType.INTEREST);
        // Set the process ID
        message.setProcessID(processID);
        // Set the interest
        message.setInterest(interest);

        // Send the message to the sequencer by calling the sendMessageToSequencer
        sendMessageToSequencer(message);
    }

    /**
     * Client initiated method
     *
     * @param amount Amount to deposit
     */
    @Override
    public void requestDeposit(double amount) throws RemoteException {

        // Make a new message instance and set the following
        Message message = new Message();
        // Set the type of message - deposit
        message.setOptype(OperationType.DEPOSIT);
        // Set the process ID
        message.setProcessID(processID);
        // Set the deposit amount
        message.setDepositamount(amount);

        // Send the message to the sequencer
        sendMessageToSequencer(message);
    }

    /**
     * Client initiated method
     *
     * @param amount Amount to withdraw
     */
    @Override
    public void requestWithdrawal(double amount) throws RemoteException {

        // Make a new message instance and set the following
        Message message = new Message();
        // Set the type of message - withdrawal
        message.setOptype(OperationType.WITHDRAWAL);
        // Set the process ID
        message.setProcessID(processID);
        // Set the withdrawal amount
        message.setWithdrawamount(amount);

        // Send the message to the sequencer
        sendMessageToSequencer(message);
    }

    private void sendMessageToSequencer(Message message) throws RemoteException {

        // Get the port for the sequencer: use Util class
        Integer sequencerPort = replicas.get(Sequencer.SEQUENCER);

        // Get the sequencer stub: use Util class
        ProcessInterface sequenceStub = Util.getProcessStub(Sequencer.SEQUENCER, sequencerPort);

        // Using the sequencer stub, call the remote onMessageReceived method to send the message to the sequencer
        sequenceStub.onMessageReceived(message);
    }

    /**
     * Iterate over the queue.
     * For each message in the queue, check the operation type.
     * Call the appropriate update method for the operation type and pass the value to be updated.
     */
    public void applyOperation() throws RemoteException {

        for (var message : queue) {
            switch (message.getOptype()) {
                case DEPOSIT -> updateDeposit(message.getDepositamount());
                case WITHDRAWAL -> updateWithdrawal(message.getWithdrawamount());
                case INTEREST -> updateInterest(message.getInterest());
            }
        }

        Util.printClock(this);
    }

    @Override
    public void onMessageReceived(Message message) throws RemoteException {

        // Upon receipt of a message, add message to the queue
        queue.add(message);
        // Check the ordering limit, if equal to queue size, start to process the following:
        if (queue.size() >= Sequencer.ORDERINGLIMIT) {
            // Sort the queue according to time stamped by the sequencer
            sortQueue();
            // Apply operation and commit
            applyOperation();
            // Clear the queue
            queue.clear();
        }
    }

    @Override
    public double getBalance() throws RemoteException {
        return balance;
    }

    @Override
    public int getProcessID() throws RemoteException {
        return processID;
    }

    @Override
    public List<Message> getQueue() throws RemoteException {
        return queue;
    }

}
