package com.remindus.projetcommun.remindus.model;

/**
 * Created by ilanmalka on 11/03/15.
 */
public class ModelRDV {

    private long id;
    private String nom;
    private long date;
    private long heure;
    private String lieu;
    private long mode;

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

    public long getHeure() {
        return heure;
    }

    public void setHeure(long heure) {
        this.heure = heure;
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

    public String toString(){
        return this.getNom();
    }
}
