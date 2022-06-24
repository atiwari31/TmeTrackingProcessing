package com.dcardprocessing.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dcardprocessing.JasyptConfig;
import com.dcardprocessing.bean.LogActivityDetail;
import com.dcardprocessing.bean.ScanActivity;
import com.dcardprocessing.bean.ScanSchedule;
import com.dcardprocessing.bean.UserScanActivity;
import com.dcardprocessing.controller.LoginController;
import com.dcardprocessing.service.LogActivityDetailService;
import com.dcardprocessing.service.ScanActivityService;
import com.dcardprocessing.service.ScanScheduleService;
import com.dcardprocessing.service.UserScanActivityService;

@Component
@EnableScheduling
public class ScanScheduleUtility {
	

	@Autowired
	UserScanActivityService userScanActivityService;
	@Autowired
	LogActivityDetailService logActivityDetailService;
	@Autowired
	ScanActivityService scanActivityService;
	
	@Autowired
	ScanScheduleService scanScheduleService;
	
//	@Scheduled(cron = "0/20 * * * * ?")
	//@Scheduled(cron = "0 0 12 * * ?")
	public void scanSchdule() {
	
	List<ScanSchedule> listScanSchedule = scanScheduleService.getScheduleByDate();
	if(null!=listScanSchedule && !listScanSchedule.isEmpty()) { 
		System.out.println("Done>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	File[] drives = File.listRoots();
	if(drives.length > 0 && drives != null)
	{
	    for (File drive : drives) 
	    {
	    	if(drive.length()>0) {
	    	UserScanActivity userScanActivity = new UserScanActivity();
	   		userScanActivity.setFilePath(drive.getAbsolutePath());
	   		userScanActivity.setTimestamp(new Date());
	   		userScanActivityService.save(userScanActivity);
	   		try {
				if(loadScannedFiles(drive.getAbsolutePath(), userScanActivityService.findMaxID())){
					LogActivityDetail logActivityDetail = new LogActivityDetail();
					logActivityDetail.setUserName(JasyptConfig.encryptKey(LoginController.getUserInstance().getFirstName() + "  "
							+ LoginController.getUserInstance().getLastName()));
					logActivityDetail.setActivityDate(new Date());
					logActivityDetail.setActivityName(JasyptConfig.encryptKey("SCAN"));
					logActivityDetail.setScanRandomId(getRandom(userScanActivityService.findMaxID()).toString());
					logActivityDetail.setFilePath(JasyptConfig.encryptKey(drive.getAbsolutePath()));
					logActivityDetail.setScanActivityId(userScanActivityService.findMaxID());
					logActivityDetailService.save(logActivityDetail);
				}
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	}
	    }
	}
	}
}
	
	private Boolean loadScannedFiles(String filePath, int userScanActivityId)
			throws InterruptedException, ExecutionException {
		List<ScanActivity> scannedFiles = new ArrayList<ScanActivity>();
		  
		/** Calling for scanning of folder and Drive **/
		List<ScanActivity> scannedFilesList = scanActivityService.fileScaning(filePath, filePath,
				filePath, scannedFiles, "Done", userScanActivityId);

		if (!scannedFilesList.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
	public static String getRandom(int max) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMddhhmmss");
		String dateAsString = simpleDateFormat.format(new Date());
		return "scan@" + (int) (Math.random() * max) + dateAsString;
	}
}