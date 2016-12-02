package com.txlcommon.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by lucas on 22/08/15.
 */
@MappedSuperclass
public abstract class TXLPersistentObject implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
