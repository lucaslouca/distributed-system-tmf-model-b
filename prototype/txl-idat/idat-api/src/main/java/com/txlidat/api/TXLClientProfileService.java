package com.txlidat.api;

import com.txlcommon.domain.psn.TXLPsn;
import com.txlidat.domain.TXLClientProfile;

import java.util.List;

/**
 * Created by lucas on 19/08/15.
 */
public interface TXLClientProfileService {
    List<TXLClientProfile> listClientProfiles();

    List<TXLClientProfile> listClientProfiles(int start, int maxResult);

    TXLClientProfile createClientProfileWithPsn(TXLPsn psn);

    TXLClientProfile update(TXLClientProfile clientProfile);

    TXLClientProfile clientProfileForPsn(TXLPsn psn) throws TXLClientProfileDoesNotExistException;
}
