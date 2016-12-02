package com.txlcommon;

import org.springframework.security.core.userdetails.UserDetails;


public interface TXLTokenService {
    public static final String HEADER_SECURITY_TOKEN = "X-Trixl-Token";

    public String createToken(UserDetails userDetails);

    public String computeSignature(UserDetails userDetails, long expires);

    public String getUserNameFromToken(String authToken);

    public boolean validateToken(String authToken, UserDetails userDetails);
}
