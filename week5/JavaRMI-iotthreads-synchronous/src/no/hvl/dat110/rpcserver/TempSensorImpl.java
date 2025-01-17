package no.hvl.dat110.rpcserver;

import java.io.Serial;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.atomic.AtomicInteger;

import no.hvl.dat110.rpcinterface.CallbackInterface;
import no.hvl.dat110.rpcinterface.TempSensorInterface;

/**
 * For demonstration purpose in dat110 course
 */

public class TempSensorImpl extends UnicastRemoteObject implements TempSensorInterface {

    @Serial
    private static final long serialVersionUID = 1L;

    private final AtomicInteger temp;
    private CallbackInterface callback;

    public TempSensorImpl(CallbackInterface callback) throws RemoteException {
        super();
        temp = new AtomicInteger(0);
        this.callback = callback;
    }

    public TempSensorImpl() throws RemoteException {
        this(null);
    }

    @Override
    public void setTemperature(int temp) throws RemoteException {
        callback.acknowledge("Temperature is being set to " + temp);
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.temp.set(temp);
        callback.notify("Temperature is set to " + temp);
    }

    @Override
    public int getTemperature() throws RemoteException {
        while (!callback.isNotified()) {
            System.out.println("Waiting for temperature to be set...");
            try {
                //noinspection BusyWait
                Thread.sleep(100);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return temp.get();
    }

    @Override
    public void setCallback(CallbackInterface callback) throws RemoteException {
        this.callback = callback;
    }

    @Override
    public CallbackInterface getCallback() {
        return callback;
    }
}
