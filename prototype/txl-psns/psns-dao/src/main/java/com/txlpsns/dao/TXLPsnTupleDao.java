package com.txlpsns.dao;

import com.txlcommon.domain.psn.TXLPsnType;
import com.txlcommon.domain.psn.TXLPsn;
import com.txlpsns.domain.TXLPsnTuple;
import com.txlcommon.TXLGenericDao;

import java.util.List;

/**
 * Created by lucas on 17/08/15.
 */
public interface TXLPsnTupleDao  extends TXLGenericDao<TXLPsnTuple> {
    List<TXLPsn> getCounterpartPsnsForPsn(TXLPsn psn, TXLPsnType resultType);
}