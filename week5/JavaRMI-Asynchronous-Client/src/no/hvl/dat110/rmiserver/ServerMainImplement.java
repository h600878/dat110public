package no.hvl.dat110.rmiserver;

import java.io.Serial;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import no.hvl.dat110.rmiinterface.ClientCallbackInterface;
import no.hvl.dat110.rmiinterface.ServerMainInterface;

/**
 * dat110: DS-Lab 2
 *
 * @author tdoy
 */
public class ServerMainImplement extends UnicastRemoteObject implements ServerMainInterface {

    @Serial
    private static final long serialVersionUID = 1L;

    private ClientCallbackInterface clientcallbackobj;

    protected ServerMainImplement() throws RemoteException {
        super();

        Thread shutdownhook = new Thread(() -> System.out.println("RPC Server is now shutting down..."));
        Runtime.getRuntime().addShutdownHook(shutdownhook);
    }

    /**
     * This method registers the clientcallback object that the server will use to invoke the object's method
     *
     * @param clientcallbackobj the clientcallback object that the server can use to invoke the object's method
     * @throws RemoteException if the remote call fails
     */
    @Override
    public void registerClientCallbackObject(ClientCallbackInterface clientcallbackobj) throws RemoteException {
        this.clientcallbackobj = clientcallbackobj;
    }

    /**
     * This method is invoked remotely by client to add two numbers
     *
     * @param a the first number
     * @param b the second number
     * @throws RemoteException if the remote call fails
     */
    public void doOperation(int a, int b) throws RemoteException {

        // Acknowledge client message
        clientcallbackobj.acknowledge("From Server: Message recieved!");        // Send a message back to the client

        try {
            Thread.sleep(8000);  // Wait for 8 sec and then add numbers - simulate long task
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        int result = a + b;

        String resmsg = "Result received from server: " + a + " + " + b + " = " + result;

        clientcallbackobj.notify(resmsg);        // notify client through its registered object
    }

    /**
     * This method is invoked remotely by client to shut the server down
     *
     * @throws RemoteException if the remote call fails
     */
    @Override
    public void shutdown() throws RemoteException {

        System.out.println("RPC Server will shut down in 2 sec...");
        try {
            Thread.sleep(2000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);

    }

}
