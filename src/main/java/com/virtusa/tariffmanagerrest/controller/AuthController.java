package com.virtusa.tariffmanagerrest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.virtusa.tariffmanagerrest.exception.WrongPasswordException;
import com.virtusa.tariffmanagerrest.model.LoginModel;
import com.virtusa.tariffmanagerrest.security.JwtAuthResponse;
import com.virtusa.tariffmanagerrest.security.JwtTokenHelper;

@RestController
@RequestMapping("/public")
public class AuthController {

	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private AuthenticationManager authenticationManager;
	static final Logger log = LoggerFactory.getLogger(AuthController.class);

	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody LoginModel request) {
		String username = request.getUsername();
		String password = request.getPassword();
		this.authenticate(username, password);
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
		log.info("Generating token");
		String token = this.jwtTokenHelper.generateToken(userDetails);

		log.info(token);
		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);

		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	private void authenticate(String username, String password) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);		

		try {
			this.authenticationManager.authenticate(authenticationToken);
			log.info("authentication success");

		} catch (BadCredentialsException e) {
			throw new WrongPasswordException("Invalid password");
		}
	}

}
