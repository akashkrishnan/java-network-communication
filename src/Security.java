


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * @author ak@aakay.net
 */
public final class Security {

    public static char[] hexChars = "0123456789ABCDEF".toCharArray();

    public static String encrypt(String str) {
        return toHexString(encrypt(str.getBytes()));
    }

    public static byte[] encrypt(byte[] bytes) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(bytes, 0, bytes.length);
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String toHexString(byte[] buf) {
        StringBuilder sb = new StringBuilder(buf.length * 2);
        for (int i = 0; i < buf.length; i++) {
            sb.append(hexChars[(buf[i] & 0xf0) >>> 4]);
            sb.append(hexChars[buf[i] & 0x0f]);
        }
        return sb.toString();
    }
}
