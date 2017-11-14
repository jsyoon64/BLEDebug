package com.jsyoon.testbt5;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
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
import com.jsyoon.testbt5.data.rxdata;
import com.jsyoon.testbt5.databinding.ActivityMainBinding;
import com.jsyoon.testbt5.utils.utils;


public class MainActivity extends AppCompatActivity implements DataInterface {

    private final static String TAG = MainActivity.class.getSimpleName();
    private String mDeviceName;
    private String mDeviceAddress;
    BLEControl bleControl;

    private ToggleButton RtogB, GtogB, BtogB, MtogB;

    private Button mode_run;
    private Spinner mode_spinner;

    ActivityMainBinding binding;
    rxdata rd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initialToggleButton();

        rd = new rxdata(); // your data is created here
        binding.setRxdata(rd); // generated setter based on the data in the layout file
        binding.contents.setRxdata(rd);
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

            binding.contents.deviceAddress.setText(mDeviceAddress);

            bleControl = new BLEControl(this, mDeviceName, mDeviceAddress);

            Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
            bindService(gattServiceIntent, bleControl.mServiceConnection, BIND_AUTO_CREATE);

            registerReceiver(bleControl.mGattUpdateReceiver, bleControl.makeGattUpdateIntentFilter());

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bleControl != null) {
            if (bleControl.mServiceConnection != null) {
                unbindService(bleControl.mServiceConnection);
            }
            bleControl.stopService();
        }
    }


    @Override
    public void processBinaryData(byte[] data) {
        if ((data != null) && (data.length >= 30)) {
            if ((data[0] == (byte) 0xFF) && (data[1] == (byte) 0xFE)) {
                rd.setLeadoff(data[3]);
                rd.setOpmodebyte(data[4]);

                rd.setBatt(utils.byteToUnsignedInt(data[6]));
                rd.setTemp(utils.byteToUnsignedInt(data[7]));
                rd.setAbl(utils.byteToUnsignedInt(data[8]));
                rd.setEda(utils.byteToUnsignedInt(data[9]));
                rd.setMic(utils.byteToUnsignedInt(data[12]));
                rd.setPpg(utils.byteToUnsignedInt(data[13]));
                rd.setSpo2(utils.byteToUnsignedInt(data[14]));

                float val;
                val = (float) ((int) (data[15] << 8) | (int)(data[16] & 0xFF));
                rd.setEeg_d(val);
                val = (float) ((int) (data[17] << 8) | (int)(data[18] & 0xFF));
                rd.setEeg_t(val);
                val = (float) ((int) (data[19] << 8) | (int)(data[20] & 0xFF));
                rd.setEeg_a(val);
                val = (float) ((int) (data[21] << 8) | (int)(data[22] & 0xFF));
                rd.setEeg_b(val);

                int val1;
                val1 = (int)((int) (data[24] << 8) | (int)(data[23] & 0xFF) );
                rd.setAcc_x(val1);

                val1 = (int)((int) (data[26] << 8) | (int)(data[25] & 0xFF));
                rd.setAcc_y(val1);

                val1 = (int)((int) (data[28] << 8) | (int)(data[27] & 0xFF));
                rd.setAcc_z(val1);
            }
        }
    }

    @Override
    public void displayData(String data) {
        if (data != null) {
            binding.contents.dataValue.setText(data);
        }
    }

    @Override
    public void connectionState(final int resourceId) {
        binding.contents.connectionState.setText(resourceId);
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
                    value[Const.DataField] = Const.MP3PlayCtr;
                    // if toggle button is enabled/on
                    //MtogB.setBackgroundColor(Color.parseColor("#FF63D486"));
                } else {
                    value[Const.DataField] = Const.MP3StopCtr;
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
                } else if ("준비모드".equals(String.valueOf(mode_spinner.getSelectedItem()))) {
                    value[Const.DataField] = Const.Standbymode;
                    if (bleControl != null) bleControl.writeData(value);
                } else if ("시작모드".equals(String.valueOf(mode_spinner.getSelectedItem()))) {
                    value[Const.DataField] = Const.Startmode;
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
