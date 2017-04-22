package com.example.android.timemachine;

import android.animation.ObjectAnimator;
import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity implements AddAlarmFragment.AlarmInterface {

    private ListView alarmListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        // Remember that you should never show the action bar if the
        // status bar is hidden, so hide that too if necessary.
        getSupportActionBar().hide();


        /* ActionBar initialization
        ActionBar actionBar = getSupportActionBar();
        // Hide ActionBar Title
        actionBar.setDisplayShowTitleEnabled(false);
        */

        alarmListView = (ListView) findViewById(R.id.show_alarms_list_view);
        final ArrayList<AlarmSettings> AlarmSettingsList = AlarmSettings.getSettingsFromFile('settings.json', this);
        String [] listItems = new String[AlarmSettingsList.size()];


        final ImageView addAlarmIcon = (ImageView) findViewById(R.id.add_alarm_icon);

        // TextView for showing time
        final TextView timeShow = (TextView) findViewById(R.id.time_text_view_id);


        timeShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        // Showing and updating time
        final Handler handler = new Handler(getMainLooper());
        handler.postDelayed(new Runnable() {
            //pHour = cal.get(Calendar.HOUR_OF_DAY);
            //pMinute = cal.get(Calendar.MINUTE);
            @Override
            public void run() {
                timeShow.setText(new SimpleDateFormat("HH:mm").format(new Date()));
                handler.postDelayed(this, 1000);
                /*
                final Calendar cal = Calendar.getInstance();
                int h = cal.get(Calendar.HOUR_OF_DAY);
                int m = cal.get(Calendar.MINUTE);
                for(int i = 0; i < mAlarmSettings.size();i++)
                {
                    if( h == mAlarmSettings.get(i).getAlarmHour() && m  == mAlarmSettings.get(i).getAlarmMinute());
                    {
                        Toast.makeText(getBaseContext(), h + ":" + m + "-" + mAlarmSettings.get(i).getAlarmHour() + ":" +  mAlarmSettings.get(i).getAlarmMinute(), Toast.LENGTH_SHORT).show();
                    }
                }
                */
            }
        }, 10);

        // Add Alarm Icon + On click
        addAlarmIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.add_alarm_in_anim, R.anim.add_alarm_out_anim, R.anim.add_alarm_in_anim, R.anim.add_alarm_out_anim);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(android.R.id.content, new AddAlarmFragment()).commit();


            }

        });


    }

    public void setAlarmSettings(AlarmSettings settings)
    {

        mAlarmSettings.add(settings);
        /*
        Toast.makeText(getBaseContext(), settings.getAlarmHour() + ":" + settings.getAlarmMinute() , Toast.LENGTH_SHORT).show();
        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), settings.getAlarmRingtone());
        Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        if(settings.isAlarmvibration())
            v.vibrate(5000);
        //r.play();
        */
        int hour = settings.getAlarmHour();
        int minute = settings.getAlarmMinute();
        TextView newAlarm = new TextView(this);
        if( hour < 10 && minute < 10 )
            newAlarm.setText("0" + hour + ":" + "0" + minute);
        else if(hour < 10)
            newAlarm.setText("0" + hour + ":" + minute);
        else if(minute < 10 )
            newAlarm.setText(hour + ":" + "0" + minute);
        else
            newAlarm.setText( hour + ":" +  minute);
        newAlarm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);
        newAlarm.setTextColor(getResources().getColor(R.color.primaryDark));
        newAlarm.setGravity(Gravity.CENTER);
        newAlarm.setId(0);
        alarmListView.addView(newAlarm);

        StartAlarmActivty(settings);
    }

    public void StartAlarmActivty(AlarmSettings alarmSettings)
    {
        final Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, alarmSettings.getAlarmHour());
        cal.set(Calendar.MINUTE, alarmSettings.getAlarmMinute());
        cal.set(Calendar.SECOND, 0);
        AlarmManager alarmManager = (AlarmManager) getBaseContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getBaseContext(), AlarmRingingActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, intent, 1);
        alarmManager.setWindow(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 5000,  pendingIntent );
    }


}

// android:background="@drawable/alarm_linear_layout_rounded"