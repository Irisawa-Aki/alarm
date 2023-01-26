package com.example.aki.myapplication;

import android.media.SoundPool;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import java.util.Calendar;
import static android.media.AudioManager.STREAM_MUSIC;

public class PlayAlarmActivity extends Activity {

    boolean allReady;
    boolean stopClicked;
    int streamId;
    int[] numberList;
    int[] timeList;
    int[] loadTimeList;
    int[] hiraSoundList;
    int[] alarmNameList;
    int[] loadAlarmNameList;
    String[] hiraStrList;
    String[] alarmNameStrList;
    String inputText;
    SoundPool sp;
    TextView alarmNameTextView;
    public static boolean reachPlaySound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_play_alarm);

        reachPlaySound = true;

        numberList= new int[16];
        numberList[0] = R.raw.sound0;
        numberList[1] = R.raw.sound1;
        numberList[2] = R.raw.sound2;
        numberList[3] = R.raw.sound3;
        numberList[4] = R.raw.sound4;
        numberList[5] = R.raw.sound5;
        numberList[6] = R.raw.sound6;
        numberList[7] = R.raw.sound7;
        numberList[8] = R.raw.sound8;
        numberList[9] = R.raw.sound9;
        numberList[11] = R.raw.sound10;
        numberList[12] = R.raw.sound20;
        numberList[13] = R.raw.sound30;
        numberList[14] = R.raw.sound40;
        numberList[15] = R.raw.sound50;

        hiraStrList = new String[]{"あ","い","う","え","お",
                "か","き","く","け","こ",
                "が","ぎ","ぐ","げ","ご",
                "さ","し","す","せ","そ",
                "ざ","じ","ず","ぜ","ぞ",
                "た","ち","つ","て","と",
                "だ","ぢ","づ","で","ど",
                "な","に","ぬ","ね","の",
                "は","ひ","ふ","へ","ほ",
                "ば","び","ぶ","べ","ぼ",
                "ぱ","ぴ","ぷ","ぺ","ぽ",
                "ま","み","む","め","も",
                "や","ゆ","よ",
                "ら","り","る","れ","ろ",
                "わ","を","ん"
        };

        hiraSoundList = new int[]{R.raw.a,R.raw.i,R.raw.u,R.raw.e,R.raw.o,
                R.raw.ka,R.raw.ki,R.raw.ku,R.raw.ke,R.raw.ko,
                R.raw.ga,R.raw.gi,R.raw.gu,R.raw.ge,R.raw.go,
                R.raw.sa,R.raw.si,R.raw.su,R.raw.se,R.raw.so,
                R.raw.za,R.raw.zi,R.raw.zu,R.raw.ze,R.raw.zo,
                R.raw.ta,R.raw.ti,R.raw.tu,R.raw.te,R.raw.to,
                R.raw.da,R.raw.zi,R.raw.zu,R.raw.de,R.raw.do_sound,
                R.raw.na,R.raw.ni,R.raw.nu,R.raw.ne,R.raw.no,
                R.raw.ha,R.raw.hi,R.raw.hu,R.raw.he,R.raw.ho,
                R.raw.ba,R.raw.bi,R.raw.bu,R.raw.be,R.raw.bo,
                R.raw.pa,R.raw.pi,R.raw.pu,R.raw.pe,R.raw.po,
                R.raw.ma,R.raw.mi,R.raw.mu,R.raw.me,R.raw.mo,
                R.raw.ya,R.raw.yu,R.raw.yo,
                R.raw.ra,R.raw.ri,R.raw.ru,R.raw.re,R.raw.ro,
                R.raw.wa,R.raw.wo,R.raw.n
        };

        sp = new SoundPool(99999999, STREAM_MUSIC, 0);
        stopClicked = false;

        inputText=com.example.aki.myapplication.MainActivity.alarmNameStr;

        alarmNameTextView = (TextView)this.findViewById(R.id.alarmNameTextView);
        alarmNameTextView.setText(inputText);

        Button stopAlarmButton = findViewById(R.id.stopAlarmButton);
        PlayAlarmActivity.alarmStopingListener listener = new PlayAlarmActivity.alarmStopingListener();
        stopAlarmButton.setOnClickListener(listener);

        hiraSet();
        timeSet();

        sp.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int loadId, int status) {
                if (0 == status) {

                    allReady = true;
                    for (int i = 0;i< timeList.length;i++) {
                        if (timeList[i] == loadId) {
                            loadTimeList[i]= 1;
                        } else if (loadTimeList[i] == 0){
                            allReady = false;
                        }
                    }

                    for (int i = 1;i< loadAlarmNameList.length;i++) {
                        if (alarmNameList[i] == loadId) {
                            loadAlarmNameList[i]= 1;
                        } else if (loadAlarmNameList[i] == 0){
                            allReady = false;
                        }
                    }
                    if (allReady){
                    playSound();
                    }
                }
            }
        });
    }
    void timeSet(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        timeList= new int[6];
        loadTimeList= new int[6];
        final int soundhour10;
        final int soundhour1;
        final int soundminute10;
        final int soundminute1;


        //時
        loadTimeList[0]= 9;
        if (hour / 10 != 0){
            soundhour10 = sp.load(getApplicationContext(), numberList[hour / 10 + 10], 1);
            timeList[0] = soundhour10;
            loadTimeList[0]= 0;
        }

        loadTimeList[1]= 9;
        if ((hour % 10) != 0){
            soundhour1 = sp.load(getApplicationContext(), numberList[hour % 10], 1);
            timeList[1] = soundhour1;
            loadTimeList[1] = 0;
        } else if (hour == 0){
            soundhour1 = sp.load(getApplicationContext(), numberList[0], 1);
            timeList[1] = soundhour1;
            loadTimeList[1] = 0;
        }

        timeList[2] = sp.load(getApplicationContext(), R.raw.zi1, 1);
        loadTimeList[2]= 0;

        //分
        loadTimeList[3]= 9;
        if (minute / 10 != 0){
            soundminute10 = sp.load(getApplicationContext(), numberList[minute / 10 + 10], 1);
            timeList[3] = soundminute10;
            loadTimeList[3]= 0;
        }

        loadTimeList[4]= 9;
        if ((minute % 10) != 0){
            soundminute1 = sp.load(getApplicationContext(), numberList[minute % 10], 1);
            timeList[4] = soundminute1;
            loadTimeList[4] = 0;
        } else if (minute == 0){
            soundminute1 = sp.load(getApplicationContext(), numberList[0], 1);
            timeList[4] = soundminute1;
            loadTimeList[4] = 0;
        }

        timeList[5] = sp.load(getApplicationContext(), R.raw.fun1, 1);
        loadTimeList[5]= 0;
    }

    void hiraSet(){
        alarmNameStrList = inputText.split("");
        alarmNameList= new int[alarmNameStrList.length];
        loadAlarmNameList= new int[alarmNameStrList.length];
        for (int i = 1; i < alarmNameStrList.length; i++) {
            for (int j = 0; j < hiraStrList.length; j++) {
                if (alarmNameStrList[i].equals(hiraStrList[j])){
                    alarmNameList[i]  = sp.load(getApplicationContext(), hiraSoundList[j], 1);
                    loadAlarmNameList[i]= 0;
                }
            }
        }

    }
    void playSound(){
        Log.d("soundSPEAK","playsound!");
        for (int i = 0; i < timeList.length; i++) {
            if (stopClicked) {
                break;
            }
            if (loadTimeList[i] != 9) {
                streamId = sp.play(timeList[i], 1.0F, 1.0F, 0, 0, 1.0F);
                try {
                    if(i == 2 || i == 5){
                        Thread.sleep(600);
                    } else{
                        Thread.sleep(800);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        for (int i = 1; i < alarmNameList.length; i++) {
            if (stopClicked) {
                break;
            }
            streamId = sp.play(alarmNameList[i], 1.0F, 1.0F, 0, 0, 1.0F);
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (!stopClicked) {
            timeSet();
        }

    }

    private class alarmStopingListener implements  View.OnClickListener {
        @Override
        public void onClick(View view) {
            sp.stop(streamId);
            stopClicked = true;
        }
    }
}
