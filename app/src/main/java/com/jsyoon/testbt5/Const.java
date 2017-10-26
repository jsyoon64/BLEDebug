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

    public static final int CmdField = 2;
    public static final int DataField = 3;
    public static final byte[] CommandFormat = new byte[]{(byte) 0xFF, (byte) 0xFE, (byte) 0x00, (byte) 0x00};

    /* Data Field : CommandFormat[3]*/
    public  static final byte OffData = (byte)0x00;
    public  static final byte OnData = (byte)0x01;
    public  static final byte Idlemode = (byte)0x01;
    public  static final byte Standbymode = (byte)0x02;
    public  static final byte Startmode = (byte)0x03;
    public  static final byte Demomode = (byte)0x04;
    public  static final byte REDLedCtr = (byte)0xA0;
    public  static final byte GREENLedCtr = (byte)0xB0;
    public  static final byte BLUELedCtr = (byte)0xC0;
    public  static final byte MP3StopCtr = (byte)0x00;
    public  static final byte MP3PauseCtr = (byte)0x01;
    public  static final byte MP3PlayCtr = (byte)0x02;

    /* Command Field : CommandFormat[2]*/
    public static final byte musicCommand =(byte) 0xC4;
    public static final byte modeCommand =(byte) 0xCB;
    public static final byte ledCommand =(byte) 0xC9;
    public static final byte RLEDPWMCommand =(byte) 0xC5;
    public static final byte GLEDPWMCommand =(byte) 0xC6;
    public static final byte BLEDPWMCommand =(byte) 0xC7;
}
