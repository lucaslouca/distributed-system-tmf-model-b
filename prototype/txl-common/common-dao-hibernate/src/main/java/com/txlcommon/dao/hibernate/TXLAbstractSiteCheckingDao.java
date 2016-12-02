package com.txlcommon.dao.hibernate;

import com.txlcommon.domain.user.TXLUser;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by lucas on 29/08/15.
 */
public abstract class TXLAbstractSiteCheckingDao {
    /**
     * Attach restrictions that check if the user has a site included in the
     * query object's site set.
     *
     * @param criteria original criteria to query a TXLPersistentSiteObject (query object)
     * @return original criteria with additional restrictions for the user sites.
     */
    protected Criteria attachUserSiteCriteria(Criteria criteria) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        TXLUser user = (TXLUser) auth.getPrincipal();
        DetachedCriteria userCriteria = DetachedCriteria.forClass(TXLUser.class, "user");
        userCriteria.add(Restrictions.eq("user.username", user.getUsername()));
        userCriteria.createAlias("sites", "userSites");
        criteria.createAlias("sites", "objectSites");
        userCriteria.add(Property.forName("objectSites.name").eqProperty("userSites.name"));
        criteria.add(Subqueries.exists(userCriteria.setProjection(Projections.property("userSites.name"))));

        return criteria;
    }
}
