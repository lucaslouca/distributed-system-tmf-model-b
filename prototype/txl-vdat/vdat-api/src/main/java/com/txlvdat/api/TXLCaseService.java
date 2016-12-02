package com.txlvdat.api;


import java.util.List;

import com.txlcommon.domain.psn.TXLPsn;
import com.txlcommon.domain.user.TXLUser;
import com.txlvdat.domain.TXLCase;

public interface TXLCaseService {
	TXLCase createCaseWithPsn(TXLPsn psn);

	TXLCase update(TXLCase txlCase);

	TXLCase caseWithId(Long id) throws TXLCaseDoesNotExistException;

	TXLCase caseForPsnValue(String psnValue) throws TXLCaseDoesNotExistException;

	List<TXLCase> getCasesAssignedToUser(TXLUser user);
}
