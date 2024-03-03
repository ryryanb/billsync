package com.localtide.billsync.security;


import java.io.IOException;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
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
		logger.info("token:" + token);
		if (token != null && !token.startsWith("Basic")) {
			try{
				if (!authenticate((HttpServletResponse)response,token)){
					return;
				}

			}catch(Exception e) {
				
				logger.error(e.getMessage(),e);
				((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
				return;
			} 
		}else if (token ==null){
			//for image, document links
			token = ((HttpServletRequest) request).getParameter(SecurityUtil.JWT_TOKEN_PARAM);
			if (token !=null){
				if (!authenticate((HttpServletResponse)response,token)){
					return;
				}
			}
		}
		
		chain.doFilter(request, response);

	}
	
	private boolean authenticate(HttpServletResponse response, String token) throws IOException{
		logger.info(SecurityUtil.JWT_SECRET.length() +  SecurityUtil.JWT_SECRET);
		String actualToken = token.replace(SecurityUtil.TOKEN_PREFIX, "");
		actualToken = actualToken.replace(" ", "");
		logger.info(actualToken.length() + actualToken);
		Claims claims = Jwts.parser().setSigningKey(SecurityUtil.JWT_SECRET).parseClaimsJws(actualToken).getBody();
		if (claims == null || claims.getExpiration().before(new Date())) {
			logger.info("Here");
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return false;
		}

		String username = claims.getSubject();	
		if (!StringUtils.hasText(username)) {
			logger.info("There");
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return false;
		}
		
		UserDetails user = userDetailsService.loadUserByUsername(username);
		if (user == null) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return false;
		}
		
		Authentication authentication = new AuthenticationImpl((SecurityUser) user);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return true;
	}

}
