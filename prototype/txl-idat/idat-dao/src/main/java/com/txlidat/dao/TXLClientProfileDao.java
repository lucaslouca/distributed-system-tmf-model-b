package com.txlidat.dao;

import com.txlidat.domain.TXLClientProfile;
import com.txlcommon.TXLGenericDao;
import org.hibernate.Criteria;

import java.util.List;

/**
 * Created by lucas on 19/08/15.
 */
public interface TXLClientProfileDao extends TXLGenericDao<TXLClientProfile> {
    List<TXLClientProfile> listClientProfiles();

    List<TXLClientProfile> listClientProfiles(int start, int maxResult);

    TXLClientProfile fetchClientProfileWithPsnValue(String psnValue);
}
