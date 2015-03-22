package com.remindus.projetcommun.remindus.model;

/**
 * Created by ilanmalka on 18/03/15.
 */
public class ModelRDVxContacts extends IModel {

    private long idrdv;
    private long idcontact;

    public long getIdrdv() {
        return idrdv;
    }

    public void setIdrdv(long idrdv) {
        this.idrdv = idrdv;
    }

    public long getIdcontact() {
        return idcontact;
    }

    public void setIdcontact(long idcontact) {
        this.idcontact = idcontact;
    }

    public String toString() {
        return idrdv + " " + idcontact;
    }
}
