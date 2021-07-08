package com.synechron.mymart.authentication;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


@Component
public class JwtTokenFilter extends OncePerRequestFilter{

	@Autowired 
	private CustomUserDetailsService customUserDetailsSrervice;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil; 
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
		if(header == null || header.isEmpty() || !header.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		final String token = header.split(" ")[1].trim();
		UserDetails userDetails = customUserDetailsSrervice.loadUserByUsername(jwtTokenUtil.getUserName(token));
		
		if(!jwtTokenUtil.validateToken(token, userDetails)) { 
			filterChain.doFilter(request, response);
		 	return;
		}
		
		UsernamePasswordAuthenticationToken passwordAuthenticationToken = 
					new UsernamePasswordAuthenticationToken(userDetails, null,
			                userDetails == null ?
			                        List.of() : userDetails.getAuthorities());
		
		passwordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		
		SecurityContextHolder.getContext().setAuthentication(passwordAuthenticationToken);
		filterChain.doFilter(request, response);
	}

}
