package com.remindus.projetcommun.remindus.model;

import java.util.Date;

/**
 * Created by ilanmalka on 08/03/15.
 */
public class ModelContactxGroupe {

    private long idcontact;
    private long idgroupe;
    private Date dateAjout;

    public ModelContactxGroupe(long idcontact, long idgroupe, Date dateAjout) {
        this.idcontact = idcontact;
        this.idgroupe = idgroupe;
        this.dateAjout = dateAjout;
    }

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

    public Date getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(Date dateAjout) {
        this.dateAjout = dateAjout;
    }
}
