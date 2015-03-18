package com.remindus.projetcommun.remindus.model;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by ilanmalka on 08/03/15.
 */
public class ModelMsgProg {

    private long idMsgProg;
    private String titre;
    private long date;
    private String datestring;
    private DateFormat format;
    private String msgProg;


    public long getIdMsgProg() {
        return idMsgProg;
    }

    public void setIdMsgProg(long idMsgProg) {
        this.idMsgProg = idMsgProg;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getDatestring() {
        return datestring;
    }

    public void setDatestring(String datestring) {
        this.datestring = datestring;
    }

    public DateFormat getFormat() {
        return format;
    }

    public void setFormat(DateFormat format) {
        this.format = format;
    }

    public String getMsgProg() {
        return msgProg;
    }

    public void setMsgProg(String msgProg) {
        this.msgProg = msgProg;
    }

    public String toString(){
        return this.getTitre() + "\n"+ this.getMsgProg();
    }
}
