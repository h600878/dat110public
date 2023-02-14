/**
 *
 */
package no.hvl.dat110.crack;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.paukov.combinatorics3.Generator;

import no.hvl.dat110.workernodes.Utility;

/**
 * dat110: DS-Lab 2
 *
 * @author tdoy
 */
public class BruteForce {

    /**
     * Permutation with repetition
     * key = #alphabets^lengthOfKey
     *
     * @param keyspace - the set of characters to use
     * @throws NoSuchAlgorithmException - if the algorithm is not found
     */
    public static boolean crackPassword(String[] keyspace, int keylength, String hashtocrack) throws NoSuchAlgorithmException {

        for (List<String> key : Generator.permutation(keyspace).withRepetitions(keylength)) {

            StringBuilder skey = new StringBuilder();
            for (String s : key) {
                skey.append(s);
            }

            boolean found = PasswordUtility.verifyHash(skey.toString(), hashtocrack);
            if (found) {
                System.out.println(skey);
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {

        int[] jobsPasswordLen = {6, 5};
        // Password = s0lbA
        String hashOfPassword = PasswordUtility.generateHashWithoutSalt("s0lbA");
        System.out.println("This is the hash of the password we want to crack " + hashOfPassword);

        // Start to crack - this is a compute intensive task
        System.out.println("Attempting to crack password...");
        boolean found;
        long start = System.currentTimeMillis();
        for (int j : jobsPasswordLen) {
            found = crackPassword(Utility.getKeyspace(), j, hashOfPassword);

            if (found) {
                long end = System.currentTimeMillis();
                long diff = end - start;
                System.out.println("Password found! | search took " + diff + " milliseconds");
                return;
            }
        }

    }

}
