package com.txlvdat.presentation.dto;

import com.txlvdat.domain.TXLType;

/**
 * Created by lucas on 08/11/15.
 */
public class TXLTypeDTO {
    public String name;

    public TXLTypeDTO() {}

    public TXLTypeDTO(TXLType type) {
        this.name = type.name();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
