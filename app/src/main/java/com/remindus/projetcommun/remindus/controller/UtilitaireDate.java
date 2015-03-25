package com.remindus.projetcommun.remindus.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ilanmalka on 13/03/15.
 */
public class UtilitaireDate {
    /**
     * permet de convertir une date de type string en long
     * @param date
     * @return
     * @throws ParseException
     */
    public long convertirStringDateEnLong(String date) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy-HH:mm");
        Date d = (Date) simpleDateFormat.parse(date);
        return d.getTime();
    }

    /**
     * permet de convertir une date long en string
     * @param dateLong
     * @param format
     * @return
     */
    public String convertirLongDateString(long dateLong, String format) { //à mettre par défaut "dd/MM/yy à HH:mm:ss"
        DateFormat df = new SimpleDateFormat(format);
        return df.format(dateLong);
    }

    /**
     * permet de connaitre la date actuelle
     * @return
     */
    public long dateActuelle() {
        Date actuelle = new Date();
        return actuelle.getTime();
    }

}
