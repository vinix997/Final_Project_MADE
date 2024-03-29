package com.example.submission_3.reminder;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;

import com.example.submission_3.MainActivity;
import com.example.submission_3.R;

import java.util.Calendar;

public class DailyReminder extends BroadcastReceiver {

    private static final String NOTIFICATION_CHANNEL_ID = "channel_01";
    private static final String NOTIFICATION_CHANNEL_NAME = "channel_name";
    private static final int NOTIFICATION_ID = 100;

    public DailyReminder() {

    }

    private static PendingIntent getPendingIntent(Context context) {
        Intent intent = new Intent(context, DailyReminder.class);
        return PendingIntent.getBroadcast(context, NOTIFICATION_ID, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String title = context.getString(R.string.app_name);
        String message = context.getString(R.string.notif_message);
        showNotification(context, title, message);
    }

    private void showNotification(Context context, String title, String message) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri uriRingtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSound(uriRingtone)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});

            builder.setChannelId(NOTIFICATION_CHANNEL_ID);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
        Notification notification = builder.build();
        if (notificationManager != null) {
            notificationManager.notify(NOTIFICATION_ID, notification);
        }
    }

    public void setRepeatingAlarm(Context context)
    {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), getPendingIntent(context));
        } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, getPendingIntent(context));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), getPendingIntent(context));
        }

    }

    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(getPendingIntent(context));
    }

}
