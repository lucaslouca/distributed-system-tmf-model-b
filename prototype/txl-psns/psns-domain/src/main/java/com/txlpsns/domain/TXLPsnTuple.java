package com.txlpsns.domain;

import com.txlcommon.domain.TXLPersistentObject;
import com.txlcommon.domain.psn.TXLPsn;

import javax.persistence.*;

/**
 * Class holding a pseudonym tuple.
 * <p/>
 * Created by lucas on 16/08/15.
 */
@Entity
@Table(name = "txl_psn_tuple")
public class TXLPsnTuple extends TXLPersistentObject {
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "psn1_id", nullable = false)
    private TXLPsn psn1;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "psn2_id", nullable = false)
    private TXLPsn psn2;

    public TXLPsnTuple() {
    }

    public TXLPsnTuple(TXLPsn psn1, TXLPsn psn2) {
        this.psn1 = psn1;
        this.psn2 = psn2;
    }

    public TXLPsn getPsn1() {
        return psn1;
    }

    public void setPsn1(TXLPsn psn1) {
        this.psn1 = psn1;
    }

    public TXLPsn getPsn2() {
        return psn2;
    }

    public void setPsn2(TXLPsn psn2) {
        this.psn2 = psn2;
    }

    @Override
    public String toString() {
        return "TXLPsnTuple{" +
                "psn1=" + psn1 +
                ", psn2=" + psn2 +
                '}';
    }
}
