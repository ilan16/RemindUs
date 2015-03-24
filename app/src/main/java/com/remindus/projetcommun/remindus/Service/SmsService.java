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
import android.util.Log;
import android.widget.Toast;

import com.remindus.projetcommun.remindus.MainActivity;
import com.remindus.projetcommun.remindus.R;
import com.remindus.projetcommun.remindus.dao.DAOMsgProg;
import com.remindus.projetcommun.remindus.dao.DAOMsgProgxContacts;
import com.remindus.projetcommun.remindus.model.ModelMsgProg;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by kevin on 21/03/2015.
 */
public class SmsService extends Service {

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
        // TODO Auto-generated method stub
        super.onStart(intent, startId);

        ConnectivityManager connexion=(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        LocationManager localisation=(LocationManager) getSystemService(LOCATION_SERVICE);
        if(localisation.isProviderEnabled(LocationManager.GPS_PROVIDER)||connexion.getActiveNetworkInfo()!=null){

            DAOMsgProg daoMsgProg = new DAOMsgProg(this);

            ModelMsgProg modelMsgProg = daoMsgProg.lastMsgProg();
            DAOMsgProgxContacts daoContact=new DAOMsgProgxContacts(this);
            String num=daoContact.getAllNumero(modelMsgProg.getId(),getApplicationContext());
            String msg=modelMsgProg.getMsgProg();
            Log.i("test",""+num);
            EnvoiSms s=new EnvoiSms(num,msg);
            s.envoi_texto();
        }else{
            NotificationManager notificationmanager=(NotificationManager) this.getApplicationContext().getSystemService(this.getApplicationContext().NOTIFICATION_SERVICE);
            Intent myIntent=new Intent(this.getApplicationContext(), MainActivity.class);
            Notification notification=new Notification(R.drawable.ic_launcher,"test",System.currentTimeMillis());
            myIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent myPendingIntent=PendingIntent.getActivity(this.getApplicationContext(),0,myIntent,PendingIntent.FLAG_UPDATE_CURRENT);
            notification.setLatestEventInfo(this.getApplicationContext(),"test1","ol",myPendingIntent);
            notification.defaults = Notification.DEFAULT_VIBRATE;
            notificationmanager.notify(0, notification);

        }
        this.stopSelf();

    }

    @Override
    public boolean onUnbind(Intent intent) {
        // TODO Auto-generated method stub

        return super.onUnbind(intent);
    }





}

