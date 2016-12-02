package com.txlvdat.presentation.dto;

import com.txlvdat.domain.TXLType;
import com.txlvdat.domain.TXLTypeOption;


/**
 * Created by lucas on 08/11/15.
 */
public class TXLTypeOptionDTO {
    private String value;
    private String label;
    private TXLTypeDTO type;

    public TXLTypeOptionDTO() {}

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

    public TXLTypeDTO getType() {
        return type;
    }

    public void setType(TXLTypeDTO type) {
        this.type = type;
    }
}
