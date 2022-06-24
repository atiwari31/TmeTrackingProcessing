package com.dcardprocessing.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dcardprocessing.bean.ScanActivity;
import com.dcardprocessing.bean.UserScanActivity;
import com.dcardprocessing.generic.GenericService;
@Service
public interface IPScanService extends GenericService<UserScanActivity>{

	public List<ScanActivity> ipAddressScan(String IPAddres,String toAddres,String userName,String passwor);
	
	public void remoteConnectionDeleteFile(Long id);
	
}
