package com.txlcommon.presentation.security.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.txlcommon.TXLTokenService;
import com.txlcommon.TXLUserService;
import com.txlcommon.domain.user.TXLUser;
import com.txlcommon.presentation.security.dto.TXLUserCredentialsDTO;
import com.txlcommon.presentation.security.dto.TXLUserDTO;

/**
 * <h1>TXLAuthenticationController</h1>
 * <p/>
 * Controller that handles user authentication and returns an authentication token.
 *
 * @author Lucas Louca
 * @version 1.0
 * @since 18.08.2015
 */

@RequestMapping("/services")
public class TXLAuthenticationController {

	private TXLUserService userService;

	private TXLTokenService tokenService;

	private AuthenticationManager authenticationManager;

	@ResponseBody
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public TXLUserDTO authenticate(HttpServletRequest request, HttpServletResponse response, @RequestBody TXLUserCredentialsDTO credentials) {
		String username = credentials.getUsername();
		String password = credentials.getPassword();

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		TXLUser user = (TXLUser) this.userService.loadUserByUsername(username);
		String token = tokenService.createToken(user);
		response.setHeader(TXLTokenService.HEADER_SECURITY_TOKEN, token);

		ModelMapper modelMapper = new ModelMapper();
		TXLUserDTO result = modelMapper.map(user, TXLUserDTO.class);

		return result;
	}


	public void setUserService(TXLUserService userService) {
		this.userService = userService;
	}

	public void setTokenService(TXLTokenService tokenService) {
		this.tokenService = tokenService;
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

}
