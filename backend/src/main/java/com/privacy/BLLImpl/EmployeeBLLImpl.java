package com.privacy.BLLImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.privacy.BLL.EmployeeBLL;
import com.privacy.Dao.EmployeeDao;
import com.privacy.Model.DTO.AnalysisDTO;
import com.privacy.Model.DTO.DataAuditLogDTO;
import com.privacy.Model.DTO.ErasureRequestDTO;
import com.privacy.Model.Entity.Client;

@Service
public class EmployeeBLLImpl implements EmployeeBLL{

	@Autowired
	private EmployeeDao employeeDao;
	
	@Override
	public Map<String, Object> getUserDataForAdmin(Integer userID, Integer employeeID) {
		return employeeDao.getUserDataForAdmin(userID, employeeID);
	}

	@Override
	public List<DataAuditLogDTO> getAuditLogs(Integer id, Integer employeeID) {
		return employeeDao.getAuditLogs(id, employeeID);
	}

	@Override
	public List<ErasureRequestDTO> getErasureRequests() {
		return employeeDao.getErasureRequests();
	}

	@Override
	public List<Map<String, String>> hardDelete(Integer id, Integer employeeID, String notes) {
		return employeeDao.hardDelete(id, employeeID, notes);
	}

	@Override
	public List<Map<String, String>> getMarketingUsers(Integer id, Integer employeeID) {
		if (id!= null)
		{
			return employeeDao.getMarketingUsersById(id, employeeID);
		}
		else {
			return employeeDao.getMarketingUsers(employeeID);
		}
	}

	@Override
	public AnalysisDTO getAnalytics(Integer employeeID) {
		return employeeDao.getAnalytics(employeeID);
	}

	@Override
	public Boolean updateUserProfile(Integer id, Client client, Integer employeeID) {
		return employeeDao.updateUserProfile(id, client, employeeID);
	}

	@Override
	public Client restoreSoftDeletedProfile(Integer id, Integer employeeID) {
		return employeeDao.restoreSoftDeletedProfile(id, employeeID);
	}

	@Override
	public void createTopicInDB(String topicName, String arnTopic, String purpose, Integer employeeID) {
		employeeDao.createTopicInDB(topicName, arnTopic, purpose, employeeID);
	}

	@Override
	public String addTopicMessageInDB(String purpose, String subject, String message, Integer employeeID) {	
		return employeeDao.addTopicMessageInDB(purpose, subject, message, employeeID);
	}

}
