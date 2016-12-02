package com.txlvdat.service;

import java.util.List;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import com.txlcommon.domain.psn.TXLPsn;
import com.txlcommon.domain.site.TXLSite;
import com.txlcommon.domain.user.TXLUser;
import com.txlvdat.api.TXLCaseDoesNotExistException;
import com.txlvdat.api.TXLCaseService;
import com.txlvdat.dao.TXLCaseDao;
import com.txlvdat.domain.TXLCase;

public class TXLCaseServiceImpl implements TXLCaseService {
	private TXLCaseDao caseDao;

	public void setCaseDao(TXLCaseDao caseDao) {
		this.caseDao = caseDao;
	}

	/**
	 * Create a case with the given pseudonym.
	 *
	 * @param psn the pseudonym for the case.
	 * @return the created case.
	 */
	@Override
	@Transactional
	public TXLCase createCaseWithPsn(TXLPsn psn) {
		TXLCase result = new TXLCase();
		result.setPsn(psn);

		// Get user
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TXLUser user = (TXLUser) auth.getPrincipal();
		Set<TXLSite> userSites = user.getSites();
		if (!userSites.isEmpty()) {
			result.getSites().add(userSites.iterator().next());
		}

		caseDao.save(result);
		return result;
	}


	/**
	 * Update a given case.
	 *
	 * @param txlCase the case to update.
	 * @return the updated case.
	 */
	@Override
	@Transactional
	public TXLCase update(TXLCase txlCase) {
		TXLCase dbObject = caseDao.findById(txlCase.getId());

		dbObject.setClientProfilePsnToBeResolved(txlCase.getClientProfilePsnToBeResolved());
		dbObject.setNumber(txlCase.getNumber());
		dbObject.setState(txlCase.getState());
		dbObject.setType(txlCase.getType());
		dbObject.setTemplate(txlCase.getTemplate());
		return caseDao.save(dbObject);
	}

	/**
	 * Get case with a given psn.
	 *
	 * @param psnValue
	 * @return the case with psn
	 * @throws TXLCaseDoesNotExistException if no case for the given psn exists.
	 */
	@Override
	@Transactional(readOnly = true)
	public TXLCase caseForPsnValue(String psnValue) throws TXLCaseDoesNotExistException {
		TXLCase result = caseDao.fetchCaseWithPsnValue(psnValue);
		if (result == null) {
			throw new TXLCaseDoesNotExistException("Case with psn="+psnValue+" does not exist!");
		}

		return result;
	}

	/**
	 * Get a case with a given id.
	 * @param id
	 * @return the case with the id
	 * @throws TXLCaseDoesNotExistException if no case for the given id exists.
	 */
	@Override
	@Transactional
	public TXLCase caseWithId(Long id) throws TXLCaseDoesNotExistException {
		TXLCase result = caseDao.findById(id);
		if (result == null) {
			throw new TXLCaseDoesNotExistException("Case with id '"+id+"' does not exist!");
		}

		return result;
	}


	/**
	 * Fetches all cases that are assigned to the specified user.
	 * @param user The specified user
	 * @return all cases
	 */
	@Override
	@Transactional(readOnly = true)
	public List<TXLCase> getCasesAssignedToUser(TXLUser user) {
		return caseDao.fetchCasesFromUserWithID(user.getId());
	}
}
