package com.dcardprocessing.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcardprocessing.JasyptConfig;
import com.dcardprocessing.bean.ScanActivity;
import com.dcardprocessing.repository.ScanActivityRepository;
import com.dcardprocessing.service.ScanActivityService;
import com.dcardprocessing.util.ThreadExecutor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

@Service
public class ScanActivityServiceImpl implements ScanActivityService{

	@Autowired
	private ScanActivityRepository scanActivityDetailRepository;
	
	@Autowired
	private ThreadExecutor threadExecutor;
	
	@Autowired
	private IpScanServiceImpl IPScanService;
	
	private CheckBox select;
	
	public static double ii = 0;
	
	@Override
	public ScanActivity save(ScanActivity entity) {
		return scanActivityDetailRepository.save(entity);
	}

	@Override
	public ScanActivity update(ScanActivity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(ScanActivity entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteInBatch(List<ScanActivity> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ScanActivity find(Long id) {
		// TODO Auto-generated method stub
		return scanActivityDetailRepository.findOne(id);
	}

	@Override
	public List<ScanActivity> findAll() {
		return scanActivityDetailRepository.findAll();
	}
	
	@Override
	public int findLatestID() {
		return scanActivityDetailRepository.findLatestID();
	}
	
	@Override
	public void updateByUserScanActivityId(int user_scan_activity_id) {
		return;
	}
	
	ObservableList<String> options = 
		    FXCollections.observableArrayList(
		        "True Positive",
		        "False Positive"
		        );
	
	@Override
	public List<ScanActivity> findByUserIsDeletedFlag() {
		List<ScanActivity> scanActivityList=scanActivityDetailRepository.findByUserIsDeletedFlag();
		ScanActivity scanActivityLocal=null;
		List<ScanActivity> scanActivityListLocal = new ArrayList<>();
		TextField text = null;
		
		for(ScanActivity scanActivity:scanActivityList) {
			text = new TextField();
			
			scanActivityLocal = new ScanActivity();
			scanActivityLocal.setId(scanActivity.getId());
			scanActivityLocal.setScanTypeId(scanActivity.getScanTypeId());
		
				scanActivityLocal.setScanTypeDetail(JasyptConfig.decryptKey(scanActivity.getScanTypeDetail()));
				scanActivityLocal.setScanDetail(JasyptConfig.decryptKey(scanActivity.getScanDetail()));
				scanActivityLocal.setScanPath(JasyptConfig.decryptKey(scanActivity.getScanPath()));
				if(null!=scanActivity.getRemarks() && !scanActivity.getRemarks().isEmpty()) {
					text.setText(JasyptConfig.decryptKey(scanActivity.getRemarks()));
					scanActivityLocal.setUserremarks(text);
				}else
				{
					text.setText(null);
					scanActivityLocal.setUserremarks(text);
				}
			if(null!=scanActivity.getType()) {
				if("True Positive".equalsIgnoreCase(scanActivity.getType())) {
					   ComboBox comboBox = new ComboBox(options);
					   comboBox.setValue(scanActivity.getType());
					   scanActivityLocal.setComboBox(comboBox);
					}
					if("False Positive".equalsIgnoreCase(scanActivity.getType())) {
						   ComboBox comboBox = new ComboBox(options);
						   comboBox.setValue(scanActivity.getType());
						   scanActivityLocal.setComboBox(comboBox);
						}
			}else {
				ComboBox comboBox = new ComboBox(options);
				scanActivityLocal.setComboBox(comboBox);
			}
			
			
			/*** status is not scan then no one can modified remarks and select box **/
			if(null!=scanActivity.getCurrentStatus() && scanActivity.getCurrentStatus().equalsIgnoreCase("REQUEST FOR APPROVAL")) {
				select= new CheckBox();
				text.setEditable(false);
				scanActivityLocal.setUserremarks(text);
				select.setDisable(true);
				scanActivityLocal.setSelect(select);
			}
			scanActivityLocal.setTimestamp(scanActivity.getTimestamp());
			scanActivityLocal.setIsDeleted(scanActivity.getIsDeleted());
			scanActivityLocal.setStatus(scanActivity.getCurrentStatus());
			scanActivityLocal.setCurrentStatus(scanActivity.getCurrentStatus());
			scanActivityLocal.setUserScanActivityId(scanActivity.getUserScanActivityId());
			scanActivityListLocal.add(scanActivityLocal);
		}
		return scanActivityListLocal;
	}
	@Override
	public List<ScanActivity> findByUserIsDeletedFlagIP() {
		List<ScanActivity> scanActivityList=scanActivityDetailRepository.findByUserIsDeletedFlagIP();
		ScanActivity scanActivityLocal=null;
		List<ScanActivity> scanActivityListLocal = new ArrayList<>();
		TextField text = null;
		
		for(ScanActivity scanActivity:scanActivityList) {
			text = new TextField();
			
			scanActivityLocal = new ScanActivity();
			scanActivityLocal.setId(scanActivity.getId());
			scanActivityLocal.setScanTypeId(scanActivity.getScanTypeId());
		
				scanActivityLocal.setScanTypeDetail(JasyptConfig.decryptKey(scanActivity.getScanTypeDetail()));
				scanActivityLocal.setScanDetail(JasyptConfig.decryptKey(scanActivity.getScanDetail()));
				scanActivityLocal.setScanPath(JasyptConfig.decryptKey(scanActivity.getScanPath()));
				if(null!=scanActivity.getRemarks() && !scanActivity.getRemarks().isEmpty()) {
					text.setText(JasyptConfig.decryptKey(scanActivity.getRemarks()));
					scanActivityLocal.setUserremarks(text);
				}else
				{
					text.setText(null);
					scanActivityLocal.setUserremarks(text);
				}
			if(null!=scanActivity.getType()) {
				if("True Positive".equalsIgnoreCase(scanActivity.getType())) {
					   ComboBox comboBox = new ComboBox(options);
					   comboBox.setValue(scanActivity.getType());
					   scanActivityLocal.setComboBox(comboBox);
					}
					if("False Positive".equalsIgnoreCase(scanActivity.getType())) {
						   ComboBox comboBox = new ComboBox(options);
						   comboBox.setValue(scanActivity.getType());
						   scanActivityLocal.setComboBox(comboBox);
						}
			}else {
				ComboBox comboBox = new ComboBox(options);
				scanActivityLocal.setComboBox(comboBox);
			}
			
			
			/*** status is not scan then no one can modified remarks and select box **/
			if(null!=scanActivity.getCurrentStatus() && scanActivity.getCurrentStatus().equalsIgnoreCase("REQUEST FOR APPROVAL")) {
				select= new CheckBox();
				text.setEditable(false);
				scanActivityLocal.setUserremarks(text);
				select.setDisable(true);
				scanActivityLocal.setSelect(select);
			}
			scanActivityLocal.setTimestamp(scanActivity.getTimestamp());
			scanActivityLocal.setIsDeleted(scanActivity.getIsDeleted());
			scanActivityLocal.setStatus(scanActivity.getCurrentStatus());
			scanActivityLocal.setCurrentStatus(scanActivity.getCurrentStatus());
			scanActivityLocal.setUserScanActivityId(scanActivity.getUserScanActivityId());
			scanActivityListLocal.add(scanActivityLocal);
		}
		return scanActivityListLocal;
	}
	
	
	@Override
	public List<ScanActivity> findByIsDeletedFlag() {
		return scanActivityDetailRepository.findByIsDeletedFlag();
	}
	

	@Override
	public List<ScanActivity> findByIsDeletedFlagIP() {
		List<ScanActivity> scanActivityList=scanActivityDetailRepository.findByIsDeletedFlagIP();
		ScanActivity scanActivityLocal=null;
		List<ScanActivity> scanActivityListLocal = new ArrayList<>();
		TextField text = null;
		
		for(ScanActivity scanActivity:scanActivityList) {
			text = new TextField();
			
			scanActivityLocal = new ScanActivity();
			scanActivityLocal.setId(scanActivity.getId());
			scanActivityLocal.setScanTypeId(scanActivity.getScanTypeId());
		
				scanActivityLocal.setScanTypeDetail(JasyptConfig.decryptKey(scanActivity.getScanTypeDetail()));
				scanActivityLocal.setScanDetail(JasyptConfig.decryptKey(scanActivity.getScanDetail()));
				scanActivityLocal.setScanPath(JasyptConfig.decryptKey(scanActivity.getScanPath()));
				if(null!=scanActivity.getRemarks() && !scanActivity.getRemarks().isEmpty()) {
					text.setText(JasyptConfig.decryptKey(scanActivity.getRemarks()));
					scanActivityLocal.setUserremarks(text);
				}else
				{
					text.setText(null);
					scanActivityLocal.setUserremarks(text);
				}
			if(null!=scanActivity.getType()) {
				if("True Positive".equalsIgnoreCase(scanActivity.getType())) {
					   ComboBox comboBox = new ComboBox(options);
					   comboBox.setValue(scanActivity.getType());
					   scanActivityLocal.setComboBox(comboBox);
					}
					if("False Positive".equalsIgnoreCase(scanActivity.getType())) {
						   ComboBox comboBox = new ComboBox(options);
						   comboBox.setValue(scanActivity.getType());
						   scanActivityLocal.setComboBox(comboBox);
						}
			}else {
				ComboBox comboBox = new ComboBox(options);
				scanActivityLocal.setComboBox(comboBox);
			}
			
			
			/*** status is not scan then no one can modified remarks and select box **/
			if(null!=scanActivity.getCurrentStatus() && scanActivity.getCurrentStatus().equalsIgnoreCase("REQUEST FOR APPROVAL")) {
				select= new CheckBox();
				text.setEditable(false);
				scanActivityLocal.setUserremarks(text);
				select.setDisable(true);
				scanActivityLocal.setSelect(select);
			}
			scanActivityLocal.setRemarks(JasyptConfig.decryptKey(scanActivity.getRemarks()));
			scanActivityLocal.setTimestamp(scanActivity.getTimestamp());
			scanActivityLocal.setIsDeleted(scanActivity.getIsDeleted());
			scanActivityLocal.setStatus(scanActivity.getCurrentStatus());
			scanActivityLocal.setCurrentStatus(scanActivity.getCurrentStatus());
			scanActivityLocal.setUserScanActivityId(scanActivity.getUserScanActivityId());
			scanActivityListLocal.add(scanActivityLocal);
		}
		return scanActivityListLocal;
	}
	
	
	@Override
	public List<ScanActivity> findByIsDeletedFlagMYSQL() {
		System.out.println();
		List<ScanActivity> scanActivityList=scanActivityDetailRepository.findByIsDeletedFlagMYSQL();
		ScanActivity scanActivityLocal=null;
		List<ScanActivity> scanActivityListLocal = new ArrayList<>();
		TextField text = null;
		
		for(ScanActivity scanActivity:scanActivityList) {
			text = new TextField();
			
			scanActivityLocal = new ScanActivity();
			scanActivityLocal.setId(scanActivity.getId());
			scanActivityLocal.setScanTypeId(scanActivity.getScanTypeId());
		
				scanActivityLocal.setScanTypeDetail(JasyptConfig.decryptKey(scanActivity.getScanTypeDetail()));
				scanActivityLocal.setScanDetail(JasyptConfig.decryptKey(scanActivity.getScanDetail()));
				scanActivityLocal.setScanPath(JasyptConfig.decryptKey(scanActivity.getScanPath()));
				if(null!=scanActivity.getRemarks() && !scanActivity.getRemarks().isEmpty()) {
					text.setText(JasyptConfig.decryptKey(scanActivity.getRemarks()));
					scanActivityLocal.setUserremarks(text);
				}else
				{
					text.setText(null);
					scanActivityLocal.setUserremarks(text);
				}
			if(null!=scanActivity.getType()) {
				if("True Positive".equalsIgnoreCase(scanActivity.getType())) {
					   ComboBox comboBox = new ComboBox(options);
					   comboBox.setValue(scanActivity.getType());
					   scanActivityLocal.setComboBox(comboBox);
					}
					if("False Positive".equalsIgnoreCase(scanActivity.getType())) {
						   ComboBox comboBox = new ComboBox(options);
						   comboBox.setValue(scanActivity.getType());
						   scanActivityLocal.setComboBox(comboBox);
						}
			}else {
				ComboBox comboBox = new ComboBox(options);
				scanActivityLocal.setComboBox(comboBox);
			}
			
			
			/*** status is not scan then no one can modified remarks and select box **/
			if(null!=scanActivity.getCurrentStatus() && scanActivity.getCurrentStatus().equalsIgnoreCase("REQUEST FOR APPROVAL")) {
				select= new CheckBox();
				text.setEditable(false);
				scanActivityLocal.setUserremarks(text);
				select.setDisable(true);
				scanActivityLocal.setSelect(select);
			}
			if(null!=scanActivity.getRemarks() && !scanActivity.getRemarks().isEmpty()) {
				scanActivityLocal.setRemarks(JasyptConfig.decryptKey(scanActivity.getRemarks()));
			}else {
				scanActivityLocal.setRemarks(null);
			}
			scanActivityLocal.setTimestamp(scanActivity.getTimestamp());
			scanActivityLocal.setIsDeleted(scanActivity.getIsDeleted());
			scanActivityLocal.setStatus(scanActivity.getCurrentStatus());
			scanActivityLocal.setCurrentStatus(scanActivity.getCurrentStatus());
			scanActivityLocal.setUserScanActivityId(scanActivity.getUserScanActivityId());
			scanActivityListLocal.add(scanActivityLocal);
		}
		return scanActivityListLocal;
	}
	
	@Override
	public List<ScanActivity> findByStatus() {
		return scanActivityDetailRepository.findByStatus();
	}
	
	
	@Override
	public List<ScanActivity> findByIsDeletedFlagOracle() {
		return scanActivityDetailRepository.findByIsDeletedFlagOracle();
	}
	
	@Override
	public List<ScanActivity>  findByUserScanActivityId(int user_scan_activity_id) {
		List<ScanActivity> list=scanActivityDetailRepository.findByUserScanActivityId(user_scan_activity_id);
		ScanActivity scanActivityLocal=null;
		List<ScanActivity> listLocal= new ArrayList<ScanActivity>();
		for(ScanActivity scanActivity:list) {
			scanActivityLocal= new ScanActivity();
			scanActivityLocal.setRemarks(JasyptConfig.decryptKey(scanActivity.getRemarks()));
			scanActivityLocal.setAdminRemarks(JasyptConfig.decryptKey(scanActivity.getAdminRemarks()));
			scanActivityLocal.setScanDetail(JasyptConfig.decryptKey(scanActivity.getScanDetail()));
			scanActivityLocal.setScanTypeDetail(JasyptConfig.decryptKey(scanActivity.getScanTypeDetail()));
			scanActivityLocal.setScanPath(JasyptConfig.decryptKey(scanActivity.getScanPath()));
			scanActivityLocal.setStatus(scanActivity.getStatus());
			scanActivityLocal.setType(scanActivity.getType());
			scanActivityLocal.setTimestamp(scanActivity.getTimestamp());
			listLocal.add(scanActivityLocal);
		}
		return listLocal;
	}
	
	//@Scheduled(cron="0 0 0 * * ?")
//	@yearly (or @annually)	once a year (0 0 0 1 1 *)
//	@monthly	once a month (0 0 0 1 * *)
//	@weekly	once a week (0 0 0 * * 0)
//	@daily (or @midnight)	once a day (0 0 0 * * *)
//	@hourly	once an hour, (0 0 * * * *)
	
	@Override
	public List<ScanActivity> fileScaning(String selectFile1, String filePath, String selectFile2,
			List<ScanActivity> scannedFiles, String status,int userScanActivityId) {
		
		List<ScanActivity> scanList=findByUserIsDeletedFlag();
		
		ComboBox comboBox = new ComboBox(options);
		
		String glob = "glob:**/*.{docx,txt,xls,pptx}";
		Future<List<ScanActivity>> scannedFilesList = threadExecutor.thredExe(glob, selectFile1, filePath,
				selectFile2, scannedFiles, "Done");
		
		List<ScanActivity> listLocal= new ArrayList<ScanActivity>();
		try {
			List<ScanActivity> list=scannedFilesList.get();
			ScanActivity scanActivityLocal=null;
			 int count=0;
			for(ScanActivity scanActivity:list) {
				ii += 0.1;
				scanActivityLocal= new ScanActivity();
				scanActivityLocal.setRemarks(JasyptConfig.encryptKey(scanActivity.getRemarks()));
				scanActivityLocal.setScanDetail(JasyptConfig.encryptKey(scanActivity.getScanDetail()));
				scanActivityLocal.setScanTypeDetail(JasyptConfig.encryptKey(scanActivity.getScanTypeDetail()));
				scanActivityLocal.setType(null);
				scanActivityLocal.setScanPath(JasyptConfig.encryptKey(scanActivity.getScanPath()));
				scanActivityLocal.setComboBox(comboBox);
				scanActivityLocal.setIsDeleted("false");
				scanActivityLocal.setScanTypeId(2);
				scanActivityLocal.setStatus("SCAN");
				scanActivityLocal.setUserScanActivityId(userScanActivityId);
				scanActivityLocal.setTimestamp(new Date());
				scanActivityLocal.setCurrentStatus("SCAN");
				if(countForFileName(scanActivity.getScanPath())) {
					save(scanActivityLocal);
					listLocal.add(scanActivityLocal);
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listLocal;
	}
	
	@Override
	public List<ScanActivity> fileScaningIP(String ipAddress, List<ScanActivity> scannedFiles,int userScanActivityId) {
		
		List<ScanActivity> scanList=findByUserIsDeletedFlag();
		ScanActivity scanActivityLocal=null;
		int count=0;
		
		List<ScanActivity> listLocal= new ArrayList<ScanActivity>();
		try {
				
			for(ScanActivity scanActivity:scannedFiles) {
				
				scanActivityLocal= new ScanActivity();
				scanActivityLocal.setRemarks(JasyptConfig.encryptKey(scanActivity.getRemarks()));
				scanActivityLocal.setScanDetail(JasyptConfig.encryptKey(scanActivity.getScanDetail()));
				scanActivityLocal.setScanTypeDetail(JasyptConfig.encryptKey(scanActivity.getScanTypeDetail()));
				scanActivityLocal.setDbUserName(JasyptConfig.encryptKey(scanActivity.getDbUserName()));
				scanActivityLocal.setDbPassword(JasyptConfig.encryptKey(scanActivity.getDbPassword()));
				scanActivityLocal.setIpAddress(JasyptConfig.encryptKey(scanActivity.getIpAddress()));
				scanActivityLocal.setType(null);
				scanActivityLocal.setIsDeleted("false");
				scanActivityLocal.setScanTypeId(1);
				scanActivityLocal.setStatus("SCAN");
				scanActivityLocal.setUserScanActivityId(userScanActivityId);
				scanActivityLocal.setTimestamp(new Date());
				scanActivityLocal.setCurrentStatus("SCAN");
				if(checkForFileNameIP(scanActivity.getIpAddress(),scanActivity.getScanDetail())) {
					save(scanActivityLocal);
					listLocal.add(scanActivityLocal);
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listLocal;
	}
	
	
	

	@Override
	public List<ScanActivity> loadAdminScannedFiles() {
		List<ScanActivity> scanActivityList = scanActivityDetailRepository.findByIsDeletedFlag();
		List<ScanActivity> scanActivityListDecr = new ArrayList<>();
		ScanActivity scanActivityDecr = null;
		TextField text = null;
		for (ScanActivity scanActivity : scanActivityList) {
			text = new TextField();
			scanActivityDecr = new ScanActivity();
			scanActivityDecr.setDbPassword(scanActivity.getDbPassword());
			scanActivityDecr.setDbUserName(scanActivity.getDbUserName());
			scanActivityDecr.setId(scanActivity.getId());
			scanActivityDecr.setIpAddress(scanActivity.getIpAddress());
			scanActivityDecr.setIsDeleted(scanActivity.getIsDeleted());
			
			if(null!=scanActivity.getAdminRemarks()) {
				text.setText(JasyptConfig.decryptKey(scanActivity.getAdminRemarks()));
				scanActivityDecr.setAdminremarks(text);
			}
			else {
				text.setText(null);
				scanActivityDecr.setAdminremarks(text);
			}
			if(scanActivity.getStatus().equalsIgnoreCase("Deleted")) {
				
				scanActivityDecr.setRemarks("##########");
				scanActivityDecr.setScanDetail("##########");
				scanActivityDecr.setScanTypeDetail("##########");
				select= new CheckBox();
				text.setEditable(false);
				scanActivityDecr.setUserremarks(text);
				select.setDisable(true);
				scanActivityDecr.setSelect(select);
				scanActivityDecr.setComboBox(null);
			}else {
				
				if(null!=scanActivity.getType()) {
					
					if("True Positive".equalsIgnoreCase(scanActivity.getType())) {
					   ComboBox comboBox = new ComboBox(options);
					   comboBox.setValue(scanActivity.getType());
					   scanActivityDecr.setComboBox(comboBox);
					}
					if("False Positive".equalsIgnoreCase(scanActivity.getType())) {
							ComboBox comboBox = new ComboBox(options);
						    comboBox.setValue(scanActivity.getType());
						    scanActivityDecr.setComboBox(comboBox);
						}
				}else {
					ComboBox comboBox = new ComboBox(options);
					scanActivityDecr.setComboBox(comboBox);
				}
				scanActivityDecr.setRemarks(JasyptConfig.decryptKey(scanActivity.getRemarks()));
				scanActivityDecr.setScanDetail(JasyptConfig.decryptKey(scanActivity.getScanDetail()));
				scanActivityDecr.setScanTypeDetail(JasyptConfig.decryptKey(scanActivity.getScanTypeDetail()));
				scanActivityDecr.setScanPath(JasyptConfig.decryptKey(scanActivity.getScanPath()));
			}
			
			scanActivityDecr.setScanTypeId(scanActivity.getScanTypeId());
			scanActivityDecr.setType(scanActivity.getType());
			scanActivityDecr.setUserScanActivityId(scanActivity.getUserScanActivityId());
			scanActivityListDecr.add(scanActivityDecr);
		}
		return scanActivityListDecr;
	}
	@Override
	public List<ScanActivity> loadAdminScannedFilesIP() {
		List<ScanActivity> scanActivityList = scanActivityDetailRepository.findByIsDeletedFlagIP();
		List<ScanActivity> scanActivityListDecr = new ArrayList<>();
		ScanActivity scanActivityDecr = null;
		TextField text = null;
		for (ScanActivity scanActivity : scanActivityList) {
			text = new TextField();
			scanActivityDecr = new ScanActivity();
			scanActivityDecr.setDbPassword(scanActivity.getDbPassword());
			scanActivityDecr.setDbUserName(scanActivity.getDbUserName());
			scanActivityDecr.setId(scanActivity.getId());
			scanActivityDecr.setIpAddress(scanActivity.getIpAddress());
			scanActivityDecr.setIsDeleted(scanActivity.getIsDeleted());
			
			if(null!=scanActivity.getAdminRemarks()) {
				text.setText(JasyptConfig.decryptKey(scanActivity.getAdminRemarks()));
				scanActivityDecr.setAdminremarks(text);
			}
			else {
				text.setText(null);
				scanActivityDecr.setAdminremarks(text);
			}
			if(scanActivity.getStatus().equalsIgnoreCase("Deleted")) {
				
				scanActivityDecr.setRemarks("##########");
				scanActivityDecr.setScanDetail("##########");
				scanActivityDecr.setScanTypeDetail("##########");
				select= new CheckBox();
				text.setEditable(false);
				scanActivityDecr.setUserremarks(text);
				select.setDisable(true);
				scanActivityDecr.setSelect(select);
				scanActivityDecr.setComboBox(null);
			}else {
				
				if(null!=scanActivity.getType()) {
					
					if("True Positive".equalsIgnoreCase(scanActivity.getType())) {
					   ComboBox comboBox = new ComboBox(options);
					   comboBox.setValue(scanActivity.getType());
					   scanActivityDecr.setComboBox(comboBox);
					}
					if("False Positive".equalsIgnoreCase(scanActivity.getType())) {
							ComboBox comboBox = new ComboBox(options);
						    comboBox.setValue(scanActivity.getType());
						    scanActivityDecr.setComboBox(comboBox);
						}
				}else {
					ComboBox comboBox = new ComboBox(options);
					scanActivityDecr.setComboBox(comboBox);
				}
				scanActivityDecr.setRemarks(JasyptConfig.decryptKey(scanActivity.getRemarks()));
				scanActivityDecr.setScanDetail(JasyptConfig.decryptKey(scanActivity.getScanDetail()));
				scanActivityDecr.setScanTypeDetail(JasyptConfig.decryptKey(scanActivity.getScanTypeDetail()));
				scanActivityDecr.setScanPath(JasyptConfig.decryptKey(scanActivity.getScanPath()));
			}
			
			scanActivityDecr.setScanTypeId(scanActivity.getScanTypeId());
			scanActivityDecr.setType(scanActivity.getType());
			scanActivityDecr.setUserScanActivityId(scanActivity.getUserScanActivityId());
			scanActivityListDecr.add(scanActivityDecr);
		}
		return scanActivityListDecr;
	}
	@Override
	public List<ScanActivity> loadAdminScannedFilesMYSQL() {
		List<ScanActivity> scanActivityList = scanActivityDetailRepository.findByIsDeletedFlagMYSQLADMIN();
		List<ScanActivity> scanActivityListDecr = new ArrayList<>();
		ScanActivity scanActivityDecr = null;
		TextField text = null;
		for (ScanActivity scanActivity : scanActivityList) {
			text = new TextField();
			scanActivityDecr = new ScanActivity();
			scanActivityDecr.setDbPassword(scanActivity.getDbPassword());
			scanActivityDecr.setDbUserName(scanActivity.getDbUserName());
			scanActivityDecr.setId(scanActivity.getId());
			scanActivityDecr.setIpAddress(scanActivity.getIpAddress());
			scanActivityDecr.setIsDeleted(scanActivity.getIsDeleted());
			
			if(null!=scanActivity.getAdminRemarks()) {
				text.setText(JasyptConfig.decryptKey(scanActivity.getAdminRemarks()));
				scanActivityDecr.setAdminremarks(text);
			}
			else {
				text.setText(null);
				scanActivityDecr.setAdminremarks(text);
			}
			if(scanActivity.getStatus().equalsIgnoreCase("Deleted")) {
				
				scanActivityDecr.setRemarks("##########");
				scanActivityDecr.setScanDetail("##########");
				scanActivityDecr.setScanTypeDetail("##########");
				select= new CheckBox();
				text.setEditable(false);
				scanActivityDecr.setUserremarks(text);
				select.setDisable(true);
				scanActivityDecr.setSelect(select);
				scanActivityDecr.setComboBox(null);
			}else {
				
				if(null!=scanActivity.getType()) {
					
					if("True Positive".equalsIgnoreCase(scanActivity.getType())) {
					   ComboBox comboBox = new ComboBox(options);
					   comboBox.setValue(scanActivity.getType());
					   scanActivityDecr.setComboBox(comboBox);
					}
					if("False Positive".equalsIgnoreCase(scanActivity.getType())) {
							ComboBox comboBox = new ComboBox(options);
						    comboBox.setValue(scanActivity.getType());
						    scanActivityDecr.setComboBox(comboBox);
						}
				}else {
					ComboBox comboBox = new ComboBox(options);
					scanActivityDecr.setComboBox(comboBox);
				}
				scanActivityDecr.setRemarks(JasyptConfig.decryptKey(scanActivity.getRemarks()));
				scanActivityDecr.setScanDetail(JasyptConfig.decryptKey(scanActivity.getScanDetail()));
				scanActivityDecr.setScanTypeDetail(JasyptConfig.decryptKey(scanActivity.getScanTypeDetail()));
				scanActivityDecr.setScanPath(JasyptConfig.decryptKey(scanActivity.getScanPath()));
			}
			
			scanActivityDecr.setScanTypeId(scanActivity.getScanTypeId());
			scanActivityDecr.setType(scanActivity.getType());
			scanActivityDecr.setUserScanActivityId(scanActivity.getUserScanActivityId());
			scanActivityListDecr.add(scanActivityDecr);
		}
		return scanActivityListDecr;
	}

	private Boolean countForFileName(String scanPath) {
		List<ScanActivity>  scanActivityList =scanActivityDetailRepository.duplicateFileChek();
	for(ScanActivity scanActivity :scanActivityList) {
		if(JasyptConfig.decryptKey(scanActivity.getScanPath()).equalsIgnoreCase(scanPath)) {
			return false;
		}
	}
		return true;
	}
	
	private Boolean checkForFileNameIP(String ipAddress,String filepath) {
		List<ScanActivity>  scanActivityList =scanActivityDetailRepository.duplicateFileChekIP();
	for(ScanActivity scanActivity :scanActivityList) {
		if(JasyptConfig.decryptKey(scanActivity.getScanDetail()).equalsIgnoreCase(filepath) && JasyptConfig.decryptKey(scanActivity.getIpAddress()).equalsIgnoreCase(ipAddress)) {
			return false;
		}
	}
		return true;
	}
	
}
