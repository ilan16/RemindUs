package com.remindus.projetcommun.remindus.model;

/**
 * Created by ilanmalka on 08/03/15.
 */
public class ModelMsgProgxGroupe {

    private long idMsgProg;
    private long idGroupe;

    public ModelMsgProgxGroupe(long idMsgProg, long idGroupe) {
        this.idMsgProg = idMsgProg;
        this.idGroupe = idGroupe;
    }

    public long getIdGroupe() {
        return idGroupe;
    }

    public void setIdGroupe(long idGroupe) {
        this.idGroupe = idGroupe;
    }

    public long getIdMsgProg() {
        return idMsgProg;
    }

    public void setIdMsgProg(long idMsgProg) {
        this.idMsgProg = idMsgProg;
    }
}
