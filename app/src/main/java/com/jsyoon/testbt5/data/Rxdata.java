package com.jsyoon.testbt5.data;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.jsyoon.testbt5.Const;

public class Rxdata extends BaseObservable {
    public byte LeadOff;
    public String OpMode;
    public byte OpModebyte;
    public byte Batt;
    public byte Temp;
    public byte ABL;
    public byte EDA;
    public byte MIC;
    public int PPG;
    public int SpO2;
    private float EEG_A;
    private float EEG_B;
    private float EEG_D;
    private float EEG_T;
    public int ACC_X;
    public int ACC_Y;
    public int ACC_Z;

    public void setOpMode(byte mode) {
        this.OpModebyte = mode;
        switch(mode){
            case Const.Idlemode: OpMode = "대기모드";break;
            case Const.Standbymode: OpMode = "준비모드";break;
            case Const.Startmode: OpMode = "시작모드";break;
            case Const.Demomode: OpMode = "데모모드";break;
            default:OpMode = "그외모드";break;
        }
        notifyPropertyChanged(com.jsyoon.testbt5.BR.opMode);
    }

    @Bindable
    public String getEEG_A() {
        return Float.toString(EEG_A);
    }
    @Bindable
    public String getEEG_B() {
        return Float.toString(EEG_B);
    }
    @Bindable
    public String getEEG_D() {
        return Float.toString(EEG_D);
    }
    @Bindable
    public String getEEG_T() {
        return Float.toString(EEG_T);
    }

    @Bindable
    public String getOpMode() {
        return this.OpMode;
    }
}
