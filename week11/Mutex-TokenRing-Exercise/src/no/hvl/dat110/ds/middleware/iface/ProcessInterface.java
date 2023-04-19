package no.hvl.dat110.ds.middleware.iface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import no.hvl.dat110.ds.middleware.Message;
import no.hvl.dat110.ds.middleware.Token;

public interface ProcessInterface extends Remote {

    void requestInterest(double interest) throws RemoteException;

    void requestDeposit(double amount) throws RemoteException;

    void requestWithdrawal(double amount) throws RemoteException;

    double getBalance() throws RemoteException;

    List<Message> getQueue() throws RemoteException;

    int getProcessID() throws RemoteException;

    void applyOperation() throws RemoteException;

    void requestToken(ProcessInterface requester) throws RemoteException;

    void forwardToken() throws RemoteException;

    void onTokenReceived(Token token) throws RemoteException;

    void setSuccessor(ProcessInterface successor) throws RemoteException;
}
