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
	List<ConsentDTO> viewPreferences(Integer userID, Integer internalUID);
	List<DataAuditLogDTO> viewDataAccess(Integer userID, Integer internalUID);
	Client getProfile(Integer userID, Integer internalUID);
	void requestErasure(Integer userID);
}
