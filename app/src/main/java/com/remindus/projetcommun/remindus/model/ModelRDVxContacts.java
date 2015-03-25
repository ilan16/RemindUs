package com.remindus.projetcommun.remindus.model;

/**
 * Created by ilanmalka on 18/03/15.
 */
public class ModelRDVxContacts extends IModel {

    private long idrdv;
    private long idcontact;

    /**
     *
     * @return long
     */
    public long getIdrdv() {
        return idrdv;
    }

    /**
     *
     * @param idrdv
     */
    public void setIdrdv(long idrdv) {
        this.idrdv = idrdv;
    }

    /**
     *
     * @return long
     */
    public long getIdcontact() {
        return idcontact;
    }

    /**
     *
     * @param idcontact
     */
    public void setIdcontact(long idcontact) {
        this.idcontact = idcontact;
    }

    /**
     *
      * @return string
     */
    @Override
    public String toString() {
        return idrdv + " " + idcontact;
    }
}
