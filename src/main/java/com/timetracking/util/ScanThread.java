package com.dcardprocessing.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import com.dcardprocessing.bean.ScanActivity;

public class ScanThread implements Callable<List<ScanActivity>> {
	String text;
	String filePath;
	String fileText;
	String status;
	String glob;
	List<ScanActivity> scannedFiles = new ArrayList<ScanActivity>();

	public ScanThread(String glob,String text, String filePath, String fileText, List<ScanActivity> scannedFile, String status) {
		
		this.glob=glob;
		this.text = text;
		this.filePath = filePath;
		this.fileText = fileText;
		this.scannedFiles = scannedFile;
		this.status = status;
	}

	@Override
	public List<ScanActivity> call() throws Exception {
		try {
		scannedFiles = Utility.match(glob, text, filePath, fileText, scannedFiles, "Done");
		}catch(Exception e) {
			e.printStackTrace();
		}
		return scannedFiles;
	}

}
