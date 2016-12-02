package com.txlpsns.dao.hibernate;

import com.txlcommon.domain.psn.TXLPsn;
import com.txlpsns.dao.TXLPsnDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Created by lucas on 16/08/15.
 */
public class TXLPsnDaoImpl implements TXLPsnDao {
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public TXLPsn save(TXLPsn o) {
        return null;
    }

    @Override
    public TXLPsn findById(Long id) {
        return null;
    }

    @Override
    public TXLPsn update(TXLPsn o) {
        return null;
    }

    @Override
    public void remove(TXLPsn o) {

    }
}
