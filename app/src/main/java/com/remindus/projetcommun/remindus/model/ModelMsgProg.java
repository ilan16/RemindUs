package com.remindus.projetcommun.remindus.model;

import java.text.DateFormat;

/**
 * Created by ilanmalka on 08/03/15.
 */
public class ModelMsgProg extends IModel {

    private String titre;
    private long date;
    private String datestring;
    private DateFormat format;
    private String msgProg;

    /**
     *
     * @return
     */
    public String getTitre() {
        return titre;
    }

    /**
     *
     * @param titre
     */
    public void setTitre(String titre) {
        this.titre = titre;
    }

    /**
     *
     * @return
     */
    public long getDate() {
        return date;
    }

    /**
     *
     * @param date
     */
    public void setDate(long date) {
        this.date = date;
    }

    /**
     *
     * @return
     */
    public String getDatestring() {
        return datestring;
    }

    /**
     *
     * @param datestring
     */
    public void setDatestring(String datestring) {
        this.datestring = datestring;
    }

    /**
     *
     * @return
     */
    public DateFormat getFormat() {
        return format;
    }

    /**
     *
     * @param format
     */
    public void setFormat(DateFormat format) {
        this.format = format;
    }

    /**
     *
     * @return
     */
    public String getMsgProg() {
        return msgProg;
    }

    /**
     *
     * @param msgProg
     */
    public void setMsgProg(String msgProg) {
        this.msgProg = msgProg;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return this.getTitre() + "\n" + this.getMsgProg();
    }
}
