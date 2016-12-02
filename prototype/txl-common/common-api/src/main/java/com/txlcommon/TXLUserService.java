package com.txlcommon;

import com.txlcommon.domain.user.TXLUser;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created by lucas on 08/08/15.
 */
public interface TXLUserService extends UserDetailsService {
    TXLUser saveUser(TXLUser user);
}
