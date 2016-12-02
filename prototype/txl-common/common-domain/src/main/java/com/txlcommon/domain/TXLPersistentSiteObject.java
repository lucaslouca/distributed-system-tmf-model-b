package com.txlcommon.domain;

import com.txlcommon.domain.site.TXLSite;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by lucas on 25/08/15.
 */
@MappedSuperclass
public abstract class TXLPersistentSiteObject extends TXLPersistentObject {
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(inverseJoinColumns = @JoinColumn(name = "site_id"))
    private Set<TXLSite> sites = new HashSet<TXLSite>();

    public Set<TXLSite> getSites() {
        return sites;
    }

    public void setSites(Set<TXLSite> sites) {
        this.sites = sites;
    }
}
