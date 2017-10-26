package com.jsyoon.testbt5.BlueTooth;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;

import com.jsyoon.testbt5.R;

import java.util.List;
import java.util.UUID;

public class BLEControl{
    private BluetoothLeService mBluetoothLeService;
    private final static String TAG = BLEControl.class.getSimpleName();

    private String mDeviceName;
    private String mDeviceAddress;
    private boolean mConnected = false;
    private BluetoothGattCharacteristic mNotifyCharacteristic;
    private BluetoothGattCharacteristic mWriteCharacteristic;
    private DataInterface _iDataHandler = null;

    public BLEControl(Context context, String name, String addr) {
        mDeviceName = name;
        mDeviceAddress = addr;
        _iDataHandler = (DataInterface)context;
    }

    // Code to manage Service lifecycle.
    public final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                Log.e(TAG, "Unable to initialize Bluetooth");
                //finish();
            }
            // Automatically connects to the device upon successful start-up initialization.
            mBluetoothLeService.connect(mDeviceAddress);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
        }
    };

    // Handles various events fired by the Service.
    // ACTION_GATT_CONNECTED: connected to a GATT server.
    // ACTION_GATT_DISCONNECTED: disconnected from a GATT server.
    // ACTION_GATT_SERVICES_DISCOVERED: discovered GATT services.
    // ACTION_DATA_AVAILABLE: received data from the device.  This can be a result of read
    //                        or notification operations.
    public final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                mConnected = true;
                _iDataHandler.connectionState(R.string.msg_connected);
                //updateConnectionState(R.string.msg_connected);
                //invalidateOptionsMenu();
            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                mConnected = false;
                _iDataHandler.connectionState(R.string.disconnected);
                //updateConnectionState(R.string.disconnected);
                //invalidateOptionsMenu();
                //clearUI();
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                // Show all the supported services and characteristics on the user interface.
                //displayGattServices(mBluetoothLeService.getSupportedGattServices());
                selectSleepSenseServices(mBluetoothLeService.getSupportedGattServices());
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                _iDataHandler.displayData(intent.getStringExtra(BluetoothLeService.EXTRA_DATA));
            } else if (BluetoothLeService.BINARY_DATA_AVAILABLE.equals(action)) {
                _iDataHandler.processBinaryData(intent.getByteArrayExtra(BluetoothLeService.EXTRA_DATA));
            }
        }
    };

    public void selectSleepSenseServices(List<BluetoothGattService> supportedGattServices) {
        for (BluetoothGattService gattService : supportedGattServices) {
            if (UUID.fromString(SampleGattAttributes.SLEEPSENSE_SERVICE).equals(gattService.getUuid())) {

                List<BluetoothGattCharacteristic> gattCharacteristics = gattService.getCharacteristics();

                for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {

                    if (UUID.fromString(SampleGattAttributes.SLEEPSENSE_MEASUREMENT).equals(gattCharacteristic.getUuid())) {
                        final int charaProp = gattCharacteristic.getProperties();
                        if ((charaProp & BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
                            // If there is an active notification on a characteristic, clear
                            // it first so it doesn't update the data field on the user interface.
                            if (mNotifyCharacteristic != null) {
                                mBluetoothLeService.setCharacteristicNotification(
                                        mNotifyCharacteristic, false);
                                mNotifyCharacteristic = null;
                            }
                            mBluetoothLeService.readCharacteristic(gattCharacteristic);
                        }
                        if ((charaProp & BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
                            mNotifyCharacteristic = gattCharacteristic;
                            mBluetoothLeService.setCharacteristicNotification(
                                    gattCharacteristic, true);
                        }
                    } else if (UUID.fromString(SampleGattAttributes.SLEEPSENSE_CONTROL_NORESP).equals(gattCharacteristic.getUuid())) {
                        final int charaProp = gattCharacteristic.getProperties();
                        if (((charaProp & BluetoothGattCharacteristic.PROPERTY_WRITE) > 0) ||
                                ((charaProp & BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE) > 0)
                                ) {
                            mWriteCharacteristic = gattCharacteristic;
                        }
                    }
                }
            }
        }
    }

    public static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        intentFilter.addAction(BluetoothLeService.BINARY_DATA_AVAILABLE);
        return intentFilter;
    }

    public void writeData(byte[] value) {
        if( (mWriteCharacteristic != null) && (mBluetoothLeService != null) ){
            mBluetoothLeService.writeCharacteristic(mWriteCharacteristic,value);
            //characteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
        }
    }
}
