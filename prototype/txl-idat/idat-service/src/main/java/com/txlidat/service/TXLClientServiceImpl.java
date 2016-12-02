package com.txlidat.service;

import com.txlcommon.domain.psn.TXLPsn;
import com.txlcommon.domain.user.TXLUser;
import com.txlidat.api.TXLClientService;
import com.txlidat.dao.TXLClientDao;
import com.txlidat.domain.TXLClient;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lucas on 22/08/15.
 */
public class TXLClientServiceImpl implements TXLClientService {
    private TXLClientDao clientDao;

    public void setClientDao(TXLClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @Override
    @Transactional
    public TXLClient saveClient(TXLClient client) {
        return clientDao.save(client);
    }

    @Override
    @Transactional(readOnly = true)
    public TXLPsn getEIDForUsername(String username) {
        TXLPsn result = clientDao.fetchEIDForUsername(username);
        if (result == null) {
            throw new IllegalArgumentException("No EID for username '"+username+"' found!");
        }

        return result;
    }

    /**
     * Fetch List of all TXLClient in db.
     *
     * @return List of all TXLClient in db.
     */
    @Override
    @Transactional
    public List<TXLClient> listClients() {
        return clientDao.listClients();
    }

    /**
     * Fetch a List of TXLClient.
     *
     * @param start the start index
     * @param maxResult number of clients to fetch
     * @return List of TXLClient.
     */
    @Override
    @Transactional
    public List<TXLClient> listClients(int start, int maxResult) {
        return clientDao.listClients(start,maxResult);
    }
}
