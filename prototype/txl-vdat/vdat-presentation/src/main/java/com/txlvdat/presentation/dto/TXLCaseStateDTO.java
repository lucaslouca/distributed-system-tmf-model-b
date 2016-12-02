package com.txlvdat.presentation.dto;

import com.txlcommon.presentation.dto.TXLGenericDTO;
import com.txlvdat.domain.TXLCaseState;

/**
 * Created by Evi on 23.08.2015.
 *
 */
public class TXLCaseStateDTO implements TXLGenericDTO<TXLCaseState>  {
    public String state;

    public TXLCaseStateDTO() {}

    public TXLCaseStateDTO(TXLCaseState state) {
        this.state = state.name();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public TXLCaseState toBo() {
        return TXLCaseState.valueOf(this.state);
    }
}
