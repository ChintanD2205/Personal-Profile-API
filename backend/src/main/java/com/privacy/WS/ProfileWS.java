package com.privacy.WS;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.privacy.BLL.ProfileBLL;
import com.privacy.Model.DTO.ClientDTO;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/users")
public class ProfileWS {
	
	@Autowired
	private ProfileBLL profileBLL;
	
	@PostMapping("/create-client")
	@PreAuthorize("hasRole('CLIENT')")
	public void createUser(@RequestBody ClientDTO clientDTO)
	{
		System.out.println(clientDTO.toString());
		ClientDTO client = profileBLL.createClient(clientDTO);
	}
	
	@PostMapping("/hello")
	@PreAuthorize("hasRole('CLIENT')")
	public String returnHello()
	{
		return "Hello WOrld";
	}
	
	@GetMapping("/token")
	@PreAuthorize("hasRole('CLIENT')")
    public Map<String, String> csrf(HttpServletRequest request) {
        CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
        return Map.of(
            "token", token.getToken(),
            "headerName", token.getHeaderName()
        );
    }

}
