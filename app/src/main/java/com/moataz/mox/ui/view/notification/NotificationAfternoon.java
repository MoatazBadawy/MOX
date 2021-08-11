package com.moataz.mox.ui.view.notification;

import static android.content.Context.ALARM_SERVICE;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.moataz.mox.R;
import com.moataz.mox.ui.view.activity.MainActivity;

import java.util.Calendar;

public class NotificationAfternoon extends BroadcastReceiver {

    private static final String CHANNEL_ID = "NEW_ARTICLES_AFTERNOON_CHANNEL_ID";

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent notificationIntent = new Intent(context, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(notificationIntent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(context);
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("New Articles Arrived")
                .setContentText("Tap here to read the latest articles for today")
                .setSmallIcon(R.drawable.ic_notification)
                .setLights(Notification.FLAG_SHOW_LIGHTS, 1000, 500)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setStyle(new NotificationCompat.BigTextStyle())
                .build();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID);
        }

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Channel_Afternoon_ID",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.enableLights(true);
            channel.setLightColor(Color.WHITE);
            channel.enableVibration(false);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(0, notification);
    }

    public static void setupAfternoonNotification(Context context) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.HOUR_OF_DAY, 17);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        if (cal.getTimeInMillis() > System.currentTimeMillis()) {
            Intent notificationIntent = new Intent(context, NotificationAfternoon.class);
            @SuppressLint("UnspecifiedImmutableFlag") PendingIntent broadcast = PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 24 * 60 * 60 * 1000, broadcast); //Repeat every 24 h
        }
    }
}