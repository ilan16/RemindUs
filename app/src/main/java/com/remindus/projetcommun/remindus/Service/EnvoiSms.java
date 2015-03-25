package com.remindus.projetcommun.remindus.Service;

import android.telephony.gsm.SmsManager;

/**
 * Created by kevin on 21/03/2015.
 */
public class EnvoiSms {public String num;
    public String msg;

    /**
     *
     * @param num
     * @param msg
     */
    public EnvoiSms(String num, String msg){
        this.num=num;
        this.msg=msg;
    }

    /**
     * 
     */
    public void envoi_texto(){



        int taille=0;
        String comp=";";
        //on determine le nombre de numero a qui envoyé le message
        for(int a=0;a<num.length();a++){
            if(num.charAt(a)==comp.charAt(0)){
                taille++;
            }
        }
        String test="+";
        int it=0;
        String recup[]= new String[taille];
        //on recupere tout les numeros
        for(int a=0;a<num.length();){

            String num_recup="0";
            //si le numero commence par + on le remplace par 00
            if(num.charAt(0)==test.charAt(0)){
                num_recup+="0";
            }
            a++;
            //on recupere le numero jusquau ;
            while(num.charAt(a)!=comp.charAt(0)){
                num_recup+=num.charAt(a);
                a++;
            }
            a++;
            recup[it]=num_recup;
            it++;
        }
        //On envoi tout les messages
        for(int a=0;a<taille;a++){
            SmsManager.getDefault().sendTextMessage(recup[a], null, msg, null, null) ;
        }


    }

}

