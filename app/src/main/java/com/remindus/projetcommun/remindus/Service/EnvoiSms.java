package com.remindus.projetcommun.remindus.Service;

import android.telephony.gsm.SmsManager;

/**
 * Created by kevin on 21/03/2015.
 */
public class EnvoiSms {public String num;
    public String msg;

    public EnvoiSms(String num, String msg){
        this.num=num;
        this.msg=msg;
    }
    public void envoi_texto(){



        int taille=0;
        String comp=";";
        for(int a=0;a<num.length();a++){
            if(num.charAt(a)==comp.charAt(0)){
                taille++;
            }
        }

        int it=0;
        String recup[]= new String[taille];
        for(int a=0;a<num.length();){
            String num_recup="0";
            a++;
            while(num.charAt(a)!=comp.charAt(0)){
                num_recup+=num.charAt(a);
                a++;
            }
            a++;
            recup[it]=num_recup;
            it++;
        }

        for(int a=0;a<taille;a++){
            SmsManager.getDefault().sendTextMessage(recup[a], null, msg, null, null);
        }


    }

}

