package com.jsyoon.testbt5;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.jsyoon.testbt5.BlueTooth.BLEControl;
import com.jsyoon.testbt5.BlueTooth.BluetoothLeService;
import com.jsyoon.testbt5.BlueTooth.DataInterface;
import com.jsyoon.testbt5.BlueTooth.DeviceScanActivity;

public class MainActivity extends AppCompatActivity implements DataInterface {

    private final static String TAG = MainActivity.class.getSimpleName();
    private String mDeviceName;
    private String mDeviceAddress;
    BLEControl bleControl;

    private TextView mDeviceAddressText;
    private TextView mConnectionState;
    private TextView mDataField;

    private ToggleButton RtogB, GtogB, BtogB, MtogB;

    private Button mode_run;
    private Spinner mode_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDeviceAddressText = (TextView) findViewById(R.id.device_address);
        mDataField = (TextView) findViewById(R.id.data_value);
        mConnectionState = (TextView) findViewById(R.id.connection_state);

        initialToggleButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.menu_search) {
            final Intent intent = new Intent(this, DeviceScanActivity.class);
            startActivityForResult(intent, Const.REQUEST_SCAN_BT);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Const.REQUEST_SCAN_BT && resultCode == Activity.RESULT_OK) {

            mDeviceName = data.getStringExtra(Const.EXTRAS_DEVICE_NAME);
            mDeviceAddress = data.getStringExtra(Const.EXTRAS_DEVICE_ADDRESS);

            mDeviceAddressText.setText(mDeviceAddress);

            bleControl = new BLEControl(this, mDeviceName, mDeviceAddress);

            Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
            bindService(gattServiceIntent, bleControl.mServiceConnection, BIND_AUTO_CREATE);

            registerReceiver(bleControl.mGattUpdateReceiver, bleControl.makeGattUpdateIntentFilter());

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void processBinaryData(byte[] data) {
        if ((data[0] == 0xFF) && (data[1] == 0xFE)) {

        }
    }

    @Override
    public void displayData(String data) {
        if (data != null) {
            mDataField.setText(data);
        }
    }

    @Override
    public void connectionState(final int resourceId) {
        mConnectionState.setText(resourceId);
    }

    private void initialToggleButton() {
        MtogB = (ToggleButton) findViewById(R.id.MusictoggleButton);
        RtogB = (ToggleButton) findViewById(R.id.RedLedtoggleButton);
        GtogB = (ToggleButton) findViewById(R.id.GreenLedtoggleButton);
        BtogB = (ToggleButton) findViewById(R.id.BlueLedtoggleButton);

        // Set a checked change listener for toggle button
        MtogB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                byte[] value = Const.CommandFormat;
                value[Const.CmdField] = Const.musicCommand;
                if (isChecked) {
                    value[Const.DataField] = Const.OnData;
                    // if toggle button is enabled/on
                    //MtogB.setBackgroundColor(Color.parseColor("#FF63D486"));
                } else {
                    value[Const.DataField] = Const.OffData;
                    // If toggle button is disabled/off
                    //MtogB.setBackgroundColor(Color.parseColor("#FFD4558C"));
                }
                if (bleControl != null) bleControl.writeData(value);
            }
        });

        // Set a checked change listener for toggle button
        RtogB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                byte[] value = Const.CommandFormat;
                value[Const.CmdField] = Const.ledCommand;
                if (isChecked) {
                    value[Const.DataField] = Const.OnData | Const.REDLedCtr;
                } else {
                    value[Const.DataField] = Const.OffData | Const.REDLedCtr;
                }
                if (bleControl != null) bleControl.writeData(value);
            }
        });

        // Set a checked change listener for toggle button
        GtogB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                byte[] value = Const.CommandFormat;
                value[Const.CmdField] = Const.ledCommand;
                if (isChecked) {
                    value[Const.DataField] = Const.OnData | Const.GREENLedCtr;
                } else {
                    value[Const.DataField] = Const.OffData | Const.GREENLedCtr;
                }
                if (bleControl != null) bleControl.writeData(value);
            }
        });

        // Set a checked change listener for toggle button
        BtogB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                byte[] value = Const.CommandFormat;
                value[Const.CmdField] = Const.ledCommand;
                if (isChecked) {
                    value[Const.DataField] = Const.OnData | Const.BLUELedCtr;
                } else {
                    value[Const.DataField] = Const.OffData | Const.BLUELedCtr;
                }
                if (bleControl != null) bleControl.writeData(value);
            }
        });

        mode_spinner = (Spinner) findViewById(R.id.modespinner);
        mode_run = (Button) findViewById(R.id.btn_run);
        mode_run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] value = Const.CommandFormat;
                value[Const.CmdField] = Const.modeCommand;
                if ("대기모드".equals(String.valueOf(mode_spinner.getSelectedItem()))) {
                    value[Const.DataField] = Const.Idlemode;
                    if (bleControl != null) bleControl.writeData(value);
                } else if ("데모모드".equals(String.valueOf(mode_spinner.getSelectedItem()))) {
                    value[Const.DataField] = Const.Demomode;
                    if (bleControl != null) bleControl.writeData(value);
                }
            }
        });

        // Initially off the third toggle button
        RtogB.setChecked(false);
        GtogB.setChecked(false);
        BtogB.setChecked(false);
        MtogB.setChecked(false);
    }
}
