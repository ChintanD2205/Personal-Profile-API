package com.privacy.BLL;

import java.util.List;
import java.util.Map;

import com.privacy.Model.DTO.DataAuditLogDTO;

public interface EmployeeBLL {

	Map<String, Object> getUserDataForAdmin(Integer userID, Integer employeeID);

	List<DataAuditLogDTO> getAuditLogs(Integer id, Integer employeeID);

}
