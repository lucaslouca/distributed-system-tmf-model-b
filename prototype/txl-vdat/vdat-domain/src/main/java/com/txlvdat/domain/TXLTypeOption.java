package com.txlvdat.domain;

import com.txlcommon.domain.TXLPersistentObject;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 * Created by lucas on 08/11/15.
 *
 * Type that can be instantiated and created by the user. Example:
 *
 * Target network: Internet, Intranet, VPN
 *
 */
@Entity
@Table(name = "txl_typeoption")
public class TXLTypeOption extends TXLPersistentObject {
    private String value;
    private String label;

    @Enumerated(EnumType.STRING)
    private TXLType type;

    public TXLTypeOption() {}

    public TXLTypeOption(TXLType type, String value, String label) {
        this.setType(type);
        this.value = value;
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public TXLType getType() {
        return type;
    }

    public void setType(TXLType type) {
        this.type = type;
    }
}
