package com.jsyoon.testbt5.utils;

public class utils {
    public static int byteToUnsignedInt(byte b) {
        return 0x00 << 24 | b & 0xff;
    }

    public static final long fourBytesToLong(byte[] value)  {
        if (value.length < 4) {
            return (long)0;
        }
        int temp0 = value[0] & 0xFF;
        int temp1 = value[1] & 0xFF;
        int temp2 = value[2] & 0xFF;
        int temp3 = value[3] & 0xFF;
        return (((long)temp0 << 24) + (temp1 << 16) + (temp2 << 8) + temp3);
    }


}
