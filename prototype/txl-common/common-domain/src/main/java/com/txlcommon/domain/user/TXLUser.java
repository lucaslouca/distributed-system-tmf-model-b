package com.txlcommon.domain.user;

import java.util.*;

import com.txlcommon.domain.TXLPersistentObject;
import com.txlcommon.domain.site.TXLSite;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

/**
 * Created by lucas on 08/08/15.
 */

@Entity
@Table(name = "txl_user")
public class TXLUser extends TXLPersistentObject implements UserDetails {
    private String username;
    private String password;

    private boolean enabled;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;

    @Enumerated(EnumType.STRING)
    private TXLUserType type;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "txl_user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<TXLUserRole> roles = new HashSet<TXLUserRole>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "txl_user_site", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "site_id"))
    private Set<TXLSite> sites = new HashSet<TXLSite>();

    public TXLUser() {}

    @Override
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (TXLUserRole role : this.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public Set<TXLUserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<TXLUserRole> roles) {
        this.roles = roles;
    }

    public void addRole(TXLUserRole role) {
        this.roles.add(role);
    }

    public TXLUserType getType() {
        return type;
    }

    public void setType(TXLUserType type) {
        this.type = type;
    }

    public Set<TXLSite> getSites() {
        return sites;
    }

    public void setSites(Set<TXLSite> sites) {
        this.sites = sites;
    }

    @Override
    public String toString() {
        return "TXLUser{" +
                "id=" + getId() +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", accountNonExpired=" + accountNonExpired +
                ", accountNonLocked=" + accountNonLocked +
                ", credentialsNonExpired=" + credentialsNonExpired +
                ", type=" + type +
                ", roles=" + roles +
                '}';
    }
}
