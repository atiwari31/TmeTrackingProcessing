package com.dcardprocessing.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

@Component
public class RemoteConnection {

    
	public void remoteConnectionTest(List<String> ipList,String userName,String password) {
		JSch jsch = new JSch();
	    Session session = null;
		int count=2;
		FileOutputStream out;
		String connectionStatus="Disconnected";
		 XSSFWorkbook workbook = new XSSFWorkbook();
	     XSSFSheet spreadsheet = workbook.createSheet("Remote Connection ");
	     System.out.println("Done");
	     System.out.println("Done");
	        // creating a row object
	        XSSFRow row;
	        Map<String, Object[]> studentData
	        = new TreeMap<String, Object[]>();
	        studentData.put(
	                "1",
	                new Object[] { "User Name", "IP Address", "Connection Status" });
	        Set<String> keyid= null;
	        int rowid = 0;
	for(String ip:ipList) {	
	try {
		session = jsch.getSession(userName, ip, 22);
		session.setConfig("StrictHostKeyChecking", "no");
		session.setPassword(password);
		session.connect();
		Channel channel = session.openChannel("sftp");
		channel.connect();
		if(channel.isConnected()) {
			connectionStatus="Connected";
			studentData.put(String.valueOf(count), new Object[] {userName, ip,connectionStatus});
			 keyid = studentData.keySet();
		}
		
	} catch (Exception e) {
		studentData.put(String.valueOf(count), new Object[] {userName, ip,"Disconnected"});
		keyid = studentData.keySet();
	}
	
	 
	count++;
	}
	try {
		  for (String key : keyid) {
			   
		         row = spreadsheet.createRow(rowid++);
		         Object[] objectArr = studentData.get(key);
		         int cellid = 0;

		         for (Object obj : objectArr) {
		             Cell cell = row.createCell(cellid++);
		             cell.setCellValue((String)obj);
		         }
		     }
		
		 String home = System.getProperty("user.home");
	        FileOutputStream fileOut = new FileOutputStream(home+"/Downloads/Remote Connection.xlsx");
	        workbook.write(fileOut);
	        fileOut.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     // workbook object
	} 
  
	}

