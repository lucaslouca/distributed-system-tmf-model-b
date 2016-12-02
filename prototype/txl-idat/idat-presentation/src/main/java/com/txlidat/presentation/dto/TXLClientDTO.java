package com.txlidat.presentation.dto;

import com.txlcommon.domain.psn.TXLPsn;
import com.txlcommon.presentation.dto.TXLPsnDTO;

/**
 * Created by lucas on 25/10/15.
 */
public class TXLClientDTO {
    private Long id;
    private String name;
    private TXLPsnDTO eid;

    public TXLClientDTO() {}

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

    public TXLPsnDTO getEid() {
        return eid;
    }

    public void setEid(TXLPsnDTO eid) {
        this.eid = eid;
    }
}
