package com.txlcommon.presentation.dto;

import com.txlcommon.domain.psn.TXLPsnType;


/**
 * Created by lucas on 22/08/15.
 */
public class TXLPsnTypeDTO implements TXLGenericDTO<TXLPsnType>  {
    public String name;

    public TXLPsnTypeDTO() {}

    public TXLPsnTypeDTO(TXLPsnType type) {
        this.name = type.name();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public TXLPsnType toBo() {
        return TXLPsnType.valueOf(this.name);
    }
}
