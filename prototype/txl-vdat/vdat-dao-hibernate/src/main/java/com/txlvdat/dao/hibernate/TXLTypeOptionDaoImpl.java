package com.txlvdat.dao.hibernate;

import com.txlvdat.dao.TXLTypeOptionDao;
import com.txlvdat.domain.TXLCase;
import com.txlvdat.domain.TXLType;
import com.txlvdat.domain.TXLTypeOption;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by lucas on 08/11/15.
 */
public class TXLTypeOptionDaoImpl implements TXLTypeOptionDao {
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }


    @Override
    public List<TXLTypeOption> fetchOptionsForType(TXLType type) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(TXLTypeOption.class);
        criteria.add(Restrictions.eq("type", type));
        List<TXLTypeOption> result = criteria.list();
        return result;
    }

    @Override
    public TXLTypeOption fetchOptionForTypeAndValue(TXLType type, String value) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(TXLTypeOption.class);
        criteria.add(Restrictions.eq("type", type));
        criteria.add(Restrictions.eq("value", value));
        List<TXLTypeOption> result = criteria.list();
        if (result.size() > 0) {
            return result.get(0);
        } else {
            return null;
        }
    }

    @Override
    public TXLTypeOption save(TXLTypeOption typeOption) {
        Session session = getSession();
        session.saveOrUpdate(typeOption);
        return typeOption;
    }

    @Override
    public TXLTypeOption findById(Long id) {
        return null;
    }

    @Override
    public TXLTypeOption update(TXLTypeOption o) {
        return null;
    }

    @Override
    public void remove(TXLTypeOption o) {

    }
}
