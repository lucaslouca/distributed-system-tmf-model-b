package com.txlcommon.domain.site;

import com.txlcommon.domain.TXLPersistentObject;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * A site represents a realm from which the user is allowed to fetch certain data from the database.
 *
 * Created by lucas on 23/08/15.
 */
@Entity
@Table(name = "txl_site")
public class TXLSite extends TXLPersistentObject {
    private String name;

    public TXLSite(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TXLSite{" +
                "name='" + name + '\'' +
                '}';
    }
}
