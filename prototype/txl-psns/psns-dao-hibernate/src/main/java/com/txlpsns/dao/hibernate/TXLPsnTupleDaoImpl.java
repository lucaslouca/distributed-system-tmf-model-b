package com.txlpsns.dao.hibernate;

import com.txlcommon.domain.psn.TXLPsnType;
import com.txlcommon.domain.psn.TXLPsn;
import com.txlpsns.dao.TXLPsnTupleDao;
import com.txlpsns.domain.TXLPsnTuple;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by lucas on 17/08/15.
 */
public class TXLPsnTupleDaoImpl implements TXLPsnTupleDao {
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * Fetch counterpart pseudonyms from the database.
     *
     * @param psn The pseudonym for which we want its counterpart.
     * @param resultType The type of the counterpart pseudonyms we expect.
     * @return List of resolved values for psn. Returns an empty List if psn doesn't exist.
     */
    @Override
    public List<TXLPsn> getCounterpartPsnsForPsn(TXLPsn psn, TXLPsnType resultType) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(TXLPsnTuple.class);

        // resultType.name().toLowerCase() must be the same as the field name we want to return, e.g. psn1
        String resultFieldName = resultType.name().toLowerCase();
        String queryFieldName = psn.getType().name().toLowerCase();
        Projection counterPartPsnProjection;

        criteria.createAlias(queryFieldName, "psn");
        criteria.add(Restrictions.eq("psn.value", psn.getValue()));
        counterPartPsnProjection = Projections.property(resultFieldName);
        criteria.setProjection(counterPartPsnProjection);

        List<TXLPsn> result = criteria.list();
        return result;
    }

    /**
     * Save a pseudonym tuple.
     *
     * @param tuple
     * @return persisted instance with a generated identifier.
     */
    @Override
    public TXLPsnTuple save(TXLPsnTuple tuple) {
        Session session = getSession();
        session.save(tuple);
        return tuple;
    }

    @Override
    public TXLPsnTuple findById(Long id) {
        return null;
    }

    @Override
    public TXLPsnTuple update(TXLPsnTuple o) {
        return null;
    }

    @Override
    public void remove(TXLPsnTuple o) {

    }
}
