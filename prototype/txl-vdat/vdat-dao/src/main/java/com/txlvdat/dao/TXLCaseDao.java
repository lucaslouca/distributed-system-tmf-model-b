package com.txlvdat.dao;

import java.util.List;

import com.txlcommon.TXLGenericDao;
import com.txlvdat.domain.TXLCase;

public interface TXLCaseDao extends TXLGenericDao<TXLCase> {
	TXLCase fetchCaseWithPsnValue(String psnValue);

	List<TXLCase> fetchCasesFromUserWithID(Long id); 
}
