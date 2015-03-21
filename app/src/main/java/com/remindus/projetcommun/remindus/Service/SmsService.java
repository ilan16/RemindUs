package com.remindus.projetcommun.remindus.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

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
        String num="0621766518;";





        String msg="testboot";

        EnvoiSms s=new EnvoiSms(num,msg);
        s.envoi_texto();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        // TODO Auto-generated method stub

        return super.onUnbind(intent);
    }



}

