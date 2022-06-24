package com.dcardprocessing.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.support.CronSequenceGenerator;

import com.dcardprocessing.bean.ScanSchedule;

public class TestIP {
   public static void main(String args[]) throws IOException {
	   
//		String timeCorn= "17:10:5";///hr min secnd
//		String[] arrOfStr=timeCorn.split(":");
//	
//	   System.out.println(arrOfStr[]);
	   
//	   String fromIP="10.10.10.11";
//	   String toIP="10.10.10.15";
//	   
//	  int i= fromIP.lastIndexOf(".");
//	  String finalStr = fromIP.substring(i+1, fromIP.length());
//	  String finalStr3 = fromIP.substring(0, i+1);
//	  System.out.println(finalStr);
//	  int j= toIP.lastIndexOf(".");
//	  String finalStr2 = toIP.substring(j+1, fromIP.length());
//	  System.out.println(finalStr2);
//	  System.out.println(finalStr3);
//	  List<String> ipList=new ArrayList<String>();
//	  for(int k=Integer.valueOf(finalStr);k<=Integer.valueOf(finalStr2);k++){
//		  ipList.add(finalStr3+k);
//		 
//	  }
//	  System.out.println(ipList);
   
   
//   File[] drives = File.listRoots();
//	if(drives.length > 0 && drives != null)
//	{
//		for (File drive : drives) 
//	    {
//			if(drive.length()>0) {
//				System.out.println(drive);
//				System.out.println(drive.getAbsolutePath());
//			}
//	    }
//	}
	
//
//	        LocalDateTime dateTime = LocalDateTime.now();
//
//	        System.out.println(toCron(String.valueOf(dateTime.getMinute()),
//	                String.valueOf(dateTime.getHour()),
//	                String.valueOf(dateTime.getDayOfMonth()),
//	                String.valueOf(dateTime.getMonth()),
//	                String.valueOf(dateTime.getDayOfWeek()), String.valueOf(dateTime.getYear())));
//	   CronSequenceGenerator generator = new CronSequenceGenerator("5 10 15 14 8 ?");
//	   Date nextRunDate= generator.next(new Date());
//	   System.out.println("Date:: " + nextRunDate);
//	    
//   }
//
//	    public static String toCron(final String mins, final String hrs, final String dayOfMonth, final String month, final String dayOfWeek, final String year) {
//	        return String.format("%s %s %s %s %s %s", mins, hrs, dayOfMonth, month, dayOfWeek, year);
	    //}
   
   
//	@Bean
//	public String getCornExpression() {
//		List<ScanSchedule> listScanSchedule = scanScheduleService.getScheduleByDate();
//		int cornDate=listScanSchedule.get(0).getScanDate().getDate();
//		int cornMonth=listScanSchedule.get(0).getScanDate().getMonth();
//		String[] arrOfStr=listScanSchedule.get(0).getScanTime().split(":");
//		int hr=Integer.valueOf(arrOfStr[0]);
//		int minute=Integer.valueOf(arrOfStr[1]);
//		int sec=Integer.valueOf(arrOfStr[2]);	
//		String corn=sec+" "+minute+" "+hr+" "+cornDate+" "+cornMonth+ " " + "?"; 
//		System.out.println(corn);
//		return corn;
//	}
	    
   try (XWPFDocument doc = new XWPFDocument(
           Files.newInputStream(Paths.get("E:\\testScan\\testScan\\New_Creadit.docx")))) {
	  	Boolean isValidFlag=null;
	  	List<XWPFParagraph> xwpfParagraphList=doc.getParagraphs();
       
	    for (XWPFParagraph xwpfParagraph : xwpfParagraphList) {
            for (XWPFRun xwpfRun : xwpfParagraph.getRuns()) {
                String docText = xwpfRun.getParagraph().getText().replaceAll(" ", "");
                System.out.println(docText);
            }
        }

   }
}
}
