package com.jsyoon.testbt5.data;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableByte;
import android.databinding.ObservableField;
import android.databinding.ObservableFloat;
import android.databinding.ObservableInt;

import com.jsyoon.testbt5.Const;

public class Rxdata extends BaseObservable {
    public ObservableByte LeadOff = new ObservableByte();
    public ObservableField<String> OpMode = new ObservableField<>();
    public ObservableByte OpModebyte = new ObservableByte();
    public ObservableByte Batt = new ObservableByte();
    public ObservableByte Temp = new ObservableByte();
    public ObservableByte ABL = new ObservableByte();
    public ObservableByte EDA = new ObservableByte();
    public ObservableByte MIC = new ObservableByte();
    public ObservableInt PPG = new ObservableInt();
    public ObservableInt SpO2 = new ObservableInt();
    private ObservableFloat EEG_A = new ObservableFloat();
    private ObservableFloat EEG_B = new ObservableFloat();
    private ObservableFloat EEG_D = new ObservableFloat();
    private ObservableFloat EEG_T = new ObservableFloat();
    public ObservableInt ACC_X = new ObservableInt();
    public ObservableInt ACC_Y = new ObservableInt();
    public ObservableInt ACC_Z = new ObservableInt();

    public void setOpMode(byte mode) {
        this.OpModebyte.set(mode);
        byte data = OpModebyte.get();
        switch (data) {
            case Const.Idlemode:
                OpMode.set("대기모드");
                break;
            case Const.Standbymode:
                OpMode.set("준비모드");
                break;
            case Const.Startmode:
                OpMode.set("시작모드");
                break;
            case Const.Demomode:
                OpMode.set("데모모드");
                break;
            default:
                OpMode.set("그외모드");
                break;
        }
    }

    @Bindable
    public String getEEG_A() {
        Float data = EEG_A.get();
        return Float.toString(data);
    }

    @Bindable
    public String getEEG_B() {
        Float data = EEG_B.get();
        return Float.toString(data);
    }

    @Bindable
    public String getEEG_D() {
        Float data = EEG_D.get();
        return Float.toString(data);
    }

    @Bindable
    public String getEEG_T() {
        Float data = EEG_T.get();
        return Float.toString(data);
    }

    public String getHexStringbyte(ObservableByte val) {
        byte data = val.get();
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%02X ", data));
        return sb.toString();
    }
}
