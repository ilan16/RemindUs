package com.remindus.projetcommun.remindus.model;

/**
 * Created by ilanmalka on 11/03/15.
 */
public class ModelModelMsg extends IModel {

    private String titre;
    private String contenu;
    private long dateCreation;

    /**
     *
     * @return
     */
    public String getTitre() {
        return titre;
    }

    /**
     *
     * @param titre
     */
    public void setTitre(String titre) {
        this.titre = titre;
    }

    /**
     *
     * @return
     */
    public String getContenu() {
        return contenu;
    }

    /**
     *
     * @param contenu
     */
    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    /**
     *
     * @return
     */
    public long getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(long dateCreation) {
        this.dateCreation = dateCreation;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return this.getTitre() + "\n" + this.getContenu();
    }
}
