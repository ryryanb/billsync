package com.localtide.billsync.security;

import java.util.Base64;

import io.jsonwebtoken.security.Keys;

public class SecurityUtil {
	//public static final String JWT_SECRET = "fwio432360583j5l63jj5345805353805lrtwe";
	public static final byte[] JWT_SECRET_BYTES = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256).getEncoded();
    public static final String JWT_SECRET = Base64.getEncoder().encodeToString(JWT_SECRET_BYTES);
	public static final long   JWT_EXPIRATIONTIME = 3600000; // 3600000 = 60 minute 
	
	public static final String JWT_TOKEN_PARAM = "token";
	
	public static final String TOKEN_PREFIX = "Bearer";
	
	public static final String HEADER_STRING = "Authorization";
	
}
