package com.privacy.Dao;
import java.util.List;

import com.privacy.Model.DTO.ConsentDTO;
import com.privacy.Model.DTO.DataAuditLogDTO;
import com.privacy.Model.Entity.Client;

public interface ProfileDao {
	Client save(Client client);
	Client login(String email, String password);
	List<String> getRolesByUserId(int userId);
	boolean updateConsent(List<ConsentDTO> consentList, Integer userID);
	List<ConsentDTO> viewPreferences(Integer userID);
	List<DataAuditLogDTO> viewDataAccess(Integer userID);
	Client getProfile(Integer userID);
	void requestErasure(Integer userID);
}
