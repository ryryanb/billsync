package com.localtide.billsync.security;


import java.io.IOException;
import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthenticationProcessingFilter extends GenericFilterBean {

    protected final Log logger = LogFactory.getLog(getClass());

    private UserDetailsService userDetailsService;

    public JWTAuthenticationProcessingFilter(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String token = ((HttpServletRequest) request).getHeader(SecurityUtil.HEADER_STRING);
        logger.debug("token:" + token);
        if (token != null && !token.startsWith("Basic")) {
            try {
                if (!authenticate((HttpServletResponse) response, token)) {
                    return;
                }

            } catch (Exception e) {

                logger.error(e.getMessage(), e);
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        } else if (token == null) {
            // for image, document links
            token = ((HttpServletRequest) request).getParameter(SecurityUtil.JWT_TOKEN_PARAM);
            if (token != null) {
                if (!authenticate((HttpServletResponse) response, token)) {
                    return;
                }
            }
        }

        chain.doFilter(request, response);
    }

    private boolean authenticate(HttpServletResponse response, String token) throws IOException {
        Key signingKey = new SecretKeySpec(SecurityUtil.JWT_SECRET.getBytes(), SignatureAlgorithm.HS256.getJcaName());

        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(signingKey).build()
                    .parseClaimsJws(token.replace("Bearer ", "")).getBody();

            if (claims == null || claims.getExpiration().before(new Date())) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }

            String username = claims.getSubject();
            if (username == null || username.trim().isEmpty()) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }

            // Your additional authentication logic here...

            return true;
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
    }
}
