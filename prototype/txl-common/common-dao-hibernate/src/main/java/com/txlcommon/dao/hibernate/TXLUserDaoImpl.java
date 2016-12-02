package com.txlcommon.dao.hibernate;

import com.txlcommon.TXLUserDao;
import com.txlcommon.domain.user.TXLUser;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

/**
 * Created by lucas on 22/08/15.
 */
public class TXLUserDaoImpl implements TXLUserDao {
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public TXLUser getUserByUsername(String username) {
        TXLUser result;

        Criteria criteria = getSession().createCriteria(TXLUser.class);
        criteria.add(Restrictions.eq("username", username));
        @SuppressWarnings("unchecked")
        List<TXLUser> domainUsers = criteria.list();

        if (domainUsers.size() > 0) {
            result = domainUsers.get(0);
        } else {
            throw new UsernameNotFoundException("Username '" + username + "' couldn't be found!");
        }

        return result;
    }

    @Override
    public TXLUser saveUser(TXLUser user) {
        Session session = getSession();
        session.saveOrUpdate(user);
        return user;
    }

}
