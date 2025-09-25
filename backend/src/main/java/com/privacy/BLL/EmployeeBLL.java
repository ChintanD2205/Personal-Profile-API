package com.privacy.BLL;

import java.util.List;
import java.util.Map;

import com.privacy.Model.DTO.DataAuditLogDTO;
import com.privacy.Model.DTO.ErasureRequestDTO;

public interface EmployeeBLL {

	Map<String, Object> getUserDataForAdmin(Integer userID, Integer employeeID);

	List<DataAuditLogDTO> getAuditLogs(Integer id, Integer employeeID);

	List<ErasureRequestDTO> getErasureRequests();

	String hardDelete(Integer id, Integer employeeID);

	List<Map<String, String>> getMarketingUsers(Integer id, Integer employeeID);

}
