package com.txlidat.domain;

import com.txlcommon.domain.TXLPersistentObject;
import com.txlcommon.domain.TXLPersistentSiteObject;
import com.txlcommon.domain.psn.TXLPsn;

import javax.persistence.*;

/**
 * Created by lucas on 19/08/15.
 * 
 * A Client Profile is associated with a TXLClient, but contains more details that are tied to a specific case.
 * For instance, a TXLClient would only state 'BMW', whereas a TXLClientProfile would be associated with 'BMW' but
 * would also contain more details like the person who put on the contract (e.g. John Hoffmann), the division within 
 * BMW (e.g. Logistics), the branch (e.g.: BMW, ITZ, München). In summary a Client Profile contains more detailed information
 * tied to a case that cannot be generalize for the Client overall.
 */
@Entity
@Table(name = "txl_client_profile")
public class TXLClientProfile extends TXLPersistentSiteObject {
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "psn_id", nullable = true)
    private TXLPsn psn;

    private String name;

    public TXLClientProfile() {
    }

    public TXLClientProfile(TXLPsn psn) {
        this.psn = psn;
    }

    public TXLPsn getPsn() {
        return psn;
    }

    public void setPsn(TXLPsn psn) {
        this.psn = psn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TXLClientProfile{" +
                "id=" + getId() +
                ", psn=" + psn +
                ", name='" + name + '\'' +
                '}';
    }
}
