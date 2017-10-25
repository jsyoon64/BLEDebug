package com.jsyoon.testbt5;

import java.text.SimpleDateFormat;

public class Const {
    // Message types sent from the DeviceConnector Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    public static final int MY_PERMISSIONS_REQUEST_GPS = 7;
    public static final int REQUEST_ENABLE_BT = 8;
    public static final int REQUEST_SCAN_BT = 9;

    public static final String CRC_OK = "#FFFF00";
    public static final String CRC_BAD = "#FF0000";

    public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";

    public static final SimpleDateFormat timeformat = new SimpleDateFormat("HH:mm:ss.SSS");

    public static final byte[] musicOffCmd = new byte[]{(byte) 0xFF, (byte) 0xFE, (byte) 0xC4, (byte) 0x00, (byte) 0x01};
    public static final byte[] musicOnCmd = new byte[]{(byte) 0xFF, (byte) 0xFE, (byte) 0xC4, (byte) 0x01, (byte) 0x01};

    public static final byte[] modecommand = new byte[]{(byte) 0xFF, (byte) 0xFE, (byte) 0xCB, (byte) 0x00, (byte) 0x00};
}
