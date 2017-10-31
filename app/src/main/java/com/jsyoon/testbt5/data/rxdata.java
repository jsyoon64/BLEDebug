package com.jsyoon.testbt5.data;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableByte;
import android.databinding.ObservableField;
import android.databinding.ObservableFloat;
import android.databinding.ObservableInt;

import com.jsyoon.testbt5.Const;
import com.jsyoon.testbt5.BR;

public class rxdata extends BaseObservable {
    /*
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
    */
    private byte leadoff;
    private byte opmodebyte = 0;
    private String opmode;
    private int batt;
    private int temp;
    private int abl;
    private int eda;
    private int mic;
    private int ppg;
    private int spo2;
    private float eeg_a;
    private float eeg_b;
    private float eeg_d;
    private float eeg_t;
    private int acc_x;
    private int acc_y;
    private int acc_z;

    @Bindable
    public String getLeadoff() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%02X ", leadoff));
        return sb.toString();
    }

    public void setLeadoff(byte val) {
        leadoff = val;
        notifyPropertyChanged(BR.leadoff);
    }

    @Bindable
    public String getOpmodebyte() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%02X ", opmodebyte));
        return sb.toString();
    }

    public void setOpmodebyte(byte val) {
        opmodebyte = val;
        notifyPropertyChanged(BR.opmodebyte);
        setOpmodeString(val);
    }

    @Bindable
    public String getOpmode() {
        return opmode == null ? "" : opmode.toString();
    }

    public void setOpmode(String val) {
        opmode = val;
        notifyPropertyChanged(BR.opmode);
    }

    @Bindable
    public String getTemp() {
        return String.valueOf(temp);
    }

    public void setTemp(int val) {
        temp = val;
        notifyPropertyChanged(BR.temp);
    }

    @Bindable
    public String getBatt() {
        return String.valueOf(batt);
    }

    public void setBatt(int val) {
        batt = val;
        notifyPropertyChanged(BR.batt);
    }

    @Bindable
    public String getAbl() {
        return String.valueOf(abl);
    }

    public void setAbl(int val) {
        abl = val;
        notifyPropertyChanged(BR.abl);
    }

    @Bindable
    public String getEda() {
        return String.valueOf(eda);
    }

    public void setEda(int val) {
        eda = val;
        notifyPropertyChanged(BR.eda);
    }

    @Bindable
    public String getMic() {
        return String.valueOf(mic);
    }

    public void setMic(int val) {
        mic = val;
        notifyPropertyChanged(BR.mic);
    }

    @Bindable
    public String getPpg() {
        return String.valueOf(ppg);
    }

    public void setPpg(int val) {
        ppg = val;
        notifyPropertyChanged(BR.ppg);
    }

    @Bindable
    public String getSpo2() {
        return String.valueOf(spo2);
    }

    public void setSpo2(int val) {
        spo2 = val;
        notifyPropertyChanged(BR.spo2);
    }

    @Bindable
    public String getEeg_a() {
        return Float.toString(eeg_a);
    }

    public void setEeg_a(float val) {
        eeg_a = val;
        notifyPropertyChanged(BR.eeg_a);
    }

    @Bindable
    public String getEeg_b() {
        return Float.toString(eeg_b);
    }

    public void setEeg_b(float val) {
        eeg_b = val;
        notifyPropertyChanged(BR.eeg_b);
    }

    @Bindable
    public String getEeg_d() {
        return Float.toString(eeg_d);
    }

    public void setEeg_d(float val) {
        eeg_d = val;
        notifyPropertyChanged(BR.eeg_d);
    }

    @Bindable
    public String getEeg_t() {
        return Float.toString(eeg_t);
    }

    public void setEeg_t(float val) {
        eeg_t = val;
        notifyPropertyChanged(BR.eeg_t);
    }

    @Bindable
    public String getAcc_x() {
        return String.valueOf(acc_x);
    }

    public void setAcc_x(int val) {
        acc_x = val;
        notifyPropertyChanged(BR.acc_x);
    }

    @Bindable
    public String getAcc_y() {
        return String.valueOf(acc_y);
    }

    public void setAcc_y(int val) {
        acc_y = val;
        notifyPropertyChanged(BR.acc_y);
    }

    @Bindable
    public String getAcc_z() {
        return String.valueOf(acc_z);
    }

    public void setAcc_z(int val) {
        acc_z = val;
        notifyPropertyChanged(BR.acc_z);
    }

    public String getHexStringbyte(ObservableByte val) {
        byte data = val.get();
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%02X ", data));
        return sb.toString();
    }

    private void setOpmodeString(byte mode) {
        switch (mode) {
            case Const.Idlemode:
                setOpmode("대기");
                break;
            case Const.Standbymode:
                setOpmode("준비");
                break;
            case Const.Startmode:
                setOpmode("시작");
                break;
            case Const.Demomode:
                setOpmode("데모");
                break;
            default:
                setOpmode("그외");
                break;
        }
    }

}
