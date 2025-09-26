package com.privacy.BLL;

import java.util.List;
import java.util.Map;

import com.privacy.Model.DTO.AnalysisDTO;
import com.privacy.Model.DTO.DataAuditLogDTO;
import com.privacy.Model.DTO.ErasureRequestDTO;
import com.privacy.Model.Entity.Client;

public interface EmployeeBLL {

	Map<String, Object> getUserDataForAdmin(Integer userID, Integer employeeID);

	List<DataAuditLogDTO> getAuditLogs(Integer id, Integer employeeID);

	List<ErasureRequestDTO> getErasureRequests();

	List<Map<String, String>> hardDelete(Integer id, Integer employeeID, String notes);

	List<Map<String, String>> getMarketingUsers(Integer id, Integer employeeID);

	AnalysisDTO getAnalytics(Integer employeeID);

	Boolean updateUserProfile(Integer id, Client client, Integer employeeID);

	Client restoreSoftDeletedProfile(Integer id, Integer employeeID);

	void createTopicInDB(String string, String arnTopic, String string2, Integer employeeID);

	String addTopicMessageInDB(String string, String string2, String string3, Integer employeeID);

}
