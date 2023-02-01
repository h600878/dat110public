package no.hvl.dat110.tempsensor;


import no.hvl.dat110.rpcinterface.CallbackInterface;
import no.hvl.dat110.rpcinterface.TempSensorInterface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import static no.hvl.dat110.rpcinterface.TempSensorInterface.N;

public class TemperatureDevice extends Thread {

    private final TemperatureSensor sn;

    public TemperatureDevice() {
        this.sn = new TemperatureSensor();
    }

    public void run() {

        System.out.println("temperature device started");

        try {
            // Get a reference to the registry using the port
            Registry registry = LocateRegistry.getRegistry(TempSensorInterface.SERVER_PORT);

            // Look up the registry for the remote object (TempSensorInterface) using the name TempSensorInterface.REMOTE_IFACE_NAME
            TempSensorInterface stub = (TempSensorInterface) registry.lookup(TempSensorInterface.REMOTE_IFACE_NAME);

            // Loop N times and read the temp value from the TemperatureSensor each time
            for (int i = 0; i < N; i++) {

                CallbackInterface callback = new TemperatureCallback();
                stub.setCallback(callback);

                Thread thread = new Thread(() -> {
                    int temp = sn.read();
                    // Set the temperature value by calling the setTemperature remote method via the object of TempSensorInterface
                    try {
                        stub.setTemperature(temp);
                    }
                    catch (RemoteException e) {
                        e.printStackTrace();
                    }

                });

                thread.start();

                while (!callback.isNotified()) {
                    System.out.println("Waiting for callback...");
                    //noinspection BusyWait
                    Thread.sleep(100);
                }

            }

        }
        catch (RemoteException | NotBoundException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}
