package com.txlidat.domain;

import com.txlcommon.domain.TXLPersistentObject;
import com.txlcommon.domain.psn.TXLPsn;
import com.txlcommon.domain.user.TXLUser;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by lucas on 22/08/15.
 */
@Entity
@Table(name = "txl_client")
public class TXLClient extends TXLPersistentObject {
    private String name;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "psn_id", nullable = true)
    private TXLPsn eid;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "txl_client_user", joinColumns = @JoinColumn(name = "client_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<TXLUser> endClientUsers = new HashSet<TXLUser>();

    public TXLClient() {}

    public TXLPsn getEid() { return eid; }

    public void setEid(TXLPsn eid) {
        this.eid = eid;
    }

    public Set<TXLUser> getEndClientUsers() {
        return endClientUsers;
    }

    public void setEndClientUsers(Set<TXLUser> endClientUsers) {
        this.endClientUsers = endClientUsers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
