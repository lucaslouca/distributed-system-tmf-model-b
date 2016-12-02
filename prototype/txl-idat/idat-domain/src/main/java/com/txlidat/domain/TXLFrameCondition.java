package com.txlidat.domain;

import com.txlcommon.domain.TXLPersistentObject;
import com.txlcommon.domain.psn.TXLPsn;

import javax.persistence.*;

/**
 * Created by lucas on 22/08/15.
 */
@Entity
@Table(name = "txl_framecondition")
public class TXLFrameCondition extends TXLPersistentObject {
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "psn_id", nullable = true)
    private TXLPsn psn;

    private String overview;

    public TXLFrameCondition(){}

    public TXLPsn getPsn() {
        return psn;
    }

    public void setPsn(TXLPsn psn) {
        this.psn = psn;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    @Override
    public String toString() {
        return "TXLFrameCondition{" +
                "id=" + getId() +
                ", psn=" + psn +
                ", overview='" + overview + '\'' +
                '}';
    }
}
