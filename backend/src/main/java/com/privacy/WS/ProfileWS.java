package com.privacy.WS;

import java.util.List;
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
import com.privacy.Model.DTO.ConsentDTO;
import com.privacy.Model.DTO.DataAuditLogDTO;
import com.privacy.Model.Entity.Client;
import com.privacy.security.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/clients")
@PreAuthorize("hasRole('CLIENT')")
public class ProfileWS {
	
	@Autowired
	private ProfileBLL profileBLL;
	@Autowired
	private JwtUtil jwtUtil;
	
	@PostMapping("/create-client")
	public void createUser(@RequestBody ClientDTO clientDTO)
	{
		ClientDTO client = profileBLL.createClient(clientDTO);
	}
	
	@PostMapping("/update-consent")
	public String updatePreferences(HttpServletRequest request, @RequestBody List<ConsentDTO> consentList)
	{
		String authHeader = request.getHeader("Authorization");
		String token = authHeader.substring(7);
		Integer userID = Integer.parseInt(jwtUtil.extractUserID(token));
		System.out.println("In update Preferences");
		boolean res = profileBLL.updateConsent(consentList, userID);
		if(res)
		{
			return "Updated";
		}
		else {
			return "Cant update right now. Please try later";
		}
		
	}
	
	@GetMapping("/view-preferences")
	public List<ConsentDTO> viewPreferences(HttpServletRequest request)
	{
		String authHeader = request.getHeader("Authorization");
		String token = authHeader.substring(7);
		Integer userID = Integer.parseInt(jwtUtil.extractUserID(token));
		
		return profileBLL.viewPreferences(userID);
	}
	
	@GetMapping("/view-consent-audit-logs")
	public List<DataAuditLogDTO> viewDataAccess(HttpServletRequest request)
	{
		String authHeader = request.getHeader("Authorization");
		String token = authHeader.substring(7);
		Integer userID = Integer.parseInt(jwtUtil.extractUserID(token));
		
		return profileBLL.viewDataAccess(userID);
	}
	
	@GetMapping("/")
	public Client getProfile(HttpServletRequest request)
	{
		String authHeader = request.getHeader("Authorization");
		String token = authHeader.substring(7);
		Integer userID = Integer.parseInt(jwtUtil.extractUserID(token));
		
		return profileBLL.getProfile(userID);
	}
	
	@PostMapping("/request-erasure")
	public void requestErasure(HttpServletRequest request)
	{
		String authHeader = request.getHeader("Authorization");
		String token = authHeader.substring(7);
		Integer userID = Integer.parseInt(jwtUtil.extractUserID(token));
		
		profileBLL.requestErasure(userID);
	}
	
	
	
	@PostMapping("/hello")
	public String returnHello()
	{
		return "Hello WOrld";
	}
	
	@GetMapping("/token")
    public Map<String, String> csrf(HttpServletRequest request) {
        CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
        return Map.of(
            "token", token.getToken(),
            "headerName", token.getHeaderName()
        );
    }

}
