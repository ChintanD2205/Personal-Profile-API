package com.privacy.BLL;

import com.privacy.Model.DTO.ClientDTO;

public interface ProfileBLL {
	ClientDTO createClient(ClientDTO clientDTO);
	ClientDTO getClientById(int id);
}
