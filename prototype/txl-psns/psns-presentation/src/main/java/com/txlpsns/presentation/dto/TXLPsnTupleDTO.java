package com.txlpsns.presentation.dto;

import com.txlcommon.presentation.dto.TXLPsnDTO;
import com.txlpsns.domain.TXLPsnTuple;

/**
 * Created by lucas on 16/08/15.
 */
public class TXLPsnTupleDTO {
    private TXLPsnDTO psn1;
    private TXLPsnDTO psn2;

    public TXLPsnTupleDTO() {
    }

    public TXLPsnTupleDTO(TXLPsnTuple psnTuple) {
        this.psn1 = new TXLPsnDTO(psnTuple.getPsn1());
        this.psn2 = new TXLPsnDTO(psnTuple.getPsn2());
    }

    public TXLPsnDTO getPsn1() {
        return psn1;
    }

    public void setPsn1(TXLPsnDTO psn1) {
        this.psn1 = psn1;
    }

    public TXLPsnDTO getPsn2() {
        return psn2;
    }

    public void setPsn2(TXLPsnDTO psn2) {
        this.psn2 = psn2;
    }
}
