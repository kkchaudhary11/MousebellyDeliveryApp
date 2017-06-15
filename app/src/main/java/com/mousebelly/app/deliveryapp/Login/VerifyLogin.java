package com.mousebelly.app.deliveryapp.Login;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by adity on 18-Feb-17.
 */

public class VerifyLogin {

    private static String hmacSha1(String value, String key) {
        try {
            // Get an hmac_sha1 key from the raw key bytes
            byte[] keyBytes = key.getBytes();
            SecretKeySpec signingKey = new SecretKeySpec(keyBytes, "HmacSHA1");

            // Get an hmac_sha1 Mac instance and initialize with the signing key
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);

            // Compute the hmac on input data bytes
            byte[] rawHmac = mac.doFinal(value.getBytes());

            // Convert   raw bytes to Hex
            byte[] hexBytes = new Hex().encode(rawHmac);
            //byte [] hexBytes = new Hex.encode()

            System.out.println("hash tag" + new String(hexBytes, "UTF-8"));
            //  Covert array of Hex bytes to a String
            return new String(hexBytes, "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean compare(String pass, String HashPass) {
        String[] hashPassArray = HashPass.split("\\$");


        return hashPassArray[3].equals(hmacSha1(pass, hashPassArray[1]));
    }
}
