package com.remindus.projetcommun.remindus.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ilanmalka on 13/03/15.
 */
public class UtilitaireDate {

    public long convertirStringDateEnLong(String date) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy-HH:mm");
        Date d = (Date) simpleDateFormat.parse(date);
        return d.getTime();
    }

    public String convertirLongDateString(long dateLong, String format) { //à mettre par défaut "dd/MM/yy à HH:mm:ss"
        DateFormat df = new SimpleDateFormat(format);
        return df.format(dateLong);
    }

    public long dateActuelle() {
        Date actuelle = new Date();
        return actuelle.getTime();
    }

}
