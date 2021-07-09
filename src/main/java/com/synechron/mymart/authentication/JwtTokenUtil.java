package com.synechron.mymart.authentication;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Component
public class JwtTokenUtil implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private static final long JWT_TOKEN_VALIDITY = 50L * 60L * 60L;
	
	@Value("${jwt.secret}")
	private String secret;
	
	public String getUserName(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver){
		final var claims = getAllClaimsFromToken(token);
		return claimResolver.apply(claims);
	}
	
	public Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}
	
	public String generateToken(String userName) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, userName);
	}

	private String doGenerateToken(Map<String, Object> claims, String subject) {

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}
	
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}
	
	private Boolean isTokenExpired(String token) {
		final var expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUserName(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}
