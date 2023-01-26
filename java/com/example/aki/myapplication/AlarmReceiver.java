package com.example.aki.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent startActivityIntent = new Intent(context, PlayAlarmActivity.class);
        startActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(startActivityIntent);
        Log.d("alarmSpeak","broadcastReciever Tootta");
        com.example.aki.myapplication.PlayAlarmActivity.reachPlaySound = false;
        while(!com.example.aki.myapplication.PlayAlarmActivity.reachPlaySound){
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}
