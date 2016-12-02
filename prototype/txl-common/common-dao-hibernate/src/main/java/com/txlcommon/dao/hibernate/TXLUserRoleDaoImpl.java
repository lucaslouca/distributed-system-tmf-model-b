package com.txlcommon.dao.hibernate;

import com.txlcommon.TXLUserRoleDao;
import com.txlcommon.domain.user.TXLUserRole;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Created by lucas on 23/08/15.
 */
public class TXLUserRoleDaoImpl implements TXLUserRoleDao {
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public TXLUserRole save(TXLUserRole role) {
        Session session = getSession();
        session.saveOrUpdate(role);
        return role;
    }

    @Override
    public TXLUserRole findById(Long id) {
        return null;
    }

    @Override
    public TXLUserRole update(TXLUserRole o) {
        return null;
    }

    @Override
    public void remove(TXLUserRole o) {

    }
}
