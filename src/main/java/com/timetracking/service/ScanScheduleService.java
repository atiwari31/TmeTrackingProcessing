package com.dcardprocessing.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dcardprocessing.bean.ScanSchedule;
import com.dcardprocessing.generic.GenericService;

@Service
public interface ScanScheduleService extends GenericService<ScanSchedule>{

	public List<ScanSchedule> getScheduleByDate();
	
	public List<ScanSchedule> getScheduleByUserId(int userID);
}
