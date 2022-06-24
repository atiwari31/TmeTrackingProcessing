package com.dcardprocessing.service;

import java.util.List;

import com.dcardprocessing.bean.User;
import com.dcardprocessing.generic.GenericService;

public interface UserService extends GenericService<User> {

	boolean authenticate(String email, String password);
	User authenticateUser(String email, String password);
	User findByEmail(String email);
	List<User> findByCreatedId(int createdId);
	List<User> findByAllAdmin();
}
