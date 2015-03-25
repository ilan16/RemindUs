package com.remindus.projetcommun.remindus.Service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.remindus.projetcommun.remindus.dao.DAOMsgProg;
import com.remindus.projetcommun.remindus.model.ModelMsgProg;

/**
 * Created by kevin on 21/03/2015.
 */
public class DeclencheurSms extends Service {
    /**
     *
     */
    @Override
    public void onCreate() {

    }

    /**
     *
     * @param intent
     * @return
     */
    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub

        return null;
    }

    /**
     *
     */
    @Override
    public void onDestroy() {
        // detruire le service
        super.onDestroy() ;

    }

    /**
     *
     * @param intent
     * @param startId
     */
    @Override
    public void onStart(Intent intent, int startId) {
        //connection à la base de donnée
        DAOMsgProg daoMsgProg = new DAOMsgProg(this);
        ModelMsgProg modelMsgProg = daoMsgProg.prochainMsgProg(0);
        //recupeation de la date et heure du prochain envoi
        long date = modelMsgProg.getDate();
        if(date!=0) {
            //si il existe un prochain message programmé
            //on se redirige vers SmsService.class
            Intent myIntent = new Intent(DeclencheurSms.this, SmsService.class);
            PendingIntent pendingIntent = PendingIntent.getService(DeclencheurSms.this, 0, myIntent, 0);
            //on redemarre un nouveau service DeclencheurSms pour le prochaoin sms
            Intent myIntent2 = new Intent(getApplicationContext(), DeclencheurSms.class);
            PendingIntent pendingIntent2 = PendingIntent.getService(getApplicationContext(), 0, myIntent2, 0);

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            //verifie quand envoyé le message
            alarmManager.set(AlarmManager.RTC_WAKEUP, date, pendingIntent);

            //verifie quand demarré le nouveau service declencheurSms
            alarmManager.set(AlarmManager.RTC_WAKEUP, date, pendingIntent2);
            //on ferme le service smsService pour evité qu'il renvoi un nouveau message aléatoirement
            DeclencheurSms.this.stopService(myIntent);
            //on detruit se service
            this.stopSelf();
        }else{
            //si il a plus de prochain message programmé on detruit se service
            this.stopSelf();
        }
    }

    /**
     *
     * @param intent
     * @return
     */
    @Override
    public boolean onUnbind(Intent intent) {
        // TODO Auto-generated method stub

        return super.onUnbind(intent);
    }



}

