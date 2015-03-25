package com.remindus.projetcommun.remindus.Service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;
import android.util.Log;

import com.remindus.projetcommun.remindus.dao.DAOMsgProg;
import com.remindus.projetcommun.remindus.dao.DAORDV;
import com.remindus.projetcommun.remindus.model.ModelMsgProg;
import com.remindus.projetcommun.remindus.model.ModelRDV;

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
    public void onStart(Intent intent, int startId){
        AudioManager audiomanage = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        //connection à la base de donnée
        DAORDV daordv = new DAORDV(this);
        ModelRDV modelRDV = daordv.prochainRDV(10000);
        //recupeation de la date et heure du prochain envoi
        long mode = modelRDV.getMode();
        Log.i("test","test");
        int initiale =audiomanage.getRingerMode();
    Log.i("test3",""+mode);
        if(mode==2){
            audiomanage.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
        }else if(mode==1){
            audiomanage.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        }
        this.stopSelf() ;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        // TODO Auto-generated method stub

        return super.onUnbind(intent);
    }



}

