package com.privacy.WS;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<HashMap<String, String>> login(@RequestBody LoginRequestDTO request) {
        try {
            System.out.println(">>> LOGIN START - Email: " + request.getEmail() + " Password length: " + (request.getPassword() != null ? request.getPassword().length() : 0));

            if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
                System.out.println(">>> LOGIN FAILED - Email validation");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is required");
            }

            if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
                System.out.println(">>> LOGIN FAILED - Password validation");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is required");
            }

            System.out.println(">>> LOGIN - About to call authBLL.login()");
            Client client = authBLL.login(request.getEmail(), request.getPassword());
            System.out.println(">>> LOGIN - Client returned: " + (client != null ? "NOT NULL" : "NULL"));

            if (client == null) {
                System.out.println(">>> LOGIN FAILED - Client is null");
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
            }
            
            System.out.println(">>> LOGIN - Client username: " + client.getUsername() + " UserID: " + client.getUserID());

            System.out.println(">>> LOGIN - About to get roles for user: " + client.getUserID());
            List<String> roles = authBLL.getRolesForUser(client.getUserID());
            System.out.println(">>> LOGIN - Roles returned: " + roles);

            System.out.println(">>> LOGIN - About to generate JWT token");
            String token = jwtUtil.generateToken(client.getUserID(), roles);
            System.out.println(">>> LOGIN - Token generated: " + (token != null ? "SUCCESS" : "FAILED"));

            HashMap<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("username", client.getUsername());

            System.out.println(">>> LOGIN SUCCESS - Returning response");
            return ResponseEntity.ok(response);

        } catch (ResponseStatusException e) {
            System.out.println(">>> LOGIN - ResponseStatusException: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.out.println(">>> LOGIN - Unexpected Exception: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Login failed due to server error");
        }
    }
}