package com.example.aki.myapplication;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.Calendar;


public class MainActivity extends Activity {
    Spinner hourSpinner;
    Spinner minuteSpinner;
    String hourStr;
    String minuteStr;
    public static String alarmNameStr;
    EditText alarmNameEditText;
    TextView setAlarmText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarmNameEditText =(EditText)this.findViewById(R.id.alarmNameEditText);
        hourSpinner = (Spinner)this.findViewById(R.id.hourSpinner);
        minuteSpinner = (Spinner)this.findViewById(R.id.minuteSpinner);
        setAlarmText = (TextView)this.findViewById(R.id.setAlarmText);

        Button setAlarmButton = findViewById(R.id.setAlarmButton);
        timeSettingListener listener = new timeSettingListener();
        setAlarmButton.setOnClickListener(listener);

    }
    private class timeSettingListener implements  View.OnClickListener{
        @Override
        public void onClick(View view){

            alarmNameStr= alarmNameEditText.getText().toString();

            hourStr=(String)hourSpinner.getSelectedItem();
            minuteStr=(String)minuteSpinner.getSelectedItem();

            int hour = Integer.parseInt(hourStr);
            int minute = Integer.parseInt(minuteStr);


            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());

            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);

            Calendar now = Calendar.getInstance();
            now.setTimeInMillis(System.currentTimeMillis());
            
            if (calendar.getTimeInMillis() < now.getTimeInMillis()) {
                calendar.add(Calendar.DATE, 1);
            }

            setAlarmText.setText(alarmNameStr+" [ "+hourStr+" : "+minuteStr+" ] ");

            Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
            PendingIntent pending = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
            AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending); //triggerAtMillis 1000=1sec
        }
    }
}