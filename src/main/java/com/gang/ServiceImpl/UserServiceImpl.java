package com.gang.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gang.ConfigSequrity.JwtProvider;
import com.gang.Entity.User;
import com.gang.Repo.UserRepository;
import com.gang.Service.UserService;

import jakarta.transaction.Transactional;
@Service
@Transactional
public class UserServiceImpl  implements UserService{
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtProvider jwtProvider;

	@Override
	public User findUserByJwtToken(String jwt) throws Exception {
		String email = jwtProvider.getEmailFromJwtToken(jwt);
		User user = findUserByEmail(email);
		// TODO Auto-generated method stub
		return user;
	}

	@Override
	public User findUserByEmail(String email) throws Exception {
		// 
		User user = userRepository.findByEmail(email);
		if(user==null) {
			throw new Exception("User not found");
		}
		return user;
	}

}
