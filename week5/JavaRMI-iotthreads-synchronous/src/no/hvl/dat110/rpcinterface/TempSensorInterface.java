package no.hvl.dat110.rpcinterface;

/*
 * dat110: DS Lab2
 */

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TempSensorInterface extends Remote {

    int SERVER_PORT = 9091, N = 2;

    String REMOTE_IFACE_NAME = "TempSensorInterface";

    void setTemperature(int temp) throws RemoteException;

    int getTemperature() throws RemoteException;

    void setCallback(CallbackInterface callback) throws RemoteException;

    CallbackInterface getCallback() throws RemoteException;

}
