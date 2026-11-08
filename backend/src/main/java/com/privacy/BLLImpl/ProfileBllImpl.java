package com.privacy.BLLImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.privacy.BLL.ProfileBLL;
import com.privacy.Dao.ProfileDao;
import com.privacy.Model.DTO.ClientDTO;
import com.privacy.Model.DTO.ConsentDTO;
import com.privacy.Model.DTO.DataAuditLogDTO;
import com.privacy.Model.Entity.Client;

@Service
public class ProfileBllImpl implements ProfileBLL{
	
	@Autowired
	private ProfileDao profileDao;

	@Override
	public ClientDTO createClient(ClientDTO clientDTO) {
		
		if (clientDTO.getEmail() == null || clientDTO.getEmail().isEmpty() ||
			    clientDTO.getFirstName() == null || clientDTO.getFirstName().isEmpty() ||
			    clientDTO.getLastName() == null || clientDTO.getLastName().isEmpty() ||
			    clientDTO.getUsername() == null || clientDTO.getUsername().isEmpty()) {

			    throw new IllegalArgumentException("Enter all fields");
		}
		
		Client client = convertToEntity(clientDTO);
		client = profileDao.save(client);
		return convertToDTO(client);
	}

	private ClientDTO convertToDTO(Client client) {
		return new ClientDTO(client.getFirstName(), client.getLastName(), client.getUsername(), client.getEmail());
	}

	private Client convertToEntity(ClientDTO clientDTO) {
		Client c = new Client();
		c.setFirstName(clientDTO.getFirstName());
		c.setLastName(clientDTO.getLastName());
		c.setEmail(clientDTO.getEmail());
		c.setUsername(clientDTO.getUsername());
		return c;
	}

	@Override
	public ClientDTO getClientById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean updateConsent(List<ConsentDTO> consentList, Integer userID) {
		return profileDao.updateConsent(consentList, userID);
	}

	@Override
	public List<ConsentDTO> viewPreferences(Integer userID) {
		return profileDao.viewPreferences(userID, null);
		
	}

	@Override
	public List<DataAuditLogDTO> viewDataAccess(Integer userID) {
		return profileDao.viewDataAccess(userID, null);
		
	}

	@Override
	public Client getProfile(Integer userID) {
		return profileDao.getProfile(userID, null);
		
	}

	@Override
	public void requestErasure(Integer userID) {
		profileDao.requestErasure(userID);
		
	}
	
}
