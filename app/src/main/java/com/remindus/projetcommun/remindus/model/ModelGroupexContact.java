package com.remindus.projetcommun.remindus.model;

/**
 * Created by ilanmalka on 08/03/15.
 */
public class ModelGroupexContact extends IModel {

    private long idcontact;
    private long idgroupe;
    private long dateAjout;


    public long getIdcontact() {
        return idcontact;
    }

    public void setIdcontact(long idcontact) {
        this.idcontact = idcontact;
    }

    public long getIdgroupe() {
        return idgroupe;
    }

    public void setIdgroupe(long idgroupe) {
        this.idgroupe = idgroupe;
    }

    public long getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(long dateAjout) {
        this.dateAjout = dateAjout;
    }

    @Override
    public String toString() {
        return this.getIdcontact() + " " + this.getIdgroupe();
    }
}
