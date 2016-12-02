package com.txlcommon.presentation.security.dto;

/**
 * JSON serializable DTO containing User Credentials
 */
public class TXLUserCredentialsDTO {
    private String username;
    private String password;

    public TXLUserCredentialsDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
