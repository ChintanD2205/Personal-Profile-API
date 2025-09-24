package com.privacy.WS;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.privacy.BLL.EmployeeBLL;
import com.privacy.Model.DTO.DataAuditLogDTO;
import com.privacy.Model.DTO.ErasureRequestDTO;
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
	
	@PreAuthorize("hasRole('COMPLIANCE_OFFICER')")
	@GetMapping({"/audit", "/audit/{id}"})
	public ResponseEntity<List<DataAuditLogDTO>> viewAuditLogs(HttpServletRequest request, @PathVariable(required = false) Integer id){
		String authHeader = request.getHeader("Authorization");
		String token = authHeader.substring(7);
		Integer employeeID = Integer.parseInt(jwtUtil.extractUserID(token));
		
		List<DataAuditLogDTO> audits= employeeBLL.getAuditLogs(id, employeeID);
		return ResponseEntity.ok(audits);
	}
	
	@PreAuthorize("hasRole('COMPLIANCE_OFFICER')")
	@GetMapping("/privacy-requests")
	public ResponseEntity<List<ErasureRequestDTO>> pendingErasureRequests(){
		List<ErasureRequestDTO> reqs = employeeBLL.getErasureRequests();
		return ResponseEntity.ok(reqs);
	}
	
	@PreAuthorize("hasRole('COMPLIANCE_OFFICER')")
	@DeleteMapping("/profile/{id}")
	public ResponseEntity<String> hardDelete(HttpServletRequest request, @PathVariable Integer id){
		String authHeader = request.getHeader("Authorization");
		String token = authHeader.substring(7);
		Integer employeeID = Integer.parseInt(jwtUtil.extractUserID(token));
		
		String username = employeeBLL.hardDelete(id, employeeID);
		return ResponseEntity.ok(username);
	}
	
	@PreAuthorize("hasRole('MARKETING_AGENT')")
	@GetMapping({"/profile/marketing", "/profile/marketing/{id}"})
	public ResponseEntity<List<Map<String, String>>> marketingUsers(HttpServletRequest request, @PathVariable(required = false) Integer id){
		String authHeader = request.getHeader("Authorization");
		String token = authHeader.substring(7);
		Integer employeeID = Integer.parseInt(jwtUtil.extractUserID(token));
		
		List<Map<String, String>> res = employeeBLL.getMarketingUsers(id, employeeID);
		return ResponseEntity.ok(res);
	}
	
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
