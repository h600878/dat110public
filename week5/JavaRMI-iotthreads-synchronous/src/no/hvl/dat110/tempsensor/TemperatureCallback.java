package no.hvl.dat110.tempsensor;

import no.hvl.dat110.rpcinterface.CallbackInterface;

import java.io.Serial;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class TemperatureCallback extends UnicastRemoteObject implements CallbackInterface {

    @Serial
    private static final long serialVersionUID = 1L;

    private boolean notified = false;

    public TemperatureCallback() throws RemoteException {
        super();
    }

    @Override
    public void notify(String result) throws RemoteException {
        System.out.println("Temperature: " + result);
        notified = true;
    }

    @Override
    public boolean isNotified() throws RemoteException {
        return notified;
    }

    @Override
    public void acknowledge(String msg) throws RemoteException {
        System.out.println(msg);
    }

}
