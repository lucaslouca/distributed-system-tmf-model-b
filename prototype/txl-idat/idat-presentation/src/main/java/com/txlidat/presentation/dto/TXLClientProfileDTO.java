package com.txlidat.presentation.dto;

import com.txlcommon.domain.psn.TXLPsn;
import com.txlcommon.presentation.dto.TXLGenericDTO;
import com.txlcommon.presentation.dto.TXLPsnDTO;
import com.txlidat.domain.TXLClientProfile;

/**
 * Created by lucas on 19/08/15.
 */
public class TXLClientProfileDTO implements TXLGenericDTO<TXLClientProfile> {
    private Long id;
    private String name;
    private TXLPsnDTO psn;

    public TXLClientProfileDTO() {}

    public TXLClientProfileDTO(TXLClientProfile clientProfile) {
        this.id = clientProfile.getId();
        this.name = clientProfile.getName();
        TXLPsn clientProfilePsn = clientProfile.getPsn();

        if (clientProfilePsn != null) {
            this.psn = new TXLPsnDTO(clientProfilePsn);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TXLPsnDTO getPsn() {
        return psn;
    }

    public void setPsn(TXLPsnDTO psn) {
        this.psn = psn;
    }

    @Override
    public TXLClientProfile toBo() {
        TXLClientProfile result = new TXLClientProfile();
        result.setId(this.getId());
        result.setName(this.getName());
        if (this.psn != null) {
            result.setPsn(this.psn.toBo());
        }
        return result;
    }
}
