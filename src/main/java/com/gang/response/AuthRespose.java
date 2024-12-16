package com.gang.response;

import com.gang.Entity.User_Role;

import lombok.Data;

@Data
public class AuthRespose {
	private String jwt;
	private String msg;
	private String password;
	private User_Role role;

}
