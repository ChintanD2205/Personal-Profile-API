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
import com.privacy.Model.Entity.Employee;
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
        	if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is required");
            }

            if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is required");
            }


            Client client = authBLL.login(request.getEmail(), request.getPassword());


            if (client == null) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
            }
            
            List<String> roles = authBLL.getRolesForUser(client.getUserID());
            String token = jwtUtil.generateToken(client.getUserID(), roles);

            HashMap<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("username", client.getUsername());

            return ResponseEntity.ok(response);

        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Login failed due to server error");
        }
    }
    
    @PostMapping("/admin/login")
    public ResponseEntity<HashMap<String, String>> adminLogin(@RequestBody LoginRequestDTO request) {
        try {
        	if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is required");
            }

            if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is required");
            }


            Employee employee = authBLL.adminLogin(request.getEmail(), request.getPassword());


            if (employee == null) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
            }
            
            List<String> roles = authBLL.getRolesForUser(employee.getEmployeeID());
            String token = jwtUtil.generateToken(employee.getEmployeeID(), roles);

            HashMap<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("username", employee.getUsername());

            return ResponseEntity.ok(response);

        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Login failed due to server error");
        }
    }
}