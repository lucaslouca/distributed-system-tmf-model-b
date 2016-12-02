package com.txlcommon.presentation.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.txlcommon.TXLTokenService;
import com.txlcommon.presentation.security.handler.TXLTokenAuthenticationSuccessHandler;

/**
 * <h1>TXLAuthenticationTokenProcessingFilter</h1> Filter that handles token
 * authentication. The filter uses the TXLTokenService to verify the token value
 * included in the request header. If it is a valid token the Filter grands
 * access. If it is not a valid token the filter throws an exception.
 * <p/>
 * The filter is configured in spring-security.xml
 *
 * @author Lucas Louca
 * @version 1.0
 * @since 18.08.2015
 */
public class TXLAuthenticationTokenProcessingFilter extends AbstractAuthenticationProcessingFilter {
    private UserDetailsService userService;
    private TXLTokenService tokenService;

    /**
     * Constructor.
     *
     * @param defaultFilterProcessesUrl the path to guard. Example: /rest/**
     */
    public TXLAuthenticationTokenProcessingFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
        super.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(defaultFilterProcessesUrl));
        setAuthenticationManager(new AuthenticationManager() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                return null;
            }
        });
        setAuthenticationSuccessHandler(new TXLTokenAuthenticationSuccessHandler());
    }

    /**
     * Attempt to authenticate request.
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String token = request.getHeader(TXLTokenService.HEADER_SECURITY_TOKEN);
        AbstractAuthenticationToken userAuthenticationToken = authUserByToken(token);
        if (userAuthenticationToken == null) {
            throw new AuthenticationServiceException("Bad Token");
        }
        return userAuthenticationToken;
    }

    /**
     * Authenticate the user based on token.
     *
     * @return AbstractAuthenticationToken if token is valid. Returns null if token is not valid.
     */
    private AbstractAuthenticationToken authUserByToken(String token) {
        AbstractAuthenticationToken result = null;

        String userName = tokenService.getUserNameFromToken(token);
        if (userName != null) {
            UserDetails userDetails = this.userService.loadUserByUsername(userName);
            if (!tokenService.validateToken(token, userDetails)) {
                // Access Denied
                result = null;
            } else {
                // Grand Access
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                result = authentication;
            }
        }

        return result;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        super.doFilter(request, response, chain);
    }

    public UserDetailsService getUserService() {
        return userService;
    }

    public void setUserService(UserDetailsService userService) {
        this.userService = userService;
    }

    public TXLTokenService getTokenService() {
        return tokenService;
    }

    public void setTokenService(TXLTokenService tokenService) {
        this.tokenService = tokenService;
    }
}
