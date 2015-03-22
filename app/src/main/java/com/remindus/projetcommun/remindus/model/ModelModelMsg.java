package com.remindus.projetcommun.remindus.model;

/**
 * Created by ilanmalka on 11/03/15.
 */
public class ModelModelMsg extends IModel {

    private String titre;
    private String contenu;
    private long dateCreation;

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public long getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(long dateCreation) {
        this.dateCreation = dateCreation;
    }

    @Override
    public String toString() {
        return this.getTitre() + "\n" + this.getContenu();
    }
}
