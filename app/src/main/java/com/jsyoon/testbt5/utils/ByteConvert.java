package com.jsyoon.testbt5.utils;

public class ByteConvert {

    public static int swap(int id) {
        byte[] a = intToByte(id);
        byte[] b = new byte[a.length];

        for (int i = 0; i < a.length; i++) {
            b[a.length - i - 1] = a[i];
        }

        return byteToInt(b);
    }

    public static byte[] intToByte(int id) {
        byte[] b = new byte[4];
        b[0] = (byte) (id >> 24);
        b[1] = (byte) ((id << 8) >> 24);
        b[2] = (byte) ((id << 16) >> 24);
        b[3] = (byte) ((id << 24) >> 24);
        return b;
    }

    public static int byteToInt(byte[] id) {
        int newid = 0;
        for (int i = 0; i < id.length; i++) {
            newid += unsignedByteToInt(id[i]) << (8 * id.length - i);
        }
        return newid;

        /**
         int i = 0;
         int pos = 0;
         i += unsignedByteToInt(id[pos++]) << 24;
         i += unsignedByteToInt(id[pos++]) << 16;
         i += unsignedByteToInt(id[pos++]) << 8;
         i += unsignedByteToInt(id[pos++]) << 0;
         return i;
         **/
    }

    public static int unsignedByteToInt(byte b) {
        return (int) b & 0xFF;
    }
}
