package com.txlcommon.service.init;

/**
 * Created by lucas on 22/08/15.
 */

import com.txlcommon.TXLSiteService;
import com.txlcommon.TXLUserRoleService;
import com.txlcommon.TXLUserService;
import com.txlcommon.domain.site.TXLSite;
import com.txlcommon.domain.user.TXLUser;
import com.txlcommon.domain.user.TXLUserRole;
import com.txlcommon.domain.user.TXLUserType;
import org.hibernate.SessionFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import java.util.*;

public class TXLInitDatabaseUsers {
    private DataSource dataSource;
    private SessionFactory sessionFactory;
    private TXLUserService userService;
    private TXLSiteService siteService;
    private TXLUserRoleService userRoleService;

    private List<String> endClientUsers = Arrays.asList("ecuser1", "ecuser2", "ecuser3", "ecuser4");
    private List<String> pentesterUsers = Arrays.asList("puser1", "puser2", "puser3", "puser4");
    private List<String> siteNames = Arrays.asList("SiteA", "SiteB");
    private List<TXLSite> sites = new ArrayList<TXLSite>();
    private Map<String, TXLUserRole> roleName2Role = new HashMap<String, TXLUserRole>();

    private void createRoles() {
        TXLUserRole userRole = new TXLUserRole(TXLUserRole.Role.ROLE_USER.name());
        roleName2Role.put(TXLUserRole.Role.ROLE_USER.name(), userRole);
        userRoleService.saveRole(userRole);

        TXLUserRole adminRole = new TXLUserRole(TXLUserRole.Role.ROLE_ADMIN.name());
        roleName2Role.put(TXLUserRole.Role.ROLE_ADMIN.name(), adminRole);
        userRoleService.saveRole(adminRole);
    }

    private void createSites() {
        TXLSite site;
        for (String siteName : siteNames) {
            site = new TXLSite();
            site.setName(siteName);
            siteService.saveSite(site);
            sites.add(site);
        }
    }


    private void createAdmin() {
        System.out.println("Create user 'admin'' with password 'admin'");
        TXLUser user = this.generateUser("admin");
        user.setType(TXLUserType.PENTESTER);
        user.getRoles().add(roleName2Role.get(TXLUserRole.Role.ROLE_ADMIN.name()));

        user.getSites().add(sites.get(0));
        user.getSites().add(sites.get(1));

        userService.saveUser(user);
    }

    public void fill() {
        createRoles();
        createSites();
        createAdmin();

        TXLUser user;
        int count = 0;
        for (String username : endClientUsers) {
            System.out.println("Create end client user '" + username + "' with password '" + username + "'");
            user = this.generateUser(username);
            user.setType(TXLUserType.ENDCLIENT);

            user.getSites().add(sites.get(0));
            user.getSites().add(sites.get(1));
            System.out.println("************"+user.getSites());
            userService.saveUser(user);
        }

        for (String username : pentesterUsers) {
            System.out.println("Create pentester user '" + username + "' with password '" + username + "'");
            user = this.generateUser(username);
            user.setType(TXLUserType.PENTESTER);

            user.getSites().add(sites.get(0));
            user.getSites().add(sites.get(1));
            System.out.println("************" + user.getSites());
            userService.saveUser(user);
        }
    }

    private TXLUser generateUser(String username) {
        TXLUser result = new TXLUser();
        result.setEnabled(true);
        result.setUsername(username);
        result.setAccountNonExpired(true);
        result.setAccountNonLocked(true);
        result.setCredentialsNonExpired(true);
        result.setEnabled(true);

        result.getRoles().add(roleName2Role.get(TXLUserRole.Role.ROLE_USER.name()));


        // Hash password
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(username);
        result.setPassword(hashedPassword);
        return result;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public TXLUserService getUserService() {
        return userService;
    }

    public void setUserService(TXLUserService userService) {
        this.userService = userService;
    }

    public void setSiteService(TXLSiteService siteService) {
        this.siteService = siteService;
    }

    public void setUserRoleService(TXLUserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }
}
