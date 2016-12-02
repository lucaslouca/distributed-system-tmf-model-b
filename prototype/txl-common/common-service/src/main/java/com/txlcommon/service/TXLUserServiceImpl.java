package com.txlcommon.service;

import com.txlcommon.domain.user.TXLUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.txlcommon.TXLUserDao;
import com.txlcommon.TXLUserService;
import org.springframework.transaction.annotation.Transactional;

/**
 * <h1>TXLUserServiceImpl</h1>
 * <p/>
 * TXLUserService implementation for handling users.
 *
 * @author Lucas Louca
 * @version 1.0
 * @since 18.08.2015
 */

public class TXLUserServiceImpl implements TXLUserService {
    TXLUserDao userDao;

    public void setUserDao(TXLUserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.getUserByUsername(username);
    }

    @Override
    @Transactional
    public TXLUser saveUser(TXLUser user) {
        return userDao.saveUser(user);
    }
}

