package com.dcardprocessing.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcardprocessing.bean.License;
import com.dcardprocessing.repository.LicenseRepository;
import com.dcardprocessing.service.LicenseService;

public class LicenseServiceImpl implements LicenseService{
	
	@Autowired
	LicenseRepository licenseRepository;

	@Override
	public License save(License entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public License update(License entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(License entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteInBatch(List<License> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public License find(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<License> findAll() {
		return licenseRepository.findAll();
	}

}
