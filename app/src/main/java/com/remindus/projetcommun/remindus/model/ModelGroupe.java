package com.remindus.projetcommun.remindus.model;

/**
 * Created by ilanmalka on 04/03/15.
 */
public class ModelGroupe {

    private long idGroupe;
    private String nomGroupe;
    private long idContact;

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

    public long getIdContact() {
        return idContact;
    }

    public void setIdContact(long idContact) {
        this.idContact = idContact;
    }
}
