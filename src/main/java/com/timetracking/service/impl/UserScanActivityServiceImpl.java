package com.dcardprocessing.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcardprocessing.bean.UserScanActivity;
import com.dcardprocessing.repository.UserScanActivityRepository;
import com.dcardprocessing.service.UserScanActivityService;

@Service
public class UserScanActivityServiceImpl implements UserScanActivityService {

	@Autowired
	private UserScanActivityRepository userScanActivityDetailRepository;

	@Override
	public UserScanActivity save(UserScanActivity entity) {
		return userScanActivityDetailRepository.save(entity);
	}

	@Override
	public UserScanActivity update(UserScanActivity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(UserScanActivity entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteInBatch(List<UserScanActivity> entities) {
		// TODO Auto-generated method stub

	}

	@Override
	public UserScanActivity find(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserScanActivity> findAll() {
		return userScanActivityDetailRepository.findAll();
	}

	@Override
	public int findMaxID() {
		return userScanActivityDetailRepository.findMaxID();
	}

}
