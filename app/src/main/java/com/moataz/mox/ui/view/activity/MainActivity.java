package com.moataz.mox.ui.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.moataz.mox.R;
import com.moataz.mox.ui.view.brush.NotificationAfternoon;
import com.moataz.mox.ui.view.brush.NotificationMorning;
import com.moataz.mox.ui.view.fragment.HomeFragment;
import com.moataz.mox.ui.view.brush.Shortcuts;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeView();
        setupMorningNotification();
        setupAfternoonNotification();
        setupShortcuts();
    }

    private void initializeView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // Initialize statue bar
            Window window = getWindow();
            View decorView = window.getDecorView();
            WindowInsetsControllerCompat windowInsetsControllerCompat = new WindowInsetsControllerCompat(window, decorView);
            windowInsetsControllerCompat.setAppearanceLightStatusBars(true);
            window.setStatusBarColor(Color.WHITE);
        }
        ViewCompat.setLayoutDirection(getWindow().getDecorView(), ViewCompat.LAYOUT_DIRECTION_LTR); // make the view just from L to R
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,
                new HomeFragment()).commit();
    }

    private void setupAfternoonNotification() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.HOUR_OF_DAY, 16);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND, 0);
        if(cal.getTimeInMillis()>System.currentTimeMillis()){
            Intent notificationIntent = new Intent(MainActivity.this, NotificationAfternoon.class);
            PendingIntent broadcast = PendingIntent.getBroadcast(MainActivity.this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) MainActivity.this.getSystemService(ALARM_SERVICE);
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 24*60*60*1000, broadcast); //Repeat every 24 h
        }
    }

    private void setupMorningNotification() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.HOUR_OF_DAY, 8);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND, 0);
        if(cal.getTimeInMillis()>System.currentTimeMillis()){
            Intent notificationIntent = new Intent(MainActivity.this, NotificationMorning.class);
            PendingIntent broadcast = PendingIntent.getBroadcast(MainActivity.this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) MainActivity.this.getSystemService(ALARM_SERVICE);
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 24*60*60*1000, broadcast); //Repeat every 24 h
        }
    }

    private void setupShortcuts() {
        Shortcuts.setupShortcuts(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}