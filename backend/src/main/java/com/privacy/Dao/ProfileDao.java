package com.privacy.Dao;
import java.util.List;

import com.privacy.Model.Entity.Client;

public interface ProfileDao {
	Client save(Client client);
	Client login(String email, String password);
	List<String> getRolesByUserId(int userId);
}
