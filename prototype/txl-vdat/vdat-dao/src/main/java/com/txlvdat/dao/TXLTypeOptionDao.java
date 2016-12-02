package com.txlvdat.dao;

import com.txlcommon.TXLGenericDao;
import com.txlvdat.domain.TXLType;
import com.txlvdat.domain.TXLTypeOption;

import java.util.List;

/**
 * Created by lucas on 08/11/15.
 */
public interface TXLTypeOptionDao extends TXLGenericDao<TXLTypeOption> {
    List<TXLTypeOption> fetchOptionsForType(TXLType type);
    TXLTypeOption fetchOptionForTypeAndValue(TXLType type, String value);
}
