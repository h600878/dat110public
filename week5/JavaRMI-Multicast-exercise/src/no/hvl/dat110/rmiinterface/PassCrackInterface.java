package no.hvl.dat110.rmiinterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PassCrackInterface extends Remote {

    void registerWorkerCallbackObject(WorkerCallbackInterface workercallback) throws RemoteException;

    void crackPassword(int keylength, String hashtocrack, String workername) throws RemoteException;

    void shutdownWorker() throws RemoteException;

}
