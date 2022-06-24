package com.dcardprocessing.util;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.stereotype.Component;

import com.dcardprocessing.bean.ScanActivity;

@Component
public class ThreadExecutor {

	public Future<List<ScanActivity>> thredExe(String blog,String text, String filePath, String fileText, List<ScanActivity> scannedFile,
			String status) {
		ExecutorService executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
		
		ScanThread ScanThread = new ScanThread(blog,text, filePath, fileText, scannedFile, status);
		Future<List<ScanActivity>> scannfileList = executor.submit(ScanThread);
		// shut down the executor service now
		executor.shutdown();
		return scannfileList;

	}
}
