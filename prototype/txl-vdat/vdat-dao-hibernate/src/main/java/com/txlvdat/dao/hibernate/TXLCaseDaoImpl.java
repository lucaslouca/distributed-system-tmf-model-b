package com.txlvdat.dao.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.txlcommon.dao.hibernate.TXLAbstractSiteCheckingDao;
import com.txlvdat.dao.TXLCaseDao;
import com.txlvdat.domain.TXLCase;

public class TXLCaseDaoImpl extends TXLAbstractSiteCheckingDao implements TXLCaseDao {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}


	@Override
	public TXLCase save(TXLCase txlCase) {
		Session session = getSession();
		session.saveOrUpdate(txlCase);
		return txlCase;
	}


	@Override
	public TXLCase findById(Long id) {
		return (TXLCase) sessionFactory.getCurrentSession().get(TXLCase.class, id);

	}

	@Override
	public TXLCase update(TXLCase o) {
		return null;
	}

	@Override
	public void remove(TXLCase o) {

	}

	/**
	 * Fetch a persistent case that has the given pseudonym value.
	 * @param psnValue
	 * @return the case with the pseudonym. Null if no such case exists.
	 */
	@Override
	public TXLCase fetchCaseWithPsnValue(String psnValue) {
		TXLCase result = null;

		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(TXLCase.class);
		criteria.createAlias("psn", "psnField");
		criteria.add(Restrictions.eq("psnField.value", psnValue));

		List<TXLCase> resultList = criteria.list();

		if (resultList.size() > 0) {
			result = resultList.get(0);
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TXLCase> fetchCasesFromUserWithID(Long id) {

		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(TXLCase.class);
		criteria.add(Restrictions.eq("pentesterUserId", id));

		criteria = attachUserSiteCriteria(criteria);

		List<TXLCase> result = criteria.list();
		return result;
	}



}
