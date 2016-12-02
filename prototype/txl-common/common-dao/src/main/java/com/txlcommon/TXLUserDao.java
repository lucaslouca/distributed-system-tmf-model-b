package com.txlcommon;

import com.txlcommon.domain.user.TXLUser;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created by lucas on 08/08/15.
 */
public interface TXLUserDao {
    public UserDetails getUserByUsername(String username);

    TXLUser saveUser(TXLUser user);
}
