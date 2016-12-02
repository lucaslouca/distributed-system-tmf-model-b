package com.txlcommon.service;

import com.txlcommon.TXLUserRoleDao;
import com.txlcommon.TXLUserRoleService;
import com.txlcommon.domain.user.TXLUserRole;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lucas on 23/08/15.
 */
public class TXLUserRoleServiceImpl implements TXLUserRoleService {
    private TXLUserRoleDao userRoleDao;

    public void setUserRoleDao(TXLUserRoleDao userRoleDao) {
        this.userRoleDao = userRoleDao;
    }

    @Override
    @Transactional
    public TXLUserRole saveRole(TXLUserRole role) {
        return userRoleDao.save(role);
    }
}
