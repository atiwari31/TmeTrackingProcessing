package com.dcardprocessing.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONParserUtility {
	

	
    public static boolean isLicenseValid() 
    {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();
        JSONObject  employeeList=null;
        try (FileReader reader = new FileReader("Asus India.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
 
            employeeList = (JSONObject) obj;
            System.out.println(employeeList);
       
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parseEmployeeObject(employeeList);
    } 
        private static boolean parseEmployeeObject(JSONObject employee) 
        {
            //Get employee object within list
           // JSONObject employeeObject = (JSONObject) employee.get("id");
        	LocalDate currentDate = LocalDate.now();
            //Get employee first name
            Object days = (Object) employee.get("Days");    
            String dt = "2021-03-31";  // Start date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            try {
				c.setTime(sdf.parse(dt));
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            c.add(Calendar.DATE, Integer.valueOf(days.toString()));  // number of days to add
            dt = sdf.format(c.getTime());
           
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
            LocalDate dateTime = LocalDate.parse(dt, formatter);

            if(dateTime.isBefore(currentDate)) {
            	return true;
             }
            return false;
             

        }
}
