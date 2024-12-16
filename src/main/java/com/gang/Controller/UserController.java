package com.gang.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gang.Entity.User;
import com.gang.Service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	private UserService userService;
	@GetMapping("/profile")
	
	public ResponseEntity<User> findUserByToken(@RequestHeader("Authorization") String jwt) throws Exception{
		User byJwtToken = userService.findUserByJwtToken(jwt);
		return new ResponseEntity<User>(byJwtToken, HttpStatus.OK);
		
	}

}
