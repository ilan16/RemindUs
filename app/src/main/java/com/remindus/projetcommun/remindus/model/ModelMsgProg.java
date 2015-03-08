package com.remindus.projetcommun.remindus.model;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by ilanmalka on 08/03/15.
 */
public class ModelMsgProg {

    private long idMsgProg;
    private String titre;
    private Date date;
    private DateFormat format;
    private String msgProg;

    public ModelMsgProg(long idMsgProg, String titre, Date date, DateFormat format, String msgProg) {
        this.idMsgProg = idMsgProg;
        this.titre = titre;
        this.date = date;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
