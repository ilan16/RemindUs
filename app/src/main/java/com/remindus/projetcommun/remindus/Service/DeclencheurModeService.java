package com.remindus.projetcommun.remindus.Service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.remindus.projetcommun.remindus.dao.DAOMsgProg;
import com.remindus.projetcommun.remindus.dao.DAORDV;
import com.remindus.projetcommun.remindus.model.ModelMsgProg;
import com.remindus.projetcommun.remindus.model.ModelRDV;

/**
 * Created by kevin on 25/03/2015.
 */
public class DeclencheurModeService extends Service {
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
        super.onDestroy();

    }

    /**
     *
     * @param intent
     * @param startId
     */
    @Override
    public void onStart(Intent intent, int startId) {
        //connection à la base de donnée
        DAORDV daordv = new DAORDV(this);
        ModelRDV modelRDV = daordv.prochainRDV(0);
        //recupeation de la date et heure du prochain RDV
        long date = modelRDV.getDatedebut();
        if(date!=0) {
            //si il existe un prochain message programmé
            //on se redirige vers Mode.class
            Intent myIntent = new Intent(DeclencheurModeService.this, Mode.class);
            PendingIntent pendingIntent = PendingIntent.getService(DeclencheurModeService.this, 0, myIntent, 0);
            //on redemarre un nouveau service DeclencheurModeService pour le prochaoin RDV
            Intent myIntent2 = new Intent(getApplicationContext(), DeclencheurModeService.class);
            PendingIntent pendingIntent2 = PendingIntent.getService(getApplicationContext(), 0, myIntent2, 0);

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            //verifie quand envoyé le message
            alarmManager.set(AlarmManager.RTC_WAKEUP, date, pendingIntent);

            //verifie quand demarré le nouveau service declencheurSms
            alarmManager.set(AlarmManager.RTC_WAKEUP, date, pendingIntent2);
            //on ferme le service Mode pour evité qu'il ne se déclenche aléatoirement
            DeclencheurModeService.this.stopService(myIntent);
            //on detruit se service
            this.stopSelf() ;
        }else{
            //si il a plus de prochain RDV on detruit se service
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
