package com.dcardprocessing.util;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileOutputStream;
public class NetworkShareFileCopy {

	static final String USER_NAME = "tiwarijeedavidjee@gmail.com";
    static final String PASSWORD = "shubham@12345";
    //e.g. Assuming your network folder is: \my.myserver.netsharedpublicphotos
    //192.168.29.89
    static final String NETWORK_FOLDER = "smb://192.168.29.89//e//";
 
    public static void main(String args[]) {
        String fileContent = "This is a test file";
       // SmbFile smbfile = getSmbFile(NETWORK_FOLDER, getCredentials(USER_NAME, PASSWORD));
        new NetworkShareFileCopy().copyFiles(fileContent, "test.txt");
    }
 
    public boolean copyFiles(String fileContent, String fileName) {
        boolean successful = false;
         try{
                //String user = USER_NAME + ":" + PASSWORD;
                //System.out.println("User: " + user);
                NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(null, USER_NAME, PASSWORD);

               // NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(user);
                //String path = NETWORK_FOLDER + fileName;
                System.out.println("Path: " +NETWORK_FOLDER);
 
                SmbFile sFile = new SmbFile(NETWORK_FOLDER, auth);
               // SmbFile dir = new SmbFile(url, auth);
                for (SmbFile f : sFile.listFiles())
                {
                  System.out.println("test"+f.getName());
                }
//                SmbFileOutputStream sfos = new SmbFileOutputStream(sFile);
//                sfos.write(fileContent.getBytes());
// 
//                successful = true;
//                System.out.println("Successful" + successful);
//                sfos.close();
//                System.exit(0);
            } catch (Exception e) {
                successful = false;
                e.printStackTrace();
            }
        return successful;
    }
    
    public static NtlmPasswordAuthentication getCredentials(String usr, String pwd) {
        NtlmPasswordAuthentication credentials = null;
        if(usr.length()==0){
            credentials = NtlmPasswordAuthentication.ANONYMOUS;
        }else {
            credentials = new NtlmPasswordAuthentication(null, usr,
                    pwd);
        }
        return credentials;
    }
}
