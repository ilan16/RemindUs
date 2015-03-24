package com.remindus.projetcommun.remindus;

/**
 * Created by bahia on 26/02/2015.
 */

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.remindus.projetcommun.remindus.Service.Jesa;
import com.remindus.projetcommun.remindus.Service.SmsService;
import com.remindus.projetcommun.remindus.controller.ControllerCalendrier;
import com.remindus.projetcommun.remindus.controller.ControllerContact;
import com.remindus.projetcommun.remindus.controller.ControllerCreerMsgProg;
import com.remindus.projetcommun.remindus.controller.ControllerCreerRDV;
import com.remindus.projetcommun.remindus.controller.ControllerListerGroupe;
import com.remindus.projetcommun.remindus.controller.ControllerListerModelMsg;
import com.remindus.projetcommun.remindus.controller.ControllerListerMsgProg;
import com.remindus.projetcommun.remindus.controller.ControllerListerRDV;
import com.remindus.projetcommun.remindus.controller.ControllerParametre;
import com.remindus.projetcommun.remindus.dao.DAOMsgProg;
import com.remindus.projetcommun.remindus.dao.DAORDV;
import com.remindus.projetcommun.remindus.model.ModelMsgProg;
import com.remindus.projetcommun.remindus.model.ModelRDV;

import junit.framework.Test;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;


public class MainActivity extends ActionBarActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ControllerCreerRDV.setNomRDVstatic("");
        ControllerCreerMsgProg.setTitreMsgProgStatic("");

        DAOMsgProg daoMsgProg = new DAOMsgProg(this);
        ModelMsgProg modelMsgProg = daoMsgProg.prochainMsgProg();
        long date = modelMsgProg.getDate();
        Log.i("testetet", "" + date + "");
        if(date!=0) {
            Log.i("test3","enciler");
            //Intent myIntent = new Intent(MainActivity.this, SmsService.class);
            //PendingIntent pendingIntent = PendingIntent.getService(MainActivity.this, 0, myIntent, 0);
            Intent myIntent = new Intent(getApplicationContext(), Jesa.class);
            //PendingIntent pendingIntent2 = PendingIntent.getService(getApplicationContext(), 0, myIntent2, 0);
            //AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                /*Calendar cal = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
//cal.add(Calendar.SECOND, 10);

                cal.set(Calendar.DATE, 22);  //1-31
                cal.set(Calendar.MONTH, 2);  //first month is 0!!! January is zero!!!
                cal.set(Calendar.YEAR, 2015);//year...

                cal.set(Calendar.HOUR_OF_DAY, 0);  //HOUR
                cal.set(Calendar.MINUTE, 11);       //MIN
                cal.set(Calendar.SECOND, 0);

                //alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
                alarmManager.set(AlarmManager.RTC_WAKEUP, date, pendingIntent);*/
            //alarmManager.set(AlarmManager.RTC, System.currentTimeMillis(), pendingIntent2);
            //Intent myIntent = new Intent(getApplicationContext(), Jesa.class);
            MainActivity.this.startService(myIntent);
        }

        final Button boutonAccueil = (Button) findViewById(R.id.bouton_calendrier);
        boutonAccueil.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ControllerCalendrier.class);
                startActivity(intent);
            }
        });

        final Button boutonRDV = (Button) findViewById(R.id.bouton_rdv);
        boutonRDV.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ControllerListerRDV.class);
                startActivity(intent);
            }
        });

        final Button boutonMsgProg = (Button) findViewById(R.id.bouton_msg_prog);
        boutonMsgProg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ControllerListerMsgProg.class);
                startActivity(intent);
            }
        });

        final Button boutonGroupe = (Button) findViewById(R.id.bouton_groupe);
        boutonGroupe.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ControllerListerGroupe.class);
                startActivity(intent);
            }
        });

        final Button boutonContact = (Button) findViewById(R.id.bouton_contact);
        boutonContact.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ControllerContact.class);
                startActivity(intent);
            }
        });

        final Button boutonModelMsg = (Button) findViewById(R.id.bouton_model_msg);
        boutonModelMsg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ControllerListerModelMsg.class);
                startActivity(intent);
            }
        });


        final Button boutonParametre = (Button) findViewById(R.id.bouton_parametre);
        boutonParametre.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ControllerParametre.class);
                startActivity(intent);
            }
        });


    }

}