package com.virtusa.tariffmanagerrest.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.virtusa.tariffmanagerrest.exception.JwtTokenNotFoundException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	
	private List<String> excludeUrlPatterns = new ArrayList<>(Arrays.asList(
			"/public/login",
			"public/register",
			"/swagger-ui/index.html",
			"/favicon.ico",
			"/v2/api-docs"
			));

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		if(request.getServletPath().contains("/swagger-ui") || request.getServletPath().contains("/swagger-resources")){
			  filterChain.doFilter(request, response);
			  return;
			}
		// 1.get token
		final String requestToken = request.getHeader("Authorization");
		// Bearer 23874jhgjnbdcbjk
		log.info(requestToken);

		String username = null;
		String token = null;

		if (requestToken != null && requestToken.startsWith("Bearer")) {
			token = requestToken.substring(7);

			try {
				username = this.jwtTokenHelper.getUsernameFromToken(token);
			} catch (IllegalArgumentException e) {

				throw new JwtTokenNotFoundException("unable to get jwt token");

			} catch (ExpiredJwtException e) {
				throw new JwtTokenNotFoundException("jwt token has expired");
			} catch (MalformedJwtException e) {
				throw new JwtTokenNotFoundException(" invalid jwt token");
			}

		} else
			throw new JwtTokenNotFoundException("Token does not starts with Bearer");

		// After getting the token we have to validate it.
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

			if (this.jwtTokenHelper.validateToken(token, userDetails).booleanValue()) {
				
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				WebAuthenticationDetails buildDetails = new WebAuthenticationDetailsSource().buildDetails(request);
				authenticationToken.setDetails(buildDetails);

				SecurityContextHolder.getContext().setAuthentication(authenticationToken);

			} else {
				throw new JwtTokenNotFoundException("Invalid jwt token");
			}

		} else {
			throw new UsernameNotFoundException("username is null or context is not null");

		}
		filterChain.doFilter(request, response);
	}

	 @Override
	    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
	        String path = request.getRequestURI();
	        return excludeUrlPatterns.contains(path);    
	    }
}

