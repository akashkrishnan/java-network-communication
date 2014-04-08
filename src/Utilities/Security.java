/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author akashkrishnan@aakay.net
 */
public final class Security {

    public static String encrypt(String string) {
        try {
            byte[] bytes = string.getBytes();
            MessageDigest md = MessageDigest.getInstance("SHA");
            md.update(bytes, 0, bytes.length);
            byte[] encryptedBytes = md.digest();
            String encryptedString = toHexString(encryptedBytes);
            return encryptedString;
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return null;
    }

    public static byte[] encrypt(byte[] bytes, String method) throws NoSuchAlgorithmException {
        try {
            MessageDigest md = MessageDigest.getInstance(method);
            md.update(bytes, 0, bytes.length);
            byte[] encryptedBytes = md.digest();
            return encryptedBytes;
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return null;
    }

    public static String toHexString(byte[] buf) {
        char[] hexChar = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        StringBuilder strBuf = new StringBuilder(buf.length * 2);
        for (int i = 0; i < buf.length; i++) {
            strBuf.append(hexChar[(buf[i] & 0xf0) >>> 4]);
            strBuf.append(hexChar[buf[i] & 0x0f]);
        }
        return strBuf.toString();
    }
}
