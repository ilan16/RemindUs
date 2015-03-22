package com.remindus.projetcommun.remindus.Service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.os.SystemClock;
import android.widget.Toast;

import com.remindus.projetcommun.remindus.MainActivity;
import com.remindus.projetcommun.remindus.R;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by kevin on 21/03/2015.
 */
public class Jesa extends Service {

    @Override
    public void onCreate() {

    }
    /*public int onStartCommand(Intent intent, int flags, int startId) {
        i++;
        PendingIntent pendingIntent = PendingIntent.getService(SmsService.this, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+1000*60, pendingIntent);
        //TODO do something useful
        return Service.START_NOT_STICKY;
    }*/

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub

        return null;
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

    }

    @Override
    public void onStart(Intent intent, int startId) {
        Intent myIntent = new Intent(Jesa.this, SmsService.class);
        PendingIntent pendingIntent = PendingIntent.getService(Jesa.this, 0, myIntent, 0);
        Intent myIntent2 = new Intent(getApplicationContext(), Jesa.class);
        PendingIntent pendingIntent2 = PendingIntent.getService(getApplicationContext(), 0, myIntent2, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Calendar cal = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
//cal.add(Calendar.SECOND, 10);

        cal.set(Calendar.DATE, 22);  //1-31
        cal.set(Calendar.MONTH, 2);  //first month is 0!!! January is zero!!!
        cal.set(Calendar.YEAR, 2015);//year...

        cal.set(Calendar.HOUR_OF_DAY, 0);  //HOUR
        cal.set(Calendar.MINUTE, 11);       //MIN
        cal.set(Calendar.SECOND, 0);

        //alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1000 * 60, pendingIntent);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1000 * 60, pendingIntent2);

    }

    @Override
    public boolean onUnbind(Intent intent) {
        // TODO Auto-generated method stub

        return super.onUnbind(intent);
    }





}
