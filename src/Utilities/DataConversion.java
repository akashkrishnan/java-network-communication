/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author akashkrishnan@aakay.net
 */
public final class DataConversion {

    public static byte[] toByteArray(byte data) {
        return new byte[]{data};
    }

    public static byte[] toByteArray(Byte data) {
        return toByteArray(data.byteValue());
    }

    public static byte[] toByteArray(short data) {
        return new byte[]{
                    (byte) ((data >> 8) & 0xff),
                    (byte) ((data >> 0) & 0xff)};
    }

    public static byte[] toByteArray(Short data) {
        return toByteArray(data.shortValue());
    }

    public static byte[] toByteArray(short[] data) {
        if (data == null) {
            return null;
        }
        byte[] byts = new byte[data.length * 2];
        for (int i = 0; i < data.length; i++) {
            System.arraycopy(toByteArray(data[i]), 0, byts, i * 2, 2);
        }
        return byts;
    }

    public static byte[] toByteArray(Short[] data) {
        short[] temp = new short[data.length];
        for (int i = 0; i < data.length; i++) {
            temp[i] = data[i].shortValue();
        }
        return toByteArray(temp);
    }

    public static byte[] toByteArray(char data) {
        return new byte[]{
                    (byte) ((data >> 8) & 0xff),
                    (byte) ((data >> 0) & 0xff)};
    }

    public static byte[] toByteArray(Character data) {
        return toByteArray(data.charValue());
    }

    public static byte[] toByteArray(char[] data) {
        if (data == null) {
            return null;
        }
        byte[] byts = new byte[data.length * 2];
        for (int i = 0; i < data.length; i++) {
            System.arraycopy(toByteArray(data[i]), 0, byts, i * 2, 2);
        }
        return byts;
    }

    public static byte[] toByteArray(Character[] data) {
        char[] temp = new char[data.length];
        for (int i = 0; i < data.length; i++) {
            temp[i] = data[i].charValue();
        }
        return toByteArray(temp);
    }

    public static byte[] toByteArray(int data) {
        return new byte[]{
                    (byte) ((data >> 24) & 0xff),
                    (byte) ((data >> 16) & 0xff),
                    (byte) ((data >> 8) & 0xff),
                    (byte) ((data >> 0) & 0xff)
                };
    }

    public static byte[] toByteArray(Integer data) {
        return toByteArray(data.intValue());
    }

    public static byte[] toByteArray(int[] data) {
        if (data == null) {
            return null;
        }
        byte[] byts = new byte[data.length * 4];
        for (int i = 0; i < data.length; i++) {
            System.arraycopy(toByteArray(data[i]), 0, byts, i * 4, 4);
        }
        return byts;
    }

    public static byte[] toByteArray(Integer[] data) {
        int[] temp = new int[data.length];
        for (int i = 0; i < data.length; i++) {
            temp[i] = data[i].intValue();
        }
        return toByteArray(temp);
    }

    public static byte[] toByteArray(long data) {
        return new byte[]{
                    (byte) ((data >> 56) & 0xff),
                    (byte) ((data >> 48) & 0xff),
                    (byte) ((data >> 40) & 0xff),
                    (byte) ((data >> 32) & 0xff),
                    (byte) ((data >> 24) & 0xff),
                    (byte) ((data >> 16) & 0xff),
                    (byte) ((data >> 8) & 0xff),
                    (byte) ((data >> 0) & 0xff)
                };
    }

    public static byte[] toByteArray(Long data) {
        return toByteArray(data.longValue());
    }

    public static byte[] toByteArray(long[] data) {
        if (data == null) {
            return null;
        }
        byte[] byts = new byte[data.length * 8];
        for (int i = 0; i < data.length; i++) {
            System.arraycopy(toByteArray(data[i]), 0, byts, i * 8, 8);
        }
        return byts;
    }

    public static byte[] toByteArray(Long[] data) {
        long[] temp = new long[data.length];
        for (int i = 0; i < data.length; i++) {
            temp[i] = data[i].longValue();
        }
        return toByteArray(temp);
    }

    public static byte[] toByteArray(float data) {
        return toByteArray(Float.floatToRawIntBits(data));
    }

    public static byte[] toByteArray(Float data) {
        return toByteArray(data.floatValue());
    }

    public static byte[] toByteArray(float[] data) {
        if (data == null) {
            return null;
        }
        byte[] byts = new byte[data.length * 4];
        for (int i = 0; i < data.length; i++) {
            System.arraycopy(toByteArray(data[i]), 0, byts, i * 4, 4);
        }
        return byts;
    }

    public static byte[] toByteArray(Float[] data) {
        float[] temp = new float[data.length];
        for (int i = 0; i < data.length; i++) {
            temp[i] = data[i].floatValue();
        }
        return toByteArray(temp);
    }

    public static byte[] toByteArray(double data) {
        return toByteArray(Double.doubleToRawLongBits(data));
    }

    public static byte[] toByteArray(Double data) {
        return toByteArray(data.doubleValue());
    }

    public static byte[] toByteArray(double[] data) {
        if (data == null) {
            return null;
        }
        byte[] byts = new byte[data.length * 8];
        for (int i = 0; i < data.length; i++) {
            System.arraycopy(toByteArray(data[i]), 0, byts, i * 8, 8);
        }
        return byts;
    }

    public static byte[] toByteArray(Double[] data) {
        double[] temp = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            temp[i] = data[i].doubleValue();
        }
        return toByteArray(temp);
    }

    public static byte[] toByteArray(boolean data) {
        return new byte[]{(byte) (data ? 0x01 : 0x00)};
    }

    public static byte[] toByteArray(Boolean data) {
        return toByteArray(data.booleanValue());
    }

    public static byte[] toByteArray(boolean[] data) {
        if (data == null) {
            return null;
        }
        int len = data.length;
        byte[] lena = toByteArray(len);
        byte[] byts = new byte[lena.length + (len / 8) + (len % 8 != 0 ? 1 : 0)];
        System.arraycopy(lena, 0, byts, 0, lena.length);
        for (int i = 0, j = lena.length, k = 7; i < data.length; i++) {
            byts[j] |= (data[i] ? 1 : 0) << k--;
            if (k < 0) {
                j++;
                k = 7;
            }
        }
        return byts;
    }

    public static byte[] toByteArray(Boolean[] data) {
        boolean[] temp = new boolean[data.length];
        for (int i = 0; i < data.length; i++) {
            temp[i] = data[i].booleanValue();
        }
        return toByteArray(temp);
    }

    public static byte[] toByteArray(String data) {
        return (data == null) ? null : data.getBytes();
    }

    public static byte[] toByteArray(String[] data) {
        if (data == null) {
            return null;
        }
        int totalLength = 0;
        int bytesPos = 0;
        byte[] dLen = toByteArray(data.length);
        totalLength += dLen.length;
        int[] sLens = new int[data.length];
        totalLength += (sLens.length * 4);
        byte[][] strs = new byte[data.length][];
        for (int i = 0; i < data.length; i++) {
            if (data[i] != null) {
                strs[i] = toByteArray(data[i]);
                sLens[i] = strs[i].length;
                totalLength += strs[i].length;
            } else {
                sLens[i] = 0;
                strs[i] = new byte[0];
            }
        }
        byte[] bytes = new byte[totalLength];
        System.arraycopy(dLen, 0, bytes, 0, 4);
        byte[] bsLens = toByteArray(sLens);
        System.arraycopy(bsLens, 0, bytes, 4, bsLens.length);
        bytesPos += 4 + bsLens.length;
        for (byte[] sba : strs) {
            System.arraycopy(sba, 0, bytes, bytesPos, sba.length);
            bytesPos += sba.length;
        }
        return bytes;
    }

    public static byte[] toByteArray(Object obj) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            oos.close();
            bos.close();
            byte[] data = bos.toByteArray();
            return data;
        } catch (Exception ex) {
            System.out.println(ex);
            return null;
        }
    }

    public static byte toByte(byte[] data) {
        return (data == null || data.length == 0) ? 0x0 : data[0];
    }

    public static short toShort(byte[] data) {
        if (data == null || data.length != 2) {
            return 0x0;
        }
        return (short) ((0xff & data[0]) << 8
                | (0xff & data[1]) << 0);
    }

    public static short[] toShortArray(byte[] data) {
        if (data == null || data.length % 2 != 0) {
            return null;
        }
        short[] shts = new short[data.length / 2];
        for (int i = 0; i < shts.length; i++) {
            shts[i] = toShort(new byte[]{
                        data[(i * 2)],
                        data[(i * 2) + 1]
                    });
        }
        return shts;
    }

    public static char toChar(byte[] data) {
        if (data == null || data.length != 2) {
            return 0x0;
        }
        return (char) ((0xff & data[0]) << 8
                | (0xff & data[1]) << 0);
    }

    public static char[] toCharArray(byte[] data) {
        if (data == null || data.length % 2 != 0) {
            return null;
        }
        char[] chrs = new char[data.length / 2];
        for (int i = 0; i < chrs.length; i++) {
            chrs[i] = toChar(new byte[]{
                        data[(i * 2)],
                        data[(i * 2) + 1],});
        }
        return chrs;
    }

    public static int toInt(byte[] data) {
        if (data == null || data.length != 4) {
            return 0x0;
        }
        return (int) ((0xff & data[0]) << 24
                | (0xff & data[1]) << 16
                | (0xff & data[2]) << 8
                | (0xff & data[3]) << 0);
    }

    public static int[] toIntArray(byte[] data) {
        if (data == null || data.length % 4 != 0) {
            return null;
        }
        int[] ints = new int[data.length / 4];
        for (int i = 0; i < ints.length; i++) {
            ints[i] = toInt(new byte[]{
                        data[(i * 4)],
                        data[(i * 4) + 1],
                        data[(i * 4) + 2],
                        data[(i * 4) + 3],});
        }
        return ints;
    }

    public static long toLong(byte[] data) {
        if (data == null || data.length != 8) {
            return 0x0;
        }
        return (long) ((long) (0xff & data[0]) << 56
                | (long) (0xff & data[1]) << 48
                | (long) (0xff & data[2]) << 40
                | (long) (0xff & data[3]) << 32
                | (long) (0xff & data[4]) << 24
                | (long) (0xff & data[5]) << 16
                | (long) (0xff & data[6]) << 8
                | (long) (0xff & data[7]) << 0);
    }

    public static long[] toLongArray(byte[] data) {
        if (data == null || data.length % 8 != 0) {
            return null;
        }
        long[] lngs = new long[data.length / 8];
        for (int i = 0; i < lngs.length; i++) {
            lngs[i] = toLong(new byte[]{
                        data[(i * 8)],
                        data[(i * 8) + 1],
                        data[(i * 8) + 2],
                        data[(i * 8) + 3],
                        data[(i * 8) + 4],
                        data[(i * 8) + 5],
                        data[(i * 8) + 6],
                        data[(i * 8) + 7],});
        }
        return lngs;
    }

    public static float toFloat(byte[] data) {
        if (data == null || data.length != 4) {
            return 0x0;
        }
        return Float.intBitsToFloat(toInt(data));
    }

    public static float[] toFloatArray(byte[] data) {
        if (data == null || data.length % 4 != 0) {
            return null;
        }
        float[] flts = new float[data.length / 4];
        for (int i = 0; i < flts.length; i++) {
            flts[i] = toFloat(new byte[]{
                        data[(i * 4)],
                        data[(i * 4) + 1],
                        data[(i * 4) + 2],
                        data[(i * 4) + 3],});
        }
        return flts;
    }

    public static double toDouble(byte[] data) {
        if (data == null || data.length != 8) {
            return 0x0;
        }
        return Double.longBitsToDouble(toLong(data));
    }

    public static double[] toDoubleArray(byte[] data) {
        if (data == null) {
            return null;
        }
        if (data.length % 8 != 0) {
            return null;
        }
        double[] dbls = new double[data.length / 8];
        for (int i = 0; i < dbls.length; i++) {
            dbls[i] = toDouble(new byte[]{
                        data[(i * 8)],
                        data[(i * 8) + 1],
                        data[(i * 8) + 2],
                        data[(i * 8) + 3],
                        data[(i * 8) + 4],
                        data[(i * 8) + 5],
                        data[(i * 8) + 6],
                        data[(i * 8) + 7],});
        }
        return dbls;
    }

    public static boolean toBoolean(byte[] data) {
        return (data == null || data.length == 0) ? false : data[0] != 0x00;
    }

    public static boolean[] toBooleanArray(byte[] data) {
        if (data == null || data.length < 4) {
            return null;
        }
        int len = toInt(new byte[]{data[0], data[1], data[2], data[3]});
        boolean[] bools = new boolean[len];
        for (int i = 0, j = 4, k = 7; i < bools.length; i++) {
            bools[i] = ((data[j] >> k--) & 0x01) == 1;
            if (k < 0) {
                j++;
                k = 7;
            }
        }
        return bools;
    }

    public static String toString(byte[] data) {
        return (data == null) ? null : new String(data);
    }

    public static String[] toStringArray(byte[] data) {
        if (data == null || data.length < 4) {
            return null;
        }
        byte[] bBuff = new byte[4];
        System.arraycopy(data, 0, bBuff, 0, 4);
        int saLen = toInt(bBuff);
        if (data.length < (4 + (saLen * 4))) {
            return null;
        }
        bBuff = new byte[saLen * 4];
        System.arraycopy(data, 4, bBuff, 0, bBuff.length);
        int[] sLens = toIntArray(bBuff);
        if (sLens == null) {
            return null;
        }
        String[] strs = new String[saLen];
        for (int i = 0, dataPos = 4 + (saLen * 4); i < saLen; i++) {
            if (sLens[i] > 0) {
                if (data.length >= (dataPos + sLens[i])) {
                    bBuff = new byte[sLens[i]];
                    System.arraycopy(data, dataPos, bBuff, 0, sLens[i]);
                    dataPos += sLens[i];
                    strs[i] = toString(bBuff);
                } else {
                    return null;
                }
            }
        }
        return strs;
    }

    public static Object toObject(byte[] bytes) {
        Object object = null;
        try {
            object = new java.io.ObjectInputStream(new java.io.ByteArrayInputStream(bytes)).readObject();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return object;
    }

    public static Byte[] toObjectArray(byte[] data) {
        Byte[] temp = new Byte[data.length];
        for (int i = 0; i < data.length; i++) {
            temp[i] = new Byte(data[i]);
        }
        return temp;
    }

    public static Short[] toObjectArray(short[] data) {
        Short[] temp = new Short[data.length];
        for (int i = 0; i < data.length; i++) {
            temp[i] = new Short(data[i]);
        }
        return temp;
    }

    public static Character[] toObjectArray(char[] data) {
        Character[] temp = new Character[data.length];
        for (int i = 0; i < data.length; i++) {
            temp[i] = new Character(data[i]);
        }
        return temp;
    }

    public static Integer[] toObjectArray(int[] data) {
        Integer[] temp = new Integer[data.length];
        for (int i = 0; i < data.length; i++) {
            temp[i] = new Integer(data[i]);
        }
        return temp;
    }

    public static Long[] toObjectArray(long[] data) {
        Long[] temp = new Long[data.length];
        for (int i = 0; i < data.length; i++) {
            temp[i] = new Long(data[i]);
        }
        return temp;
    }

    public static Float[] toObjectArray(float[] data) {
        Float[] temp = new Float[data.length];
        for (int i = 0; i < data.length; i++) {
            temp[i] = new Float(data[i]);
        }
        return temp;
    }

    public static Double[] toObjectArray(double[] data) {
        Double[] temp = new Double[data.length];
        for (int i = 0; i < data.length; i++) {
            temp[i] = new Double(data[i]);
        }
        return temp;
    }

    public static Boolean[] toObjectArray(boolean[] data) {
        Boolean[] temp = new Boolean[data.length];
        for (int i = 0; i < data.length; i++) {
            temp[i] = data[i];
        }
        return temp;
    }

    public static String toHexString(byte b) {
        return Integer.toString((b & 0xff) + 0x100, 16).substring(1);
    }

    public static String toHexString(byte[] b) {
        String temp = null;
        if (b != null && b.length > 0) {
            temp = toHexString(b[0]);
            for (int i = 1; i < b.length; i++) {
                temp += " " + toHexString(b[i]);
            }
        }
        return temp;
    }
}
