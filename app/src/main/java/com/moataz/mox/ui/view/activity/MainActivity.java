package com.moataz.mox.ui.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.moataz.mox.R;
import com.moataz.mox.ui.view.brush.NotificationReceiver;
import com.moataz.mox.ui.view.fragment.HomeFragment;
import com.moataz.mox.ui.view.brush.Shortcuts;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity  {

    PendingIntent broadcast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeView();
        setupNotification();
        setupShortcuts();
    }

    private void initializeView() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        ViewCompat.setLayoutDirection(getWindow().getDecorView(), ViewCompat.LAYOUT_DIRECTION_LTR);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,
                new HomeFragment()).commit();
    }

    private void setupNotification() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.HOUR_OF_DAY, 8);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND, 0);
        if(cal.getTimeInMillis()>System.currentTimeMillis()){
            Intent notificationIntent = new Intent(MainActivity.this, NotificationReceiver.class);
            broadcast = PendingIntent.getBroadcast(MainActivity.this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
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