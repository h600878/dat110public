package no.hvl.dat110.ds.middleware;

import java.io.Serial;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import no.hvl.dat110.ds.middleware.iface.ProcessInterface;

/**
 * @author tdoy
 * <p>
 * Mutual Exclusion using Token Ring Algorithm.
 * Basic implementation with no fault-tolerance
 */
public class TokenManager extends UnicastRemoteObject implements ProcessInterface {

    @Serial
    private static final long serialVersionUID = 1L;

    private int nextid;                                        // unique token id
    public static final String TOKENMANAGER = "tokmanager";        // name of the token manager

    public TokenManager() throws RemoteException {
        super();
        nextid = 0;
    }

    @Override
    public synchronized void requestToken(ProcessInterface requester) throws RemoteException {
        // Check that nextid == 0.
        if (nextid == 0) {
            // If yes, increment nextid and create a Token object with nextid as parameter
            Token token = new Token(++nextid);
            // Send the token object to the requester by calling the onTokenReceived remote method.
            requester.onTokenReceived(token);
        }
        else {
            // If no, send null to the requester
            requester.onTokenReceived(null);
        }
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

    @Override
    public synchronized void onTokenReceived(Token token) throws RemoteException {
        // TODO Auto-generated method stub
    }

    @Override
    public synchronized void forwardToken() throws RemoteException {
        // TODO Auto-generated method stub
    }

    @Override
    public synchronized void setSuccessor(ProcessInterface successor) throws RemoteException {
        // TODO Auto-generated method stub
    }

}
