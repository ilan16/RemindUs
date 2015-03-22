package com.remindus.projetcommun.remindus.model;

/**
 * Created by Ilan on 24/02/2015.
 */
public class ModelContact extends IModel {

    private String contact;
    private String telephone;

    public int getIdLong() {
        return (int) id;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }


    @Override
    public String toString() {
        return contact + "\n" + telephone;
    }


}
