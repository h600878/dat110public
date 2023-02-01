package no.hvl.dat110.display;


import no.hvl.dat110.rpcinterface.CallbackInterface;
import no.hvl.dat110.rpcinterface.TempSensorInterface;
import no.hvl.dat110.tempsensor.TemperatureCallback;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import static no.hvl.dat110.rpcinterface.TempSensorInterface.N;

public class DisplayDevice extends Thread {


    public void run() {

        System.out.println("Display started...");

        try {
            // Get a reference to the registry using the port
            Registry registry = LocateRegistry.getRegistry(TempSensorInterface.SERVER_PORT);

            // Look up the registry for the remote object (TempSensorInterface) using the name TempSensorInterface.REMOTE_IFACE_NAME
            TempSensorInterface stub = (TempSensorInterface) registry.lookup(TempSensorInterface.REMOTE_IFACE_NAME);

            while (stub.getCallback() == null);

            // Loop N times and read the temp value from the TemperatureSensor each time
            for (int i = 0; i < N; i++) {
                // Get the temperature value by calling the getTemperature remote method via the object of TempSensorInterface
                int temp = stub.getTemperature();
                // Print the temperature value to console
                System.out.println("DisplayDevice: Temperature: " + temp);
            }

        }
        catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

    }
}
