package com.poly.SOF3021.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.SOF3021.model.Account;

public interface AccountRepository extends JpaRepository<Account, String>{
	@Query
	Account getAccountByUsernameAndActivated(String username , Boolean activated);
}
