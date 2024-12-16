package com.gang.Service;

import com.gang.Entity.User;

public interface UserService {
	public User findUserByJwtToken(String jwt) throws Exception;
	public User findUserByEmail(String email) throws Exception;

}
