package com.example.android.timemachine;

import android.net.Uri;

/**
 * Created by Juraj on 13.4.2017.
 */

public class AlarmSettings {
    private int alarmID;
    private String alarmRingtone;
    private int alarmvibration;
    private int alarmHour, alarmMinute;

    AlarmSettings() {}

    AlarmSettings(int id, String ringtone,int vibration, int hour, int minute)
    {
        alarmID = id;
        alarmRingtone = ringtone;
        alarmvibration = vibration;
        alarmHour = hour;
        alarmMinute = minute;
    }

    public int getAlarmID() {
        return alarmID;
    }

    public void setAlarmID(int alarmID) {
        this.alarmID = alarmID;
    }

    public String getAlarmRingtone() {
        return alarmRingtone;
    }

    public int isAlarmvibration() {
        return alarmvibration;
    }

    public int getAlarmHour() {
        return alarmHour;
    }

    public int getAlarmMinute() {
        return alarmMinute;
    }

    public void setAlarmRingtone(String alarmRingtone) {
        this.alarmRingtone = alarmRingtone;
    }

    public void setAlarmvibration(int alarmvibration) {
        this.alarmvibration = alarmvibration;
    }

    public void setAlarmHour(int alarmHour) {
        this.alarmHour = alarmHour;
    }

    public void setAlarmMinute(int alarmMinute) {
        this.alarmMinute = alarmMinute;
    }
}