package com.txlcommon.presentation.security.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.txlcommon.TXLTokenService;

/**
 * <h1>TXLCorsFilter</h1>
 * Filter that allows CORS.
 * <p/>
 * This filter is configured in web.xml
 *
 * @author Lucas Louca
 * @version 1.0
 * @since 18.08.2015
 */
public class TXLCorsFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String reqOrigin = request.getHeader("Origin");

        if (reqOrigin != null) {
            response.setHeader("Access-Control-Allow-Origin", reqOrigin);
        } else {
            response.setHeader("Access-Control-Allow-Origin", "*");
        }
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", TXLTokenService.HEADER_SECURITY_TOKEN + ", Content-Type, X-Requested-With");
        response.setHeader("Access-Control-Expose-Headers", TXLTokenService.HEADER_SECURITY_TOKEN);
        response.setHeader("Access-Control-Allow-Credentials", "true");
        if (!"OPTIONS".equals(request.getMethod())) {
            chain.doFilter(req, res);
        }
    }

    public void init(FilterConfig filterConfig) {
    }

    public void destroy() {
    }
}
