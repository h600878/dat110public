package no.hvl.dat110.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * exercise/demo purpose in dat110
 *
 * @author tdoy
 */

public class Hash {

    /**
     * Task: Hash a given string using MD5 and return the result as a BigInteger.
     */
    public static BigInteger hashOf(String entity) {

        BigInteger hashint = null;
        try {
            // we use MD5 with 128 bits digest
            MessageDigest digest = MessageDigest.getInstance("MD5");
            // compute the hash of the input 'entity'
            byte[] bytes = digest.digest(entity.getBytes());
            // convert the hash into hex format
//            String hex = toHex(bytes);
            // convert the hex into BigInteger
            hashint = new BigInteger(1, /*hex.getBytes()*/bytes); // FIXME Virker bare uten hex

        }
        catch (NoSuchAlgorithmException e) {
            e.getMessage();
        }

        // return the BigInteger
        return hashint;
    }

    /**
     * TODO
     * Task: compute the address size of MD5
     */
    public static BigInteger addressSize() {


        // get the digest length (Note: make this method independent of the class variables)

        // compute the number of bits = digest length * 8

        // compute the address size = 2 ^ number of bits

        // return the address size

        return null;
    }

    public static String toHex(byte[] digest) {
        StringBuilder strbuilder = new StringBuilder();
        for (byte b : digest) {
            strbuilder.append(String.format("%02x", b & 0xff));
        }
        return strbuilder.toString();
    }

}
