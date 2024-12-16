package com.gang.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gang.Entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	public User findByEmail(String email);

}
