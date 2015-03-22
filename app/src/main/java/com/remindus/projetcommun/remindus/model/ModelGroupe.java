package com.remindus.projetcommun.remindus.model;

import com.remindus.projetcommun.remindus.controller.UtilitaireDate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by ilanmalka on 04/03/15.
 */
public class ModelGroupe extends IModel {

    private long idGroupe;
    private String nomGroupe;
    private long dateCreation;
    private UtilitaireDate utilitaireDate;

    public long getIdGroupe() {
        return idGroupe;
    }

    public void setIdGroupe(long idGroupe) {
        this.idGroupe = idGroupe;
    }

    public String getNomGroupe() {
        return nomGroupe;
    }

    public void setNomGroupe(String nomGroupe) {
        this.nomGroupe = nomGroupe;
    }

    public long getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(long dateCreation) {
        this.dateCreation = dateCreation;
    }

    @Override
    public String toString() {
        this.utilitaireDate = new UtilitaireDate();
        return nomGroupe + " " +  utilitaireDate.convertirLongDateString(this.dateCreation, "dd/MM/yy à HH:mm:ss");
    }
}
