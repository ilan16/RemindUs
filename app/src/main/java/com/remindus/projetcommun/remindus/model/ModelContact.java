package com.remindus.projetcommun.remindus.model;

/**
 * Created by Ilan on 24/02/2015.
 */
public class ModelContact {

    private long id;
    private String contact;
    private String telephone;
    private int value;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return contact + "\n" + telephone;
    }


}
