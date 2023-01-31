package no.hvl.dat110.rpcinterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CallbackInterface extends Remote {

    void notify(String result) throws RemoteException;

    boolean isNotified() throws RemoteException;

    void acknowledge(String msg) throws RemoteException;

}
