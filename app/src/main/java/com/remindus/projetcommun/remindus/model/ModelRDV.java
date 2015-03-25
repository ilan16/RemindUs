package com.remindus.projetcommun.remindus.model;

import com.remindus.projetcommun.remindus.controller.UtilitaireDate;

/**
 * Created by ilanmalka on 11/03/15.
 */
public class ModelRDV extends IModel {

    private String nom;
    private long datedebut;
    private long datefin;
    private String dateString;
    private String lieu;
    private long mode;
    private UtilitaireDate utilitaireDate;


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public long getDatedebut() {
        return datedebut;
    }

    public void setDatedebut(long datedebut) {
        this.datedebut = datedebut;
    }

    public long getDatefin() {
        return datefin;
    }

    public void setDatefin(long datefin) {
        this.datefin = datefin;
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

    @Override
    public String toString() {
        this.utilitaireDate = new UtilitaireDate();
        return this.getNom() + " - " + utilitaireDate.convertirLongDateString(this.getDatedebut(), "dd/MM/yy");
    }
}
