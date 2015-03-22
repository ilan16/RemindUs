package com.remindus.projetcommun.remindus.model;

import com.remindus.projetcommun.remindus.controller.UtilitaireDate;

/**
 * Created by ilanmalka on 11/03/15.
 */
public class ModelRDV extends IModel {

    private long id;
    private String nom;
    private long date;
    private String dateString;
    private String lieu;
    private long mode;
    private UtilitaireDate utilitaireDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public long getMode() {
        return mode;
    }

    public void setMode(long mode) {
        this.mode = mode;
    }

    public String toString() {
        this.utilitaireDate = new UtilitaireDate();
        return "RDV " + this.getNom() + " prévu le " + utilitaireDate.convertirLongDateString(this.getDate(), "dd/MM/yy");
    }
}
