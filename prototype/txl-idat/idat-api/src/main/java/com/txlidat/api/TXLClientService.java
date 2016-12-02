package com.txlidat.api;

import com.txlcommon.domain.psn.TXLPsn;
import com.txlcommon.domain.user.TXLUser;
import com.txlidat.domain.TXLClient;
import com.txlidat.domain.TXLClientProfile;

import java.util.List;

/**
 * Created by lucas on 22/08/15.
 */
public interface TXLClientService {
    TXLClient saveClient(TXLClient client);

    TXLPsn getEIDForUsername(String username);

    List<TXLClient> listClients();

    List<TXLClient> listClients(int start, int maxResult);
}
