package com.privacy.BLL;

import java.util.List;

import com.privacy.Model.DTO.ClientDTO;
import com.privacy.Model.DTO.ConsentDTO;
import com.privacy.Model.DTO.DataAuditLogDTO;
import com.privacy.Model.Entity.Client;

public interface ProfileBLL {
	ClientDTO createClient(ClientDTO clientDTO);
	ClientDTO getClientById(int id);
	boolean updateConsent(List<ConsentDTO> consentList, Integer userID);
	List<ConsentDTO> viewPreferences(Integer userID);
	List<DataAuditLogDTO> viewDataAccess(Integer userID);
	Client getProfile(Integer userID);
	void requestErasure(Integer userID);
}
