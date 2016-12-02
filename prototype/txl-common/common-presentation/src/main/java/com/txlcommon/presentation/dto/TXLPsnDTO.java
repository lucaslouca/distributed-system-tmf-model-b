package com.txlcommon.presentation.dto;

import com.txlcommon.domain.psn.TXLPsn;

/**
 * Created by lucas on 16/08/15.
 */
public class TXLPsnDTO implements TXLGenericDTO<TXLPsn> {
    private TXLPsnTypeDTO type;
    private String value;

    public TXLPsnDTO() {}

    public TXLPsnDTO(TXLPsn psn) {
        this.type = new TXLPsnTypeDTO(psn.getType());
        this.value = psn.getValue();
    }

    public TXLPsnTypeDTO getType() {
        return type;
    }

    public void setType(TXLPsnTypeDTO type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    @Override
    public TXLPsn toBo() {
        TXLPsn result = new TXLPsn();
        result.setType(this.getType().toBo());
        result.setValue(this.getValue());
        return result;
    }
}
