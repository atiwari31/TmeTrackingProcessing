package com.dcardprocessing.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.POIXMLProperties.CoreProperties;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextShape;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import com.dcardprocessing.JasyptConfig;
import com.dcardprocessing.bean.LogFile;
import com.dcardprocessing.bean.ScanActivity;
import com.dcardprocessing.bean.ScannedFile;




public class Utility {
	public static List<ScanActivity> listf(String ipAddress,String directoryName, List<ScanActivity> files,String Type) {
		File directory = new File(directoryName);
		int folderCount=0;
		String regex = "^(?:(?<visa>4[0-9]{12}(?:[0-9]{3})?)|" +
		        "(?<mastercard>5[1-5][0-9]{14})|" +
		        "(?<discover>6(?:011|5[0-9]{2})[0-9]{12})|" +
		        "(?<amex>3[47][0-9]{13})|" +
		        "(?<diners>3(?:0[0-5]|[68][0-9])?[0-9]{11})|" +
		        "(?<jcb>(?:2131|1800|35[0-9]{3})[0-9]{11}))$";
		Pattern pattern = Pattern.compile(regex);
		
	/*	try {
			Find.searchInFile(directoryName, regex);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	    // get all the files from a directory
	   File[] fList1 = directory.listFiles();

	    for (File file : fList1) {
	    	if (file.isDirectory()) {
	    		folderCount=folderCount;
				//System.out.println("Folders count in this path => " + folderCount); 
			}
	    }
	    String glob = "glob:**/*.xls";
		String path = directoryName;
		try {
			match(glob, path ,ipAddress,directoryName, files,Type);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Boolean flagValidWorkbook = false;
		//Collection<File> filess = FileUtils.listFiles(directoryName, new String[]{"xlxs"}, true);

		// Get all files from a directory.
		//Files.walk(Paths.get(".")).filter((path) -> path.toString().endsWith(".java")).forEach(System.out::println);
		File[] fList = directory.listFiles();
		if (fList != null)
			for (File file : fList) {
				ScanActivity scannedFile = new ScanActivity();  
				if (file.isFile()) {
						PathMatcher filematcher =  FileSystems.getDefault().getPathMatcher("glob:*.{pptx,docx,xlsx,doc,xml,xls}");
						
							


					scannedFile.setScanTypeDetail(ipAddress.toString());
					scannedFile.setScanDetail(file.getName());
			        //System.out.println("file 1 => " + file.toString());
					//flagValidWorkbook = excelRead(file.toString());
					flagValidWorkbook=false;
					if(flagValidWorkbook)
					{
				
						scannedFile.setType("truePositive");
				        System.out.println("file is valid => " + file.toPath());

						files.add(scannedFile);
					}
				} else if (file.isDirectory()) {
					//listf(ipAddress, file.getAbsolutePath(), files,"truePositive");
				}
			}
		return files;
		
	}
	  
	  public static List<ScanActivity> match(String glob, String location,String ipAddress,String directoryName, List<ScanActivity> files,String Type)  {
		  
		  
			try {
				final PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher(
						glob);

				Files.walkFileTree(Paths.get(location), new SimpleFileVisitor<Path>() {
					
					@Override
					public FileVisitResult visitFile(Path path,
							BasicFileAttributes attrs) throws IOException {
						ScanActivity scannedFile = new ScanActivity();  
						Boolean flagValidWorkbook = false;

						if (pathMatcher.matches(path)) {
							scannedFile.setScanTypeDetail(ipAddress.toString());
							scannedFile.setScanDetail(path.getFileName().toString());
							scannedFile.setScanPath(path.toString());
							if (path.toString().contains(".txt"))
							{
								flagValidWorkbook=findLines(path.toString());
							} if (path.toString().contains(".docx"))
							{
								flagValidWorkbook=findLineDocs(path.toString());
							}
							if(path.toString().contains(".xls") || path.toString().contains(".xlsx"))
							{
								if(null!=path)
								flagValidWorkbook=excelRead(path.toString());
							}
							if(path.toString().contains(".pptx"))
							{
								if(null!=path)
								flagValidWorkbook=pptRead(path.toString());
							}				
							if(null!=flagValidWorkbook && flagValidWorkbook)
							{
								scannedFile.setType("truePositive");
						        System.out.println("file is valid => " + path.getFileName().toString());
								files.add(scannedFile);
							}
						}
						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult visitFileFailed(Path file, IOException exc)
							throws IOException {
						return FileVisitResult.CONTINUE;
					}
				});
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			return files;
		}
	  
	
	  public static Boolean findLines(String fileName) throws IOException {
		  System.out.println(" ################# Utils2 >>>>>>>> "+fileName);
		  File file = new File(fileName);
		  Boolean isValidFlag=null;
			Scanner sc = new Scanner(file);
			while (sc.hasNextLine()) {
				
				isValidFlag=validateCreditCardNumber(sc.nextLine());
			}
			sc.close();
			 System.out.println(" ################# Utils 2>>>>>>>> "+isValidFlag);
			return isValidFlag;
	  }

	  public static Boolean findLineDocs(String fileName) throws IOException {
		  try (XWPFDocument doc = new XWPFDocument(
	                Files.newInputStream(Paths.get(fileName)))) {
			  	Boolean isValidFlag=null;
	            XWPFWordExtractor xwpfWordExtractor = new XWPFWordExtractor(doc);
	            String docText = xwpfWordExtractor.getText().replaceAll(" ", "");;
	            System.out.println(docText);

	            // find number of words in the document
	            long count = Arrays.stream(docText.split("\\s+")).count();
	            String str[]=docText.split("\\s+");
	            for(String str1:str) {
	            	isValidFlag=validateCreditCardNumber(str1);
	            }
	            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> True");

	            return isValidFlag;
	        }
		
			//return isValidFlag;
	  }
	  
	  
	public static boolean excelRead(String excelpath)
	{
		Boolean flagValidCardNumber = false;
        System.out.println("excelpath => " + excelpath);

		try {
			File file = new File(excelpath);   //creating a new file instance  
			FileInputStream fis = new FileInputStream(file);   //obtaining bytes from the file  

	        Workbook workbook = WorkbookFactory.create(new File(excelpath));
	        Iterator<Sheet> sheetIterator = workbook.sheetIterator();
			FormulaEvaluator formulaEvaluator=workbook.getCreationHelper().createFormulaEvaluator();

			while (sheetIterator.hasNext()) {
	            Sheet sheet = sheetIterator.next();
	            System.out.println("=> " + sheet.getSheetName());
					for(Row row: sheet)     //iteration over row using for each loop  
					{  
					for(Cell cell: row)    //iteration over cell using for each loop  
					{  
						switch(formulaEvaluator.evaluateInCell(cell).getCellType())  
						{  
							case Cell.CELL_TYPE_NUMERIC:   //field that represents numeric cell type  
								    String cell_value = NumberToTextConverter.toText(cell.getNumericCellValue());
								    flagValidCardNumber = isValidCreditCardNumberExcel(cell_value.trim());
								    System.out.println("====================="+flagValidCardNumber);
								    //flagValidCardNumber =isValid((long) cell.getNumericCellValue());
								   // System.out.println(cell_value + " is " +   (isValid((long) cell.getNumericCellValue()) ? "valid" : "invalid"));
								    if (flagValidCardNumber)
								    {
									    	break;
								    }
						}  
						if (flagValidCardNumber)
					    {
						    	break;
					    }
					}
					if (flagValidCardNumber)
				    {
					    	break;
				    }
						//break;		
					}
					if (flagValidCardNumber)
				    {
					    	break;
				    }
			}
			workbook.close();

		}
		catch(Exception e)  
		{  
		e.printStackTrace();  
		}

		return flagValidCardNumber;  
		
	}
	public static boolean pptRead(String excelpath)
	{
		System.out.println("Starting slide..."+excelpath);
		Boolean isValidFlag=null;
		try {
		FileInputStream inputStream;
		XMLSlideShow ppt;
		
			inputStream = new FileInputStream(excelpath);
			ppt = new XMLSlideShow(inputStream);
		    CoreProperties props = ppt.getProperties().getCoreProperties();
		    String title = props.getTitle();
		  for (XSLFSlide slide: ppt.getSlides()) {
	        	List<XSLFShape> shapes = slide.getShapes();
	        	for (XSLFShape shape: shapes) {
	        		if (shape instanceof XSLFTextShape) {
	        	        XSLFTextShape textShape = (XSLFTextShape)shape;
	        	        String text = textShape.getText();
	        	        //String str[]=text.split("\\s+");
	    	            //for(String str1:str) {
	    	            	isValidFlag=validateCreditCardNumber(text);
	    	            	 if(isValidFlag) {
	 	        	        	break;
	 	        	        }
	    	           // }
	        	        
	        	       
	        		}
	        	}
	        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return false;
		}
		return isValidFlag;
	}
	
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

		String regex = "[0-9]+[\\.]?[0-9]*";
		if (str == null) {
	        return false; 
	    }
	    return pattern.matcher(str).matches();
	    
	}
	public static int sumDigits(int[] arr)
    {
        return Arrays.stream(arr).sum();
    }
	public static boolean isValidCreditCardNumber(String cardNumber)
    {
        try{// int array for processing the cardNumber
        int[] cardIntArray=new int[cardNumber.length()];
 
        for(int i=0;i<cardNumber.length();i++)
        {
            char c= cardNumber.charAt(i);
            cardIntArray[i]=  Integer.parseInt(""+c);
        }
 
        for(int i=cardIntArray.length-2;i>=0;i=i-2)
        {
            int num = cardIntArray[i];
            num = num * 2;  // step 1
            if(num>9)
            {
                num = num%10 + num/10;  // step 2
            }
            cardIntArray[i]=num;
        }
 
        int sum = sumDigits(cardIntArray);  // step 3
 
        System.out.println(sum);
 
        if(sum%10==0)  // step 4
        {
            return true;
        }
        }catch(Exception e) {
        	e.printStackTrace();
        	return false;
        }
 
        return false;
 
    }
	public static boolean validateCreditCardNumber(String line) {
		System.out.println(" ################# validateCreditCardNumber>>>>>>>> "+line);
		String str=line.replaceAll(" ", "");
		if(isNumeric(str)) {
			if(str.length()>=14)
			{
							int[] ints = new int[str.length()];
							for (int i = 0; i < str.length(); i++) {
								ints[i] = Integer.parseInt(str.substring(i, i + 1));
							}
							for (int i = ints.length - 2; i >= 0; i = i - 2) {
								int j = ints[i];
								j = j * 2;
								if (j > 9) {
									j = j % 10 + 1;
								}
								ints[i] = j;
							}
							int sum = 0;
							for (int i = 0; i < ints.length; i++) {
								sum += ints[i];
							}
							if (sum % 10 == 0) {
								System.out.println(str + " is a valid credit card number");
								return true;
							} else {
								System.out.println(str + " is an invalid credit card number");
								return false;
							}
			}
			else
			{
				return false;
			}
				}
		else
		{
			return false;
		}
	}
	public static List<ScanActivity> DBprofile(String server, String port,String username, String password,String dbname, String remarks, List<ScanActivity> tables,int userScanActivityID) 
	{
        // store the SQL statement in a string
		   final String USER =username;
		   final String PASS =password;
		   String JDBC_DRIVER ="";
		   String DB_URL ="";
		   String DATABASE_NAME=dbname;

		   Boolean isCardData=false;
			 JDBC_DRIVER = "com.mysql.jdbc.Driver"; 
			 DB_URL= "jdbc:mysql://"+server+":"+port+"/"+ DATABASE_NAME+"?characterEncoding=latin1&useConfigs=maxPerformance&useSSL=false";
			 ScanActivity scannedMySQL= new ScanActivity();  
		   scannedMySQL.setScanTypeDetail(server +" - "+port);
		   scannedMySQL.setType(null);
		   scannedMySQL.setRemarks(remarks);
		   scannedMySQL.setStatus("SCAN");
		   scannedMySQL.setCurrentStatus("SCAN");
		   scannedMySQL.setUserScanActivityId(userScanActivityID);
		   
		   String QUERY = "select * from card";
	        try {
				Class.forName(JDBC_DRIVER);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	        try(Connection conn = DriverManager.getConnection(DB_URL,USER,PASS))
	        {
	                Statement statemnt1 = conn.createStatement();
	           
	                ResultSet rs = null;
	                DatabaseMetaData meta = (DatabaseMetaData) conn.getMetaData();
	                rs = meta.getTables(DATABASE_NAME, null, null, new String[] {
	                   "TABLE"
	                });
	                int count = 0;
	                int columnCount=0;

	                   
	                
	                while (rs.next()) {
	                   String tblName = rs.getString("TABLE_NAME");
	                   String   catalog           = DATABASE_NAME;
	                   String   schemaPattern     = null;
	                   String   tableNamePattern  = tblName;
	                   String   columnNamePattern = null;
		                QUERY = "select * from   "+tblName;
		                Statement statemnt_query = conn.createStatement();


	                   ResultSet result = meta.getColumns(
	                       catalog, schemaPattern,  tableNamePattern, columnNamePattern);
	                   ResultSet rs1 = statemnt_query.executeQuery(QUERY); 
	                   ResultSetMetaData rsmd = rs1.getMetaData();
	                   int column_count = rsmd.getColumnCount();
	                	   			
	                   String cardNumber ="";
	                   System.out.println("column count --"+column_count);

		                	   while(rs1.next())
			                    {
		                		   for(int i=1;i<=column_count;i++)
		                		   {
					                   // System.out.println("Data type name: "+rs1.getString("TYPE_NAME"));
			                        cardNumber = rs1.getString(i);
			                        if (cardNumber !=null)
			                        {
			                        	System.out.println("cardNumber.length --"+cardNumber.length());
				                        isCardData = validateCreditCardNumber(cardNumber);
				                        if (isCardData)
				                        {
				                        	scannedMySQL.setScanDetail(tblName);
				                        	tables.add(scannedMySQL);
						                    System.out.println("tblName --"+tblName);
				 		                    //System.out.println("columnName ---" + columnName);
						                    System.out.println("cardNumber --"+cardNumber);
						                    break;
				                        }
			                        }
		                		   
		                		   }
			                    }
		                	   
	                   
	                  
	                   
	                   count++;
	                }
	                System.out.println(count + " Rows in set ");
	                
	        } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}     
		return tables;
       }
	
	
	public static List<LogFile> loglistf(String ipAddress,String directoryName, List<LogFile> files,String Type) {
		File directory = new File(directoryName);

		// Get all files from a directory.
		File[] fList = directory.listFiles();
		if (fList != null)
			for (File file : fList) {
				LogFile logFile = new LogFile();  
				if (file.isFile()) {
					logFile.setIpAddress("127.0.0.1");
					logFile.setFileName(file.getName());
					logFile.setType("truePositive");
					logFile.setUserName("David");
					logFile.setDeleteDate(new Date());
					files.add(logFile);
				} else if (file.isDirectory()) {
					loglistf(ipAddress, file.getAbsolutePath(), files,"truePositive");
				}
			}
		return files;
	}
	public static void deleteFile(String filePath) {
	File file = new File(filePath);
    
    if(file.delete())
    {
        System.out.println("File deleted successfully");
    }
    else
    {
        System.out.println("Failed to delete the file");
    }
	}
	public static void deleteAllFile(String directoryName) {
		File directory = new File(directoryName);

		// Get all files from a directory.
		File[] fList = directory.listFiles();
		if (fList != null)
			for (File file : fList) {
				ScannedFile scannedFile = new ScannedFile();  
				if (file.isFile()) {
					file.delete();
				} else if (file.isDirectory()) {
					deleteAllFile(file.getAbsolutePath());
				}
			}
	}
	
	
	// Return true if the card number is valid
    public static boolean isValid(long number)
    {
       return (getSize(number) >= 13 &&
               getSize(number) <= 16) &&
               (prefixMatched(number, 4) ||
                prefixMatched(number, 5) ||
                prefixMatched(number, 37) ||
                prefixMatched(number, 6)) &&
              ((sumOfDoubleEvenPlace(number) +
                sumOfOddPlace(number)) % 10 == 0);
    }
 
    // Get the result from Step 2
    public static int sumOfDoubleEvenPlace(long number)
    {
        int sum = 0;
        String num = number + "";
        for (int i = getSize(number) - 2; i >= 0; i -= 2)
            sum += getDigit(Integer.parseInt(num.charAt(i) + "") * 2);
         
        return sum;
    }
 
    // Return this number if it is a single digit, otherwise,
    // return the sum of the two digits
    public static int getDigit(int number)
    {
        if (number < 9)
            return number;
        return number / 10 + number % 10;
    }
 
    // Return sum of odd-place digits in number
    public static int sumOfOddPlace(long number)
    {
        int sum = 0;
        String num = number + "";
        for (int i = getSize(number) - 1; i >= 0; i -= 2)
            sum += Integer.parseInt(num.charAt(i) + "");       
        return sum;
    }
 
    // Return true if the digit d is a prefix for number
    public static boolean prefixMatched(long number, int d)
    {
        return getPrefix(number, getSize(d)) == d;
    }
 
    // Return the number of digits in d
    public static int getSize(long d)
    {
        String num = d + "";
        return num.length();
    }
 
    // Return the first k number of digits from
    // number. If the number of digits in number
    // is less than k, return number.
    public static long getPrefix(long number, int k)
    {
        if (getSize(number) > k) {
            String num = number + "";
            return Long.parseLong(num.substring(0, k));
        }
        return number;
    }
	
    public static boolean isValidCreditCardNumberExcel(String cardNumber)
    {
    	if(isNumeric(cardNumber.trim())) {
			if(cardNumber.length()>=14)
			{
    	// int array for processing the cardNumber
        int[] cardIntArray=new int[cardNumber.length()];
 
        for(int i=0;i<cardNumber.length();i++)
        {
            char c= cardNumber.charAt(i);
            cardIntArray[i]=  Integer.parseInt(""+c);
        }
 
        for(int i=cardIntArray.length-2;i>=0;i=i-2)
        {
            int num = cardIntArray[i];
            num = num * 2;  // step 1
            if(num>9)
            {
                num = num%10 + num/10;  // step 2
            }
            cardIntArray[i]=num;
        }
 
        int sum = sumDigitsExcel(cardIntArray);  // step 3
 
        System.out.println("sum>>>>>>>>>>>>"+sum);
 
        if(sum%10==0)  // step 4
        {
            return true;
        }
			}
			}
 
        return false;
 
    }
 
    public static int sumDigitsExcel(int[] arr)
    {
        return Arrays.stream(arr).sum();
    }
	
}
