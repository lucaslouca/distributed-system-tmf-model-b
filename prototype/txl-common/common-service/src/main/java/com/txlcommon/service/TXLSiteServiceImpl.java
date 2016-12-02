package com.txlcommon.service;

import com.txlcommon.TXLSiteDao;
import com.txlcommon.TXLSiteService;
import com.txlcommon.domain.site.TXLSite;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lucas on 23/08/15.
 */
public class TXLSiteServiceImpl implements TXLSiteService {
    TXLSiteDao siteDao;

    public void setSiteDao(TXLSiteDao siteDao) {
        this.siteDao = siteDao;
    }

    @Override
    @Transactional
    public TXLSite saveSite(TXLSite site) {
        return siteDao.save(site);
    }

    @Override
    @Transactional
    public TXLSite siteWithName(String name) {
        return siteDao.fetchSiteWithName(name);
    }
}
