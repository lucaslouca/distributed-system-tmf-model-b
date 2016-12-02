package com.txlcommon.presentation.security.entrypoint;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * <h1>TXLAuthenticationEntryPoint</h1>
 * <p/>
 * Whenever a user tries to access resources without authenticating it will return an the following.
 *
 * @author Lucas Louca
 * @version 1.0
 * @since 18.08.2015
 */

public class TXLAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Authentication token was either missing or invalid.");
    }
}
