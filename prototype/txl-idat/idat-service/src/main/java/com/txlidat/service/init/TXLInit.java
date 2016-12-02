package com.txlidat.service.init;

/**
 * Created by lucas on 22/08/15.
 */

import com.txlcommon.TXLUserService;
import com.txlcommon.domain.psn.TXLPsnType;
import com.txlcommon.domain.psn.TXLPsn;
import com.txlcommon.domain.user.TXLUser;
import com.txlidat.api.TXLClientService;
import com.txlidat.domain.TXLClient;
import org.hibernate.SessionFactory;

import javax.sql.DataSource;
import java.util.*;

public class TXLInit {
    private DataSource dataSource;
    private SessionFactory sessionFactory;
    private TXLUserService userService;
    private TXLClientService clientService;
    private Map<String, List<String>> clientNameToUsers;

    public void fill() {
        clientNameToUsers = new HashMap<String, List<String>>();
        clientNameToUsers.put("Loreal", Arrays.asList("admin", "ecuser1", "ecuser2"));
        clientNameToUsers.put("AMEX", Arrays.asList("ecuser3"));
        clientNameToUsers.put("Universal", Arrays.asList("ecuser4"));

        TXLClient client;
        for (String clientName:clientNameToUsers.keySet()) {
            System.out.println("Creating client '"+clientName+"'");
            client = new TXLClient();
            client.setName(clientName);
            client.setEid(new TXLPsn(TXLPsnType.PSN2, UUID.randomUUID().toString()));

            // Add users
            List<String> usernames = clientNameToUsers.get(clientName);
            TXLUser user;
            for (String username:usernames) {
                user = (TXLUser) userService.loadUserByUsername(username);
                client.getEndClientUsers().add(user);
            }

            // save client
            clientService.saveClient(client);
        }
    }

    public void setClientService(TXLClientService clientService) {
        this.clientService = clientService;
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

}
