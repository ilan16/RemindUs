package com.remindus.projetcommun.remindus.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.IBinder;

import com.remindus.projetcommun.remindus.MainActivity;
import com.remindus.projetcommun.remindus.R;

/**
 * Created by kevin on 21/03/2015.
 */
public class SmsService extends Service {

    @Override
    public void onCreate() {

    }

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
            String num="0621766518;";
            String msg="je suis un genie";

            EnvoiSms s=new EnvoiSms(num,msg);
            s.envoi_texto();
        }else{
            NotificationManager notificationmanager=(NotificationManager) this.getApplicationContext().getSystemService(this.getApplicationContext().NOTIFICATION_SERVICE);
            Intent myIntent=new Intent(this.getApplicationContext(), MainActivity.class);
            Notification notification=new Notification(R.drawable.ic_launcher,"test",System.currentTimeMillis());
            myIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent myPendingIntent=PendingIntent.getActivity(this.getApplicationContext(),0,myIntent,PendingIntent.FLAG_UPDATE_CURRENT);
            notification.setLatestEventInfo(this.getApplicationContext(),"test1","test2",myPendingIntent);
            notificationmanager.notify(0,notification);

        }


    }

    @Override
    public boolean onUnbind(Intent intent) {
        // TODO Auto-generated method stub

        return super.onUnbind(intent);
    }



}

