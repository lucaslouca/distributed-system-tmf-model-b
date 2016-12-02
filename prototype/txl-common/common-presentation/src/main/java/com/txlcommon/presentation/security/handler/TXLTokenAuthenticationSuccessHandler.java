package com.txlcommon.presentation.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * <h1>TXLTokenAuthenticationSuccessHandler</h1>
 * <p/>
 * AuthenticationSuccessHandler that forwards the user to the target URL on authentication success.
 *
 * @author Lucas Louca
 * @version 1.0
 * @since 18.08.2015
 */
public class TXLTokenAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
        String context = request.getContextPath();
        String fullURL = request.getRequestURI();
        String url = fullURL.substring(fullURL.indexOf(context) + context.length());
        return url;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String url = determineTargetUrl(request, response);
        request.getRequestDispatcher(url).forward(request, response);
    }

}
