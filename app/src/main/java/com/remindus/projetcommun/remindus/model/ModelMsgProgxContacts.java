package com.remindus.projetcommun.remindus.model;

/**
 * Created by ilanmalka on 08/03/15.
 */
public class ModelMsgProgxContacts extends IModel{

    private long idMsgProg;
    private long idContact;

    /**
     *
     * @return long
     */
    public long getIdContact() {
        return idContact;
    }

    /**
     *
     * @param idContact
     */
    public void setIdContact(long idContact) {
        this.idContact = idContact;
    }

    /**
     *
     * @return long
     */
    public long getIdMsgProg() {
        return idMsgProg;
    }

    /**
     *
     * @param idMsgProg
     */
    public void setIdMsgProg(long idMsgProg) {
        this.idMsgProg = idMsgProg;
    }

    /**
     *
     * @return string
     */
    @Override
    public String toString() {
        return "idContact: " + idContact + " idMsgProg: "+idMsgProg;
    }
}
