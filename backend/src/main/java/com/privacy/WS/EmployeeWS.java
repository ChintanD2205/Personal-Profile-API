package com.privacy.WS;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.privacy.BLL.EmployeeBLL;
import com.privacy.Model.DTO.AnalysisDTO;
import com.privacy.Model.DTO.DataAuditLogDTO;
import com.privacy.Model.DTO.ErasureRequestDTO;
import com.privacy.Model.Entity.Client;
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
	public ResponseEntity<List<Map<String, String>>> hardDelete(HttpServletRequest request, @PathVariable Integer id, @RequestBody String notes){
		String authHeader = request.getHeader("Authorization");
		String token = authHeader.substring(7);
		Integer employeeID = Integer.parseInt(jwtUtil.extractUserID(token));
		
		List<Map<String, String>> res = employeeBLL.hardDelete(id, employeeID, notes);
		return ResponseEntity.ok(res);
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
	
	@PreAuthorize("hasRole('MARKETING_AGENT')")
	@PostMapping("/campaign/send")
	public ResponseEntity<List<Map<String, String>>> marketingCampaign(HttpServletRequest request){
		String authHeader = request.getHeader("Authorization");
		String token = authHeader.substring(7);
		Integer employeeID = Integer.parseInt(jwtUtil.extractUserID(token));
		
		List<Map<String, String>> res = employeeBLL.getMarketingUsers(null, employeeID);
		//Implement Method to send email using Amazon SNS
		return ResponseEntity.ok(res);
	}
	
	@PreAuthorize("hasRole('ANALYTICS_AGENT')")
	@GetMapping("/analytics")
	public ResponseEntity<AnalysisDTO> analytics(HttpServletRequest request){
		String authHeader = request.getHeader("Authorization");
		String token = authHeader.substring(7);
		Integer employeeID = Integer.parseInt(jwtUtil.extractUserID(token));
		
		AnalysisDTO res = employeeBLL.getAnalytics(employeeID);
		return ResponseEntity.ok(res);
	}
	
	@PreAuthorize("hasRole('PROFILE_MANAGER')")
	@PostMapping("/profile/restore/{id}")
	public ResponseEntity<Client> restoreSoftDeletedProfile(HttpServletRequest request, @PathVariable Integer id){
		String authHeader = request.getHeader("Authorization");
		String token = authHeader.substring(7);
		Integer employeeID = Integer.parseInt(jwtUtil.extractUserID(token));
		
		Client client = employeeBLL.restoreSoftDeletedProfile(id, employeeID);
		return ResponseEntity.ok(client);
	}
	
	@PreAuthorize("hasRole('PROFILE_MANAGER')")
	@PutMapping("/profile/{id}")
	public String updateProfile(HttpServletRequest request, @PathVariable Integer id, @RequestBody Client client){
		String authHeader = request.getHeader("Authorization");
		String token = authHeader.substring(7);
		Integer employeeID = Integer.parseInt(jwtUtil.extractUserID(token));
		
		Boolean res = employeeBLL.updateUserProfile(id, client, employeeID);
		if(res) {
			return "Updated";
		}
		else {
			return "Server Issue";
		}
		
	}
}
