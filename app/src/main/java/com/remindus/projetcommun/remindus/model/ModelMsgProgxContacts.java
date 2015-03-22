package com.remindus.projetcommun.remindus.model;

/**
 * Created by ilanmalka on 08/03/15.
 */
public class ModelMsgProgxContacts extends IModel{

    private long idMsgProg;
    private long idContact;

    public long getIdContact() {
        return idContact;
    }

    public void setIdContact(long idContact) {
        this.idContact = idContact;
    }

    public long getIdMsgProg() {
        return idMsgProg;
    }

    public void setIdMsgProg(long idMsgProg) {
        this.idMsgProg = idMsgProg;
    }

    @Override
    public String toString() {
        return "idContact: " + idContact + " idMsgProg: "+idMsgProg;
    }
}
