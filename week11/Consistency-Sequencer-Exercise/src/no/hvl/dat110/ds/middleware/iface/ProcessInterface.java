package no.hvl.dat110.ds.middleware.iface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import no.hvl.dat110.ds.middleware.Message;

public interface ProcessInterface extends Remote {

    void requestInterest(double interest) throws RemoteException;

    void requestDeposit(double amount) throws RemoteException;

    void requestWithdrawal(double amount) throws RemoteException;

    double getBalance() throws RemoteException;

    List<Message> getQueue() throws RemoteException;

    int getProcessID() throws RemoteException;

    void onMessageReceived(Message message) throws RemoteException;

    void applyOperation() throws RemoteException;


}
