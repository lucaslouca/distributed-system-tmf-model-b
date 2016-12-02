package com.txlcommon.dao.hibernate;

import com.txlcommon.TXLSiteDao;
import com.txlcommon.domain.site.TXLSite;
import com.txlcommon.domain.user.TXLUser;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

/**
 * Created by lucas on 23/08/15.
 */
public class TXLSiteDaoImpl implements TXLSiteDao {
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public TXLSite save(TXLSite site) {
        Session session = getSession();
        session.saveOrUpdate(site);
        return site;
    }

    @Override
    public TXLSite fetchSiteWithName(String name) {
        TXLSite result = null;

        Criteria criteria = getSession().createCriteria(TXLSite.class);
        criteria.add(Restrictions.eq("name", name));
        List<TXLSite> domainSites = criteria.list();

        if (domainSites.size() > 0) {
            result = domainSites.get(0);
        }

        return result;
    }
}
