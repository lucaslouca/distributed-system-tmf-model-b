package com.txlcommon.domain.psn;

import com.txlcommon.domain.TXLPersistentObject;

import javax.persistence.*;

/**
 * Created by lucas on 16/08/15.
 */
@Entity
@Table(name = "txl_psn")
public class TXLPsn extends TXLPersistentObject {
    @Enumerated(EnumType.STRING)
    private TXLPsnType type;

    private String value;

    public TXLPsn() {}

    public TXLPsn(TXLPsnType type, String value) {
        this.type = type;
        this.value = value;
    }

    public TXLPsnType getType() {
        return type;
    }

    public void setType(TXLPsnType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return "TXLPsn{" +
                "id=" + getId() +
                ", type=" + type +
                ", value='" + value + '\'' +
                '}';
    }
}
