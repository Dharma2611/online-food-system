package com.gang.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gang.Entity.Address;

public interface AddressRpository extends JpaRepository<Address, Long>{

}
