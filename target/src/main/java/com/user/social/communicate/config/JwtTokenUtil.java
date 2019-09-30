package com.user.social.communicate.config;

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
public class JwtTokenUtil implements Serializable {
	private static final long serialVersionUID = -2550185165626007488L;
    
	private static final long JWT_TOKEN_VALIDITY=5*60*60;
	
	@Value("${jwt.secret}")
	private String secret;
	
	//retrive username from jwt token
	public String getUsernameFromToken(String token) { 
		System.err.println(" JwtTokenUtil getUsernameFromToken before token: "+token);
		String usernameToken= getClaimFromToken(token,Claims::getSubject);
		System.out.println(" JwtTokenUtil getUsernameFromToken After token: "+usernameToken);
		return usernameToken;
	}
    //retrive expiration date from jwt token
	 public Date getExpirationDatefromToken(String token) {
			System.err.println(" JwtTokenUtil getExpirationDatefromToken token: "+token);
			Date dataformateToken= getClaimFromToken(token, Claims::getExpiration);
			System.err.println(" JwtTokenUtil getExpirationDatefromToken token: "+dataformateToken);
          return dataformateToken;
	 }
	
	private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		System.out.println(" JwtTokenUtil getClaimFromToken token: "+token);
		System.err.println(" JwtTokenUtil getClaimFromToken token: "+claimsResolver);
		final Claims claims=getAllClaimsfromToken(token);
		System.err.println(" JwtTokenUtil getClaimFromToken after token: "+claims);
		return claimsResolver.apply(claims);
	}
	//for retrieveing any information from token we will need the secret key
		private Claims getAllClaimsfromToken(String token) {
			return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		}
		//check if the token has expired
		private Boolean isTokenExpired(String token) {
			final Date expiration = getExpirationDatefromToken(token);
			return expiration.before(new Date());
		}
		//generate token for user
		public String generateToken(UserDetails userDetails) {
			Map<String, Object> claims = new HashMap<>();
			return doGenerateToken(claims, userDetails.getUsername());
		}
		
		//while creating the token -
		//1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
		//2. Sign the JWT using the HS512 algorithm and secret key.
		//3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
		//   compaction of the JWT to a URL-safe string 
		private String doGenerateToken(Map<String, Object> claims, String subject) {

			return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
					.signWith(SignatureAlgorithm.HS512, secret).compact();
		}
		//validate token
		public Boolean validateToken(String token, UserDetails userDetails) {
			final String username = getUsernameFromToken(token);
			return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
		}
}
