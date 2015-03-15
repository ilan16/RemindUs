package com.remindus.projetcommun.remindus.model;

import java.util.Date;

/**
 * Created by ilanmalka on 08/03/15.
 */
public class ModelGroupexContact {

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

    public String toString(){
        return this.getIdcontact() + " " + this.getIdgroupe();
    }
}
