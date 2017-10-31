package com.jsyoon.testbt5.utils;

public class utils {
    public static int byteToUnsignedInt(byte b) {
        return 0x00 << 24 | b & 0xff;
    }

}
