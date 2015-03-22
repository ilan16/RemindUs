package com.remindus.projetcommun.remindus.model;

/**
 * Created by bahia on 22/03/2015.
 */
public abstract class IModel {

    public long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    abstract public String toString();

}
