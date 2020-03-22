package com.dgsystems.nfeservice.cadastro.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dgsystems.nfeservice.security.UserAccount;

@Repository
public interface UserRepository extends CrudRepository<UserAccount, Integer> {
	UserAccount findByUsername(String username);
}
