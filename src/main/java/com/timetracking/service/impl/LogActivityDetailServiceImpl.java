package com.dcardprocessing.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcardprocessing.JasyptConfig;
import com.dcardprocessing.bean.LogActivityDetail;
import com.dcardprocessing.repository.LogActivityDetailRepository;
import com.dcardprocessing.service.LogActivityDetailService;

@Service
public class LogActivityDetailServiceImpl implements LogActivityDetailService{

	@Autowired
	private LogActivityDetailRepository logActivityDetailRepository;
	
	@Override
	public LogActivityDetail save(LogActivityDetail entity) {
		return logActivityDetailRepository.save(entity);
	}

	@Override
	public LogActivityDetail update(LogActivityDetail entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(LogActivityDetail entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteInBatch(List<LogActivityDetail> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public LogActivityDetail find(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LogActivityDetail> findAll() {
		List<LogActivityDetail> logActivityDetailList=logActivityDetailRepository.findAll();
		List<LogActivityDetail> logActivityDetailDecrList = new ArrayList<LogActivityDetail>();
		LogActivityDetail logActivityDetailDecr = null;
		for (LogActivityDetail logActivityDetail : logActivityDetailList) {
			logActivityDetailDecr = new LogActivityDetail();
			logActivityDetailDecr.setActivityDate(logActivityDetail.getActivityDate());
			logActivityDetailDecr.setActivityName(JasyptConfig.decryptKey(logActivityDetail.getActivityName()));
			logActivityDetailDecr.setFilePath(JasyptConfig.decryptKey(logActivityDetail.getFilePath()));
			logActivityDetailDecr.setId(logActivityDetail.getId());
			logActivityDetailDecr.setUserName(JasyptConfig.decryptKey(logActivityDetail.getUserName()));
			logActivityDetailDecr.setScanActivityId(logActivityDetail.getScanActivityId());
			logActivityDetailDecr.setUserId(logActivityDetail.getUserId());
			logActivityDetailDecrList.add(logActivityDetailDecr);
		}
		return logActivityDetailDecrList;
	}

}
