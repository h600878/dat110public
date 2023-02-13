package no.hvl.dat110.util;

import java.math.BigInteger;
import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Set;

import no.hvl.dat110.rpc.interfaces.NodeInterface;

/**
 * @author tdoy
 * dat110 - DSLab 2
 */
public class FileManager {

    public static BigInteger[] createReplicaFiles(String filename, int nreplicas) {

        if (nreplicas < 1) {
            throw new IllegalArgumentException("nreplicas must be >= 1");
        }
        if (filename == null || filename.isEmpty()) {
            throw new IllegalArgumentException("filename must be non-null and non-empty");
        }

        BigInteger[] replicas = new BigInteger[nreplicas];
        // Task:given a filename, create nreplicas (1 to nreplicas)- idea, append each index to the filename before hash
        for (int i = 0; i < nreplicas; i++) {
            // Hash the replica using the Hash.hashOf() and store it in an array
            replicas[i] = Hash.hashOf(filename + i);
        }

        // Return the replicas as array of BigInteger

        return replicas;
    }

    /**
     * @throws RemoteException
     */
    public void distributeReplicastoPeers(String filename) throws RemoteException {

        // Task: Given a filename, make replicas and distribute them to all active peers such that: pred < replica <= peer

        // create replicas of the filename

        // collect the 5 processes from the Util class

        // iterate over the processes

        // iterate over the replicas

        // for each replica, add the replica to the peer if the condition: pred < replica <= peer is satisfied	(i.e., use Util.checkInterval)

    }

    /**
     * @param filename
     * @return list of active nodes having the replicas of this file
     * @throws RemoteException
     */
    public Set<NodeInterface> requestActiveNodesForFile(String filename) throws RemoteException {

        Set<NodeInterface> peers = new HashSet<>();

        // Task: Given a filename, find all the peers that hold a copy of this file

        // see the distributeReplicastoPeers(filename): same rules.


        return peers;
    }

}
