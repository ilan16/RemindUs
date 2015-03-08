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
    private long heure;
    private DateFormat format;
    private String msgProg;

    public ModelMsgProg() {
    }

    public ModelMsgProg(long idMsgProg, String titre, long date, long heure, DateFormat format, String msgProg) {
        this.idMsgProg = idMsgProg;
        this.titre = titre;
        this.date = date;
        this.heure = heure;
        this.format = format;
        this.msgProg = msgProg;
    }

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

    public long getHeure() {
        return heure;
    }

    public void setHeure(long heure) {
        this.heure = heure;
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
}
