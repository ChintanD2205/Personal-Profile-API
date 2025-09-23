package com.privacy.Dao;

import java.util.List;
import java.util.Map;

import com.privacy.Model.DTO.DataAuditLogDTO;
import com.privacy.Model.DTO.ErasureRequestDTO;
import com.privacy.Model.Entity.Employee;

public interface EmployeeDao {

	Map<String, Object> getUserDataForAdmin(Integer userID, Integer employeeID);

	Employee adminLogin(String email, String password);

	List<DataAuditLogDTO> getAuditLogs(Integer id, Integer employeeID);

	List<ErasureRequestDTO> getErasureRequests();

}
