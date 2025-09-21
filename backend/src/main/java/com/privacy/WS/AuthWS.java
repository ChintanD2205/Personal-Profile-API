package com.privacy.WS;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.privacy.BLL.AuthBLL;
import com.privacy.Model.DTO.LoginRequestDTO;
import com.privacy.Model.Entity.Client;
import com.privacy.security.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthWS {
	
	@Autowired
	private AuthBLL authBLL;
	
	@Autowired
	JwtUtil jwtUtil;
	
	@PostMapping("/login")
	public HashMap<String, String> login(@RequestBody LoginRequestDTO request)
	{
		Client client = authBLL.login(request.getEmail(), request.getPassword());
		
		if (client == null || client.getUsername() == null) {
	        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
	    }
		
		List<String> roles = authBLL.getRolesForUser(client.getUserID());
		
	    HashMap<String, String> response = new HashMap<>();
	    response.put("token", jwtUtil.generateToken(client.getUsername(), roles));
	    return response;
	}
}
