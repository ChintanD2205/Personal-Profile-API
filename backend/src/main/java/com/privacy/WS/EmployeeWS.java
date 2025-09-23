package com.privacy.WS;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.privacy.BLL.EmployeeBLL;
import com.privacy.security.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class EmployeeWS {

	@Autowired
	private EmployeeBLL employeeBLL;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@GetMapping("/")
	public String returnHello(HttpServletRequest request)
	{
		String authHeader = request.getHeader("Authorization");
		String token = authHeader.substring(7);
		Integer userID = Integer.parseInt(jwtUtil.extractUserID(token));
		
		return "Hello "+userID;
	}
	
	@PreAuthorize("hasRole('COMPLIANCE_OFFICER') or hasRole('PROFILE_MANAGER')")
	@GetMapping("/access-user-data/{userID}")
	public Map<String, Object> accessUserData(HttpServletRequest request, @PathVariable Integer userID){
		String authHeader = request.getHeader("Authorization");
		String token = authHeader.substring(7);
		Integer employeeID = Integer.parseInt(jwtUtil.extractUserID(token));
		
		return employeeBLL.getUserDataForAdmin(userID, employeeID);
	}
	
//	@PreAuthorize("hasRole('COMPLIANCE_OFFICER')")
//	@GetMapping("/audit")
//	public void viewAuditLogs() {}
//	
//	@PreAuthorize("hasRole('COMPLIANCE_OFFICER')")
//	@GetMapping("/privacy-requests")
//	public void pendingErasureRequests() {}
//	
//	@PreAuthorize("hasRole('COMPLIANCE_OFFICER')")
//	@DeleteMapping("/profile/{id}")
//	public void hardDelete() {}
//	
//	@PreAuthorize("hasRole('MARKETING_AGENT')")
//	@GetMapping("/profile/marketing")
//	public void marketingUsers() {}
//	
//	@PreAuthorize("hasRole('MARKETING_AGENT')")
//	@PostMapping("/campaign/send")
//	public void marketingCampaign() {}
//	
//	@PreAuthorize("hasRole('ANALYTICS_AGENT')")
//	@GetMapping("/analytics")
//	public void analytics() {}
//	
//	@PreAuthorize("hasRole('PROFILE_MANAGER')")
//	@PostMapping("/profile/restore/{id}")
//	public void updateProfile() {}
//	
//	@PreAuthorize("hasRole('PROFILE_MANAGER')")
//	@PutMapping("/profile/{id}")
//	public void restoreSoftDeleted() {}
}
