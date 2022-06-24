package com.dcardprocessing.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcardprocessing.bean.ScanSchedule;
import com.dcardprocessing.repository.ScanScheduleRepository;
import com.dcardprocessing.service.ScanScheduleService;
@Service
public class ScanScheduleServiceImpl implements ScanScheduleService{
	@Autowired
	ScanScheduleRepository scanScheduleRepository;

	@Override
	public ScanSchedule save(ScanSchedule entity) {
		return scanScheduleRepository.save(entity);
	}

	@Override
	public ScanSchedule update(ScanSchedule entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(ScanSchedule entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteInBatch(List<ScanSchedule> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ScanSchedule find(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ScanSchedule> findAll() {
		// TODO Auto-generated method stub
		return scanScheduleRepository.findAll();
	}

	@Override
	public List<ScanSchedule> getScheduleByDate() {
		// TODO Auto-generated method stub
		return scanScheduleRepository.getScanScheduleByDate();
	}

	@Override
	public List<ScanSchedule> getScheduleByUserId(int userID) {
		return scanScheduleRepository.getScheduleByUserId(userID);
	}

}
