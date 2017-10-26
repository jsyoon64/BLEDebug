package com.jsyoon.testbt5.BlueTooth;

public interface DataInterface {
    void processBinaryData(byte[] data);
    void displayData(String data);
    void connectionState(final int resourceId);
}
