package com.dcardprocessing.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dcardprocessing.bean.ScanActivity;
import com.dcardprocessing.bean.ScannedFile;
import com.dcardprocessing.generic.GenericService;

@Service
public interface ScanActivityService extends GenericService<ScanActivity>{

	List<ScanActivity> findByIsDeletedFlag();

	List<ScanActivity> findByIsDeletedFlagIP();

	List<ScanActivity> findByIsDeletedFlagMYSQL();

	List<ScanActivity> findByIsDeletedFlagOracle();

	int  findLatestID();

	List<ScanActivity> findByUserIsDeletedFlag();
	
	List<ScanActivity> findByUserIsDeletedFlagIP();
	
	List<ScanActivity>  findByUserScanActivityId(int user_scan_activity_id);
	
	List<ScanActivity> findByStatus();
	
	void updateByUserScanActivityId(int user_scan_activity_id);
	
	public List<ScanActivity> fileScaning(String selectFile1, String filePath,
			String selectFile2, List<ScanActivity> scannedFiles, String status,int userScanActivityId);
	public List<ScanActivity> fileScaningIP(String ipAddress,List<ScanActivity> scannedFiles,int userScanActivityId);
	
	public List<ScanActivity> loadAdminScannedFiles();
	
	public List<ScanActivity> loadAdminScannedFilesIP();
	
	//public Boolean countForFileName(String scanTypeDetail,String scanDetail);
	
	public List<ScanActivity> loadAdminScannedFilesMYSQL();
	
	

}
