package com.txlcommon.domain.user;

import com.txlcommon.domain.TXLPersistentObject;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by lucas on 08/08/15.
 */
@Entity
@Table(name = "txl_role")
public class TXLUserRole extends TXLPersistentObject {

    public static enum Role {
        ROLE_USER, ROLE_ADMIN
    }

    private static final long serialVersionUID = 8999306538704485972L;

    public TXLUserRole() {
    }

    private String name;

    public TXLUserRole(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
