package com.virtusa.tariffmanagerrest.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	Logger logger = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);

	@Override
	public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			Authentication authentication) throws IOException, ServletException {
		/*
		 * do some logic here if you want something to be done whenever the user
		 * successfully logs in.
		 */

		HttpSession session = httpServletRequest.getSession();
		CustomUserDetails authUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		session.setAttribute("email", authUser.getUsername());
		session.setAttribute("authorities", authentication.getAuthorities());

		httpServletResponse.setStatus(HttpServletResponse.SC_OK);

		handle(httpServletResponse, authentication);

	}

	protected void handle(HttpServletResponse httpServletResponse,
			Authentication authentication) throws IOException {
		String targetUrl = determineTargetUrl(authentication);

		if (httpServletResponse.isCommitted()) {
			logger.debug("Response has already been committed. Unable to redirect to {}",targetUrl);
			return;
		}
		httpServletResponse.sendRedirect(targetUrl);
	}

	protected String determineTargetUrl(Authentication authentication) {
		Map<String, String> roleTargetUrlMap = new HashMap<>();
	    roleTargetUrlMap.put("ROLE_EMPLOYEE", "/employee");
	    roleTargetUrlMap.put("ROLE_MANAGER", "/manager");
	    roleTargetUrlMap.put("ROLE_ADMIN", "/admin");

	    final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
	    for (final GrantedAuthority grantedAuthority : authorities) {
	        String authorityName = grantedAuthority.getAuthority();
	        if(roleTargetUrlMap.containsKey(authorityName)) {
	            return roleTargetUrlMap.get(authorityName);
	        }
	    }

	    throw new IllegalStateException();
	}
}