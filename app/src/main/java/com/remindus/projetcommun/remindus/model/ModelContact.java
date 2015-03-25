package com.remindus.projetcommun.remindus.model;

/**
 * Created by Ilan on 24/02/2015.
 */
public class ModelContact extends IModel {

    private String contact;
    private String telephone;

    /**
     *
     * @return
     */
    public int getIdLong() {
        return (int) id;
    }

    /**
     *
     * @return
     */
    public String getContact() {
        return contact;
    }

    /**
     *
     * @param contact
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     *
     * @return
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     *
     * @param telephone
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return telephone;
    }


}
