package com.txlidat.service;

import com.txlcommon.domain.psn.TXLPsn;
import com.txlcommon.domain.site.TXLSite;
import com.txlcommon.domain.user.TXLUser;
import com.txlidat.api.TXLClientProfileDoesNotExistException;
import com.txlidat.api.TXLClientProfileService;
import com.txlidat.dao.TXLClientProfileDao;
import com.txlidat.domain.TXLClientProfile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Created by lucas on 19/08/15.
 */
public class TXLClientProfileServiceImpl implements TXLClientProfileService {
    private TXLClientProfileDao clientProfileDao;

    public void setClientProfileDao(TXLClientProfileDao clientProfileDao) {
        this.clientProfileDao = clientProfileDao;
    }

    /**
     * Fetch all client profiles.
     *
     * @return list containing all client profiles in the database
     */
    @Override
    @Transactional
    public List<TXLClientProfile> listClientProfiles() {
        return clientProfileDao.listClientProfiles();
    }

    /**
     * Fetch a set of client profiles.
     *
     * @param start the start index
     * @param maxResult number of client profiles to fetch
     * @return
     */
    @Override
    @Transactional
    public List<TXLClientProfile> listClientProfiles(int start, int maxResult) {
        return clientProfileDao.listClientProfiles(start, maxResult);
    }

    /**
     * Create a client profile with the given pseudonym.
     * @param psn the pseudonym for the new client profile.
     * @return the newly created client profile
     */
    @Override
    @Transactional
    public TXLClientProfile createClientProfileWithPsn(TXLPsn psn) {
        TXLClientProfile result = new TXLClientProfile();
        result.setPsn(psn);
        result.setName("Lorem ipsum");

        // Get user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        TXLUser user = (TXLUser) auth.getPrincipal();
        Set<TXLSite> userSites = user.getSites();
        if (!userSites.isEmpty()) {
            result.getSites().add(userSites.iterator().next());
        }

        clientProfileDao.save(result);
        return result;
    }

    /**
     * Update a given client profile.
     * @param clientProfile
     * @return the updated client profile.
     */
    @Override
    @Transactional
    public TXLClientProfile update(TXLClientProfile clientProfile) {
        TXLClientProfile dbObject = clientProfileDao.findById(clientProfile.getId());

        // TODO Better mapping
        dbObject.setName(clientProfile.getName());

        return clientProfileDao.save(dbObject);
    }

    /**
     * Get a client profile with the given pseudonym.
     *
     * @param psn
     * @return the client profile with the pseudonym.
     * @throws TXLClientProfileDoesNotExistException if no client profile with the given pseudonym exists.
     */
    @Override
    @Transactional(readOnly = true)
    public TXLClientProfile clientProfileForPsn(TXLPsn psn) throws TXLClientProfileDoesNotExistException {
        TXLClientProfile result = clientProfileDao.fetchClientProfileWithPsnValue(psn.getValue());

        if (result == null) {
            throw new TXLClientProfileDoesNotExistException("Client profile with psn '"+psn.getValue()+"' does not exist!");
        }

        return result;
    }
}
