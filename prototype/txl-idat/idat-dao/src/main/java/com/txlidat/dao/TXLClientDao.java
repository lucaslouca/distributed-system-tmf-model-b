package com.txlidat.dao;

import com.txlcommon.domain.psn.TXLPsn;
import com.txlidat.domain.TXLClient;
import com.txlcommon.TXLGenericDao;
import com.txlidat.domain.TXLClientProfile;

import java.util.List;

/**
 * Created by lucas on 22/08/15.
 */
public interface TXLClientDao extends TXLGenericDao<TXLClient> {
    TXLPsn fetchEIDForUsername(String username);

    List<TXLClient> listClients();

    List<TXLClient> listClients(int start, int maxResult);
}
