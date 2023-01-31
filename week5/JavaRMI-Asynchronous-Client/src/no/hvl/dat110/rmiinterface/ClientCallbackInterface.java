package no.hvl.dat110.rmiinterface;

/*
 * For demonstration purposes in DAT110 class
 */

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientCallbackInterface extends Remote {

    /**
     * methods that server must call to notify any client that implements this interface of the results
     */
    void notify(String result) throws RemoteException;

    boolean isNotified() throws RemoteException;

    void acknowledge(String msg) throws RemoteException;

}
