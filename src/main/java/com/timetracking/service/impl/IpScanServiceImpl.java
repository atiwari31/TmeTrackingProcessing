package com.dcardprocessing.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import org.apache.poi.POIXMLProperties.CoreProperties;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextShape;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcardprocessing.JasyptConfig;
import com.dcardprocessing.bean.ScanActivity;
import com.dcardprocessing.bean.UserScanActivity;
import com.dcardprocessing.repository.IPRepository;
import com.dcardprocessing.service.IPScanService;
import com.dcardprocessing.util.Utility;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
@Service
public class IpScanServiceImpl implements IPScanService{
	
	@Autowired
	IPRepository iPRepository;

	@Override
	public UserScanActivity save(UserScanActivity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserScanActivity update(UserScanActivity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(UserScanActivity entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteInBatch(List<UserScanActivity> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UserScanActivity find(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserScanActivity> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ScanActivity> ipAddressScan(String IPAddres, String toAddres ,String userName, String password) {
		
		
		List<String> ipList = remoteConnectionList(IPAddres,toAddres);
		
		JSch jsch = new JSch();
        Session session = null;
        Boolean flagValidWorkbook = false;
        ScanActivity scannedFile = null;
        List<ScanActivity> sacnFiles= new ArrayList<>();
        PathMatcher filematcher =  FileSystems.getDefault().getPathMatcher("glob:*.{pptx,docx,xlsx,doc,xml,xls}");
        for(String ipAddress:ipList) {
        File[] drives = File.listRoots();
        if(drives.length > 0 && drives != null)
    	{
        	 for (File drive : drives) 
     	    {
        try {
            session = jsch.getSession(userName, ipAddress, 22);
        	//session = jsch.getSession(userName, IPAddres, 22);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(password);
            session.connect();

            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftpChannel = (ChannelSftp) channel;
            //System.out.println("Directory:" + sftpChannel.pwd());
            
            sftpChannel.cd("/"+drive+"\\");
            //System.out.println("Directory start cd:" + sftpChannel.pwd());
            String remotePath ="/"+drive+"\\" ;//sftpChannel.pwd();
            System.out.println("Directory after cd:" + remotePath);
            Vector<ChannelSftp.LsEntry> files = sftpChannel.ls("/"+drive+"\\");
            
            for (ChannelSftp.LsEntry entry : files) {
            	try {
					scannedFile = new ScanActivity();  
					if(entry.getAttrs().isDir())
					{
						
						File directoryPath = new File("/"+drive+"\\"+entry.getFilename());
						File filesList[] = directoryPath.listFiles();
						Files.walkFileTree(Paths.get("/"+drive+"\\"+entry.getFilename()), new SimpleFileVisitor<Path>() {
							
							@Override
							public FileVisitResult visitFile(Path path,
									BasicFileAttributes attrs) throws IOException {
								ScanActivity scannedFile = new ScanActivity();  
								Boolean flagValidWorkbook = false;
								scannedFile.setScanTypeDetail(IPAddres);
						  		scannedFile.setScanDetail(drive+entry.getFilename());
					    	 if ((drive+entry.getFilename()).toString().contains(".txt"))
							{
					    		
								flagValidWorkbook=Utility.findLines((remotePath + "/" + entry.getFilename()).toString());
							}
						  if ((drive+entry.getFilename()).toString().contains(".docx"))
							{
								flagValidWorkbook=Utility.findLineDocs((remotePath + "/" + entry.getFilename()).toString());
							}
							if((drive+entry.getFilename()).toString().contains(".xls") || (remotePath + "/" + entry.getFilename()).toString().contains(".xlsx"))
							{
								flagValidWorkbook=Utility.excelRead((remotePath + "/" + entry.getFilename()).toString());
							}
							if((drive+entry.getFilename()).toString().contains(".pptx"))
							{
								flagValidWorkbook=Utility.pptRead((remotePath + "/" + entry.getFilename()).toString());
							}				
							if(null!=flagValidWorkbook && flagValidWorkbook)
							{
								scannedFile.setType("truePositive");
								
						        System.out.println("file is valid => " + (remotePath + "/" + entry.getFilename()).toString());
						        sacnFiles.add(scannedFile);
							}
								
								return FileVisitResult.CONTINUE;
							}

							@Override
							public FileVisitResult visitFileFailed(Path file, IOException exc)
									throws IOException {
								return FileVisitResult.CONTINUE;
							}
						});
						
										
					}else {
						System.out.println(" ################# esle iS dIRECTORY 1>>>>>>>> "+remotePath + "/" + entry.getFilename());
						flagValidWorkbook=false;	
						 if ((entry.getFilename()).toString().contains(".txt"))
						{
							 //System.out.println(" ################# esle iS dIRECTORY 2>>>>>>>> "+ entry.getFilename());
							 Boolean isValidFlag=null;
							 flagValidWorkbook=false;	
								InputStream inputStream = sftpChannel.get(entry.getFilename());
								Scanner sc = new Scanner(new InputStreamReader(inputStream));
								while (sc.hasNext()) {
									//System.out.println(" ################# while loop 1>>>>>>> ");
									isValidFlag=Utility.validateCreditCardNumber(sc.next());
									//System.out.println(" ################# while loop 2>>>>>>>> "+isValidFlag);
								}
								
							flagValidWorkbook=isValidFlag;
							//System.out.println(" ################# esle iS dIRECTORY 3>>>>>>>> "+flagValidWorkbook);
							 sc.close();
						} if ((remotePath + "/" + entry.getFilename()).toString().contains(".docx"))
						{
							//flagValidWorkbook=Utility.findLineDocs((remotePath + "/" + entry.getFilename()).toString());
							// System.out.println(" ################# esle iS dIRECTORY 2 docx >>>>>>>> "+ entry.getFilename());
							 Boolean isValidFlag=null;
							 flagValidWorkbook=false;
								InputStream inputStream = sftpChannel.get(entry.getFilename());
								 try (XWPFDocument doc = new XWPFDocument(inputStream)) {
									  	
							            XWPFWordExtractor xwpfWordExtractor = new XWPFWordExtractor(doc);
							            String docText = xwpfWordExtractor.getText();
							            System.out.println(docText);

							            // find number of words in the document
							            long count = Arrays.stream(docText.split("\\s+")).count();
							            String str[]=docText.split("\\s+");
							            for(String str1:str) {
							            	isValidFlag=Utility.validateCreditCardNumber(str1);
							            }
								 }
								
							 flagValidWorkbook=isValidFlag;
							 //System.out.println(" ################# esle iS dIRECTORY 3>>>>>>>> "+flagValidWorkbook);
							 
						}
						if((remotePath + "//" + entry.getFilename()).toString().contains(".xls") || (remotePath + "//" + entry.getFilename()).toString().contains(".xlsx"))
						{
							 flagValidWorkbook=false;
							flagValidWorkbook=Utility.excelRead((remotePath + "//" + entry.getFilename()).toString());
						}
						if((remotePath + "/" + entry.getFilename()).toString().contains(".pptx"))
						{
							//flagValidWorkbook=Utility.pptRead((remotePath + "/" + entry.getFilename()).toString());
							 System.out.println(" ################# esle iS dIRECTORY 2 pptx >>>>>>>> "+ entry.getFilename());
							 Boolean isValidFlag=null;
							 flagValidWorkbook=false;
								InputStream inputStream = sftpChannel.get(entry.getFilename());
								XMLSlideShow ppt= new XMLSlideShow(inputStream);
							    CoreProperties props = ppt.getProperties().getCoreProperties();
							    String title = props.getTitle();
							  for (XSLFSlide slide: ppt.getSlides()) {
						        	List<XSLFShape> shapes = slide.getShapes();
						        	for (XSLFShape shape: shapes) {
						        		if (shape instanceof XSLFTextShape) {
						        	        XSLFTextShape textShape = (XSLFTextShape)shape;
						        	        String text = textShape.getText();
						        	        String str[]=text.split("\\s+");
						    	            for(String str1:str) {
						    	            	isValidFlag=Utility.validateCreditCardNumber(str1);
						    	            	 if(isValidFlag) {
						 	        	        	break;
						 	        	        }
						    	            }
						        	        
						        	       
						        		}
						        	}
						        }
								
							 flagValidWorkbook=isValidFlag;
							 //System.out.println(" ################# esle iS dIRECTORY 3>>>>>>>> "+flagValidWorkbook);
							}				
						if(null!=flagValidWorkbook && flagValidWorkbook)
						{
							System.out.println(" ################# Finela ############ "+flagValidWorkbook);
							scannedFile.setScanTypeDetail(IPAddres);
							scannedFile.setDbUserName(userName);
							scannedFile.setIpAddress(IPAddres);
							scannedFile.setDbPassword(password);
					  		scannedFile.setScanDetail((remotePath + entry.getFilename()).toString());
							scannedFile.setType("truePositive");
					        System.out.println("file is valid => " + (remotePath + "/" + entry.getFilename()).toString());
					        sacnFiles.add(scannedFile);
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}

          
            }
            sftpChannel.exit();
            session.disconnect();
        } catch (JSchException e) {
        	Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Remote Connection Issue!");
			alert.setHeaderText(null);
			alert.setContentText("Remote Connection Connection Issue :Please check IP address/User Name/Password");
			alert.showAndWait();
			break;
        } catch (SftpException e) {
            ///e.printStackTrace();
        }  catch (Exception e) {
        
		}
    	}
    	}
        }
       
		return sacnFiles;
	}

	
	
	public List<String> remoteConnectionList(String fromIpAddress,String toIpAddress) {
		 List<String> ipList=new ArrayList<String>();
		if(toIpAddress==null || toIpAddress.isEmpty()) {
			ipList.add(fromIpAddress);
			return ipList;
		}
		
		int ipLastIndex= fromIpAddress.lastIndexOf(".");
		String ipLastIndexValue = fromIpAddress.substring(ipLastIndex+1, fromIpAddress.length());
		String ipStartValue = fromIpAddress.substring(0, ipLastIndex+1);
		int ipToLastIndex= toIpAddress.lastIndexOf(".");
		String toIpLastIndex = toIpAddress.substring(ipToLastIndex+1, toIpAddress.length());
		  
		  for(int k=Integer.valueOf(ipLastIndexValue);k<=Integer.valueOf(toIpLastIndex);k++){
			  ipList.add(ipStartValue+k);
		  }
		  return ipList;
	}
	
	@Override
	public void remoteConnectionDeleteFile(Long ip) {
		
		ScanActivity scanActivity =iPRepository.findOne(ip);
		JSch jsch = new JSch();
        Session session = null;
        try {
			session = jsch.getSession(JasyptConfig.decryptKey(scanActivity.getDbUserName()), JasyptConfig.decryptKey(scanActivity.getIpAddress()), 22);
			//session = jsch.getSession(userName, IPAddres, 22);
			session.setConfig("StrictHostKeyChecking", "no");
			session.setPassword(JasyptConfig.decryptKey(scanActivity.getDbPassword()));
			session.connect();

			Channel channel = session.openChannel("sftp");
			channel.connect();
			ChannelSftp sftpChannel = (ChannelSftp) channel;
			sftpChannel.rm(JasyptConfig.decryptKey(scanActivity.getScanDetail()));
		} catch (JSchException | SftpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
