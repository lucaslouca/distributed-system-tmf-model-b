package com.txlcommon.presentation.dto;

import com.txlcommon.domain.site.TXLSite;

/**
 * Created by lucas on 23/08/15.
 */
public class TXLSiteDTO implements  TXLGenericDTO<TXLSite> {
    private String name;

    public TXLSiteDTO() {}

    public TXLSiteDTO(TXLSite site) {
        if (site!=null) {
            this.name = site.getName();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public TXLSite toBo() {
        TXLSite result = new TXLSite();
        result.setName(this.name);
        return result;
    }
}
