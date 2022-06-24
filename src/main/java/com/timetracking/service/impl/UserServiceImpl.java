package com.dcardprocessing.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcardprocessing.JasyptConfig;
import com.dcardprocessing.bean.User;
import com.dcardprocessing.repository.UserRepository;
import com.dcardprocessing.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User save(User entity) {
		return userRepository.save(entity);
	}

	@Override
	public User update(User entity) {
		return userRepository.save(entity);
	}

	@Override
	public void delete(User entity) {
		userRepository.delete(entity);
	}

	@Override
	public void delete(Long id) {
		userRepository.delete(id);
	}

	@Override
	public User find(Long id) {
		return userRepository.findOne(id);
	}

	@Override
	public List<User> findAll() {
		List<User> userListDecr = new ArrayList<>();
		User userDecr = null;
		List<User> userListLocal = userRepository.findAll();
		if (userListLocal.size() > 0) {
			for (User user : userListLocal) {
				userDecr = new User();
				if (user.getRole().equalsIgnoreCase("Admin")) {

					userDecr.setFirstName(user.getFirstName());
					userDecr.setLastName(user.getLastName());
					userDecr.setPassword(user.getPassword());
					userDecr.setRole(user.getRole());
					userDecr.setGender(user.getGender());
					userDecr.setId(user.getId());
					userDecr.setEmail(user.getEmail());
					userListDecr.add(userDecr);
				} else {

					userDecr.setFirstName(JasyptConfig.decryptKey(user.getFirstName()));
					userDecr.setLastName(JasyptConfig.decryptKey(user.getLastName()));
					userDecr.setPassword(JasyptConfig.decryptKey(user.getPassword()));
					userDecr.setRole(user.getRole());
					userDecr.setGender(user.getGender());
					userDecr.setId(user.getId());
					userDecr.setEmail(user.getEmail());
					userListDecr.add(userDecr);
				}
			}
		
	}
		return userListDecr;
	}

	@Override
	public boolean authenticate(String username, String password){
		User user = this.findByEmail(username);
		if(user == null){
			return false;
		}else{
			if(password.equals(user.getPassword())) return true;
			else return false;
		}
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void deleteInBatch(List<User> users) {
		userRepository.deleteInBatch(users);
	}

	@Override
	public User authenticateUser(String username, String password) {
		User user = this.findByEmail(username);
		return user;
	}

	@Override
	public List<User> findByCreatedId(int createdId) {
		return userRepository.findByCreatedId(createdId);
	}

	@Override
	public List<User> findByAllAdmin() {
		// TODO Auto-generated method stub
		return userRepository.findByAllAdmin();
	}
	
}
