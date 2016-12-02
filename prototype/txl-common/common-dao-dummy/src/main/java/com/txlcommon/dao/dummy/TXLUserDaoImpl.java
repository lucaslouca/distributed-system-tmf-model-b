package com.txlcommon.dao.dummy;

import com.txlcommon.TXLUserDao;

import com.txlcommon.domain.user.TXLUser;
import com.txlcommon.domain.user.TXLUserRole;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by lucas on 08/08/15.
 */
public class TXLUserDaoImpl implements TXLUserDao {
    public UserDetails getUserByUsername(String username) {
        TXLUser result = new TXLUser();
        result.setUsername("user");
        result.setPassword(new BCryptPasswordEncoder().encode("user"));
        result.addRole(new TXLUserRole(TXLUserRole.Role.ROLE_USER.name()));
        return result;
    }

    @Override
    public TXLUser saveUser(TXLUser user) {
        return null;
    }

}
