package no.hvl.dat110.ds.middleware;

import java.io.Serial;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import no.hvl.dat110.ds.middleware.iface.OperationType;
import no.hvl.dat110.ds.middleware.iface.ProcessInterface;
import no.hvl.dat110.ds.util.Util;

/**
 * @author tdoy
 * For demo/teaching purpose at dat110 class
 * Mutual Exclusion using Token Ring Algorithm.
 * Basic implementation with no fault-tolerance
 */
public class Process extends UnicastRemoteObject implements ProcessInterface {


    @Serial
    private static final long serialVersionUID = 1L;

    private final List<Message> queue;                    // Queue for this process
    private final int processID;                            // Id of this process
    private double balance = 1000;                    // Default balance (each replica has the same). Our goal is to avoid concurrent access
    private Token token = null;                        // Token to be passed in the ring
    private ProcessInterface successor;                // Each process has the knowledge of its successor
    private final ExecutorService backgroundExec = Executors.newCachedThreadPool();

    protected Process(int id) throws RemoteException {
        super();
        processID = id;
        queue = new ArrayList<>();
    }

    private void updateDeposit(double amount) throws RemoteException {
        balance += amount;
    }

    private void updateInterest(double interest) throws RemoteException {
        balance *= interest;
    }

    private void updateWithdrawal(double amount) throws RemoteException {
        balance -= amount;
    }

    /**
     * Client initiated method
     *
     * @param interest Interest rate to be applied
     */
    @Override
    public void requestInterest(double interest) throws RemoteException {

        // Make a new message instance and set the following:
        Message message = new Message();
        // Set the type of message - interest (get it from OperationType)
        message.setOptype(OperationType.INTEREST);
        // Set the process ID
        message.setProcessID(processID);
        // Set the interest
        message.setInterest(interest);
        // Add message to queue
        queue.add(message);
    }

    /**
     * Client initiated method
     *
     * @param amount Amount to be deposited
     */
    @Override
    public void requestDeposit(double amount) throws RemoteException {

        // Make a new message instance and set the following
        Message message = new Message();
        // Set the type of message - deposit (get it from OperationType)
        message.setOptype(OperationType.DEPOSIT);
        // Set the process ID
        message.setProcessID(processID);
        // Set the deposit amount
        message.setDepositamount(amount);
        // Add message to queue
        queue.add(message);
    }

    /**
     * Client initiated method
     *
     * @param amount Amount to be withdrawn
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
        // Add message to queue
        queue.add(message);
    }

    @Override
    public void forwardToken() throws RemoteException {

        // Create a new token object and pass the old token's Id as its parameter
        Token newToken = new Token(token.tokenId());

        // Set the old token to null
        token = null;

        // Forward the new token to the successor by calling the onTokenReceived remote method
        successor.onTokenReceived(newToken);
    }

    /**
     * @param successor the successor to set
     */
    @Override
    public void setSuccessor(ProcessInterface successor) throws RemoteException {
        this.successor = successor;
    }

    /**
     * Iterate over the queue
     * For each message in the queue, check the operation type
     * Call the appropriate update method for the operation type and pass the value to be updated
     */
    @Override
    public void applyOperation() throws RemoteException {

        for (Message message : queue) {
            switch (message.getOptype()) {
                case DEPOSIT -> updateDeposit(message.getDepositamount());
                case WITHDRAWAL -> updateWithdrawal(message.getWithdrawamount());
                case INTEREST -> updateInterest(message.getInterest());
            }
        }

        Util.printQueue(this);
        // Clear the queue
        queue.clear();
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

    @Override
    public void onTokenReceived(Token token) throws RemoteException {

        // Check whether token is null
        if (token != null) {
            // If no, set the token object to the received token
            this.token = token;
        }

        // Call applyOperation method
        applyOperation();

        // Forward the token to the successor process by calling the forwardToken method
        forwardToken();
    }

    /**
     * Give this job to a different thread
     *
     * @param requester The process that requested the token
     */
    @Override
    public void requestToken(final ProcessInterface requester) throws RemoteException {

        backgroundExec.execute(() -> {
            ProcessInterface tokmanager = Util.getProcessStub(TokenManager.TOKENMANAGER, Config.PORT4);
            try {
                tokmanager.requestToken(requester);
            }
            catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

}
