package com.privacy.Dao;

import java.util.List;
import java.util.Map;

import com.privacy.Model.DTO.AnalysisDTO;
import com.privacy.Model.DTO.DataAuditLogDTO;
import com.privacy.Model.DTO.ErasureRequestDTO;
import com.privacy.Model.Entity.Client;
import com.privacy.Model.Entity.Employee;

public interface EmployeeDao {

	Map<String, Object> getUserDataForAdmin(Integer userID, Integer employeeID);

	Employee adminLogin(String email, String password);

	List<DataAuditLogDTO> getAuditLogs(Integer id, Integer employeeID);

	List<ErasureRequestDTO> getErasureRequests();

	List<Map<String, String>> hardDelete(Integer id, Integer employeeID, String notes);

	List<Map<String, String>> getMarketingUsers(Integer employeeID);

	List<Map<String, String>> getMarketingUsersById(Integer id, Integer employeeID);

	AnalysisDTO getAnalytics(Integer employeeID);

	Boolean updateUserProfile(Integer id, Client client, Integer employeeID);

	Client restoreSoftDeletedProfile(Integer id, Integer employeeID);

	void createTopicInDB(String topicName, String arnTopic, String purpose, Integer employeeID);

	String addTopicMessageInDB(String purpose, String subject, String message, Integer employeeID);

}
