package com.txlidat.dao.hibernate;

import com.txlcommon.dao.hibernate.TXLAbstractSiteCheckingDao;
import com.txlcommon.domain.psn.TXLPsn;
import com.txlidat.dao.TXLClientDao;
import com.txlidat.domain.TXLClient;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import java.util.List;

/**
 * Created by lucas on 22/08/15.
 */
public class TXLClientDaoImpl extends TXLAbstractSiteCheckingDao implements TXLClientDao {
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }


    @Override
    public TXLClient save(TXLClient client) {
        Session session = getSession();
        session.saveOrUpdate(client);
        return client;
    }

    @Override
    public TXLClient findById(Long id) {
        return null;
    }

    @Override
    public TXLClient update(TXLClient o) {
        return null;
    }

    @Override
    public void remove(TXLClient o) {

    }

    @Override
    public TXLPsn fetchEIDForUsername(String username) {
        TXLPsn result = null;

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(TXLClient.class);

        Projection counterPartPsnProjection;

        criteria.createAlias("endClientUsers", "endClientUsersList");
        criteria.add(Restrictions.eq("endClientUsersList.username", username));
        counterPartPsnProjection = Projections.property("eid");
        criteria.setProjection(counterPartPsnProjection);

        List<TXLPsn> resultList = criteria.list();
        if (!resultList.isEmpty()) {
            result = resultList.get(0);
        }

        return result;
    }

    @Override
    public List<TXLClient> listClients() {
        List<TXLClient> result;

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(TXLClient.class);

        result = criteria.list();

        return result;
    }

    @Override
    public List<TXLClient> listClients(int start, int maxResult) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(TXLClient.class);

        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.addOrder(Order.desc("id"));
        criteria.setFirstResult(start);
        criteria.setMaxResults(maxResult);

        List<TXLClient> result = criteria.list();
        return result;
    }
}
