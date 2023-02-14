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
            // Convert the hash into hex format
//            String hex = toHex(bytes);
            // Convert the hex into BigInteger
            hashint = new BigInteger(1, /*hex.getBytes()*/bytes); // FIXME Virker bare uten hex

        }
        catch (NoSuchAlgorithmException e) {
            e.getMessage();
        }

        // Return the hash as BigInteger
        return hashint;
    }

    /**
     * Task: compute the address size of MD5
     */
    public static BigInteger addressSize() {

        BigInteger addressSize = new BigInteger("2");
        try {
            // Get the digest length (Note: make this method independent of the class variables)
            MessageDigest digest = MessageDigest.getInstance("MD5");
            // Compute the number of bits = digest length * 8
            int bits = digest.getDigestLength() * 8;
            // Compute the address size = 2 ^ number of bits
            addressSize = addressSize.pow(bits);
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // Return the address size
        return new BigInteger(String.valueOf(addressSize));
    }

    public static String toHex(byte[] digest) {
        StringBuilder strbuilder = new StringBuilder();
        for (byte b : digest) {
            strbuilder.append(String.format("%02x", b & 0xff));
        }
        return strbuilder.toString();
    }

}
