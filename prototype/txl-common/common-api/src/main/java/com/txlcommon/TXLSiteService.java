package com.txlcommon;

import com.txlcommon.domain.site.TXLSite;

/**
 * Created by lucas on 23/08/15.
 */
public interface TXLSiteService {
    TXLSite saveSite(TXLSite site);
    TXLSite siteWithName(String name);
}
