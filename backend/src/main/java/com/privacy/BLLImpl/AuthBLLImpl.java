package com.privacy.BLLImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.privacy.BLL.AuthBLL;
import com.privacy.Dao.ProfileDao;
import com.privacy.Model.Entity.Client;

@Service
public class AuthBLLImpl implements AuthBLL {

	@Autowired
	private ProfileDao profileDao;
	
	@Override
	public Client login(String email, String password) {
		if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
			    throw new IllegalArgumentException("Enter all fields");
		}
		
		return profileDao.login(email, password);
	}
	
	@Override
	public List<String> getRolesForUser(int userId) {
	    return profileDao.getRolesByUserId(userId);
	}
}
