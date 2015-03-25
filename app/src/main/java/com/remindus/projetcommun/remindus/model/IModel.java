package com.remindus.projetcommun.remindus.model;

/**
 * Created by bahia on 22/03/2015.
 */
public abstract class IModel {

    public long id;

    /**
     *
     * @return
     */
    public long getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     *
     * @return String --> cela correspond à ce qui sera affiché dans les listes
     */
    abstract public String toString();

}
