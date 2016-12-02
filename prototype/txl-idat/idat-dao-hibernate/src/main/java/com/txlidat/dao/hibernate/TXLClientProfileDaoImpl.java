package com.txlidat.dao.hibernate;

import com.txlcommon.dao.hibernate.TXLAbstractSiteCheckingDao;
import com.txlidat.dao.TXLClientProfileDao;
import com.txlidat.domain.TXLClientProfile;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.*;

import java.util.List;

public class TXLClientProfileDaoImpl extends TXLAbstractSiteCheckingDao implements TXLClientProfileDao {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }


    @Override
    public TXLClientProfile save(TXLClientProfile clientProfile) {
        Session session = getSession();
        session.saveOrUpdate(clientProfile);
        return clientProfile;
    }

    @Override
    public TXLClientProfile findById(Long id) {
        return (TXLClientProfile) sessionFactory.getCurrentSession().get(TXLClientProfile.class, id);
    }

    @Override
    public TXLClientProfile update(TXLClientProfile o) {
        return null;
    }

    @Override
    public void remove(TXLClientProfile o) {

    }

    /**
     * Fetch all client profiles.
     *
     * @return list containing all client profiles in the database
     */
    @Override
    public List<TXLClientProfile> listClientProfiles() {
        List<TXLClientProfile> result;

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(TXLClientProfile.class);
        criteria = attachUserSiteCriteria(criteria);

        result = criteria.list();

        return result;
    }

    /**
     * Fetch a set of client profiles.
     *
     * @param start the start index
     * @param maxResult number of client profiles to fetch
     * @return
     */
    @Override
    public List<TXLClientProfile> listClientProfiles(int start, int maxResult) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(TXLClientProfile.class);

        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.addOrder(Order.desc("id"));
        criteria.setFirstResult(start);
        criteria.setMaxResults(maxResult);

        criteria = attachUserSiteCriteria(criteria);

        List<TXLClientProfile> result = criteria.list();
        return result;
    }

    /**
     * Fetch a persistent client profile that has the given pseudonym value.
     * @param psnValue
     * @return the client profile with the pseudonym. Null if no such client profile exists.
     */
    @Override
    public TXLClientProfile fetchClientProfileWithPsnValue(String psnValue) {
        TXLClientProfile result = null;

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(TXLClientProfile.class, "clientProfile");
        criteria.createAlias("psn", "psnField");
        criteria.add(Restrictions.eq("psnField.value", psnValue));

        criteria = attachUserSiteCriteria(criteria);

        List<TXLClientProfile> resultList = criteria.list();

        if (resultList.size() > 0) {
            result = resultList.get(0);
        }

        return result;
    }
}
