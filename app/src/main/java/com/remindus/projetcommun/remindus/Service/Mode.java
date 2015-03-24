package com.remindus.projetcommun.remindus.Service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;

import com.remindus.projetcommun.remindus.dao.DAOMsgProg;
import com.remindus.projetcommun.remindus.model.ModelMsgProg;

/**
 * Created by kevin on 24/03/2015.
 */
public class Mode extends Service {
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
        // detruire le service
        super.onDestroy();

    }

    @Override
    public void onStart(Intent intent, int startId) {
        AudioManager audiomanage = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        int initiale =audiomanage.getRingerMode();
        String mode=null;
        if(mode=="vibreur"){
            audiomanage.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
        }else if(mode=="silencieux"){
            audiomanage.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        }
        int duree=0;
        try {
            Thread.sleep(duree);
            audiomanage.setRingerMode(initiale);
            this.stopSelf();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        // TODO Auto-generated method stub

        return super.onUnbind(intent);
    }



}

