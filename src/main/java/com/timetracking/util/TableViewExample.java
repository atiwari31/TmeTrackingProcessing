package com.dcardprocessing.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dcardprocessing.JasyptConfig;
import com.dcardprocessing.bean.ScanActivity;
import com.dcardprocessing.service.ScanActivityService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
@Component
public class TableViewExample  {
	
	@Autowired
	private ScanActivityService scanActivityService;

//    public static void excel(String[] args) {
//        launch(args);
//    }

    public void excel() throws IOException {

        TableView<ScanActivity> table = new TableView<ScanActivity>();

        ObservableList<ScanActivity> teamMembers=getLogActivity();  
        table.setItems(teamMembers);

        TableColumn<ScanActivity,String> filePath = new TableColumn<ScanActivity,String>("File Path");
        filePath.setCellValueFactory(new PropertyValueFactory<ScanActivity, String>("scanTypeDetail"));
        TableColumn<ScanActivity,String> fileName = new TableColumn<ScanActivity,String>("File Name");
        fileName.setCellValueFactory(new PropertyValueFactory<ScanActivity, String>("scanDetail"));
        TableColumn<ScanActivity,String> type = new TableColumn<ScanActivity,String>("Type");
        type.setCellValueFactory(new PropertyValueFactory<ScanActivity, String>("type"));
        TableColumn<ScanActivity,String> status = new TableColumn<ScanActivity,String>("Status");
        status.setCellValueFactory(new PropertyValueFactory<ScanActivity, String>("status"));
        
        TableColumn<ScanActivity,String> dateTime = new TableColumn<ScanActivity,String>("DateTime");
        dateTime.setCellValueFactory(new PropertyValueFactory<ScanActivity, String>("timestamp"));
        TableColumn<ScanActivity,String> remarks = new TableColumn<ScanActivity,String>("Remakrs");
        remarks.setCellValueFactory(new PropertyValueFactory<ScanActivity, String>("remarks"));
        
        ObservableList<TableColumn<ScanActivity, ?>> columns = table.getColumns();
        columns.add(filePath);
        columns.add(fileName);
        columns.add(type);
        columns.add(status);
        columns.add(dateTime);
        columns.add(remarks);

        Workbook workbook = new HSSFWorkbook();
        Sheet spreadsheet = workbook.createSheet("Activity");

        Row row = spreadsheet.createRow(0);

        for (int j = 0; j < table.getColumns().size(); j++) {
            row.createCell(j).setCellValue(table.getColumns().get(j).getText());
        }

        for (int i = 0; i < table.getItems().size(); i++) {
            row = spreadsheet.createRow(i + 1);
            for (int j = 0; j < table.getColumns().size(); j++) {
                if(table.getColumns().get(j).getCellData(i) != null) { 
                    row.createCell(j).setCellValue(table.getColumns().get(j).getCellData(i).toString()); 
                }
                else {
                    row.createCell(j).setCellValue("");
                }   
            }
        }
        String home = System.getProperty("user.home");
        FileOutputStream fileOut = new FileOutputStream(home+"/Downloads/Acitivity_Log.xls");
        workbook.write(fileOut);
        fileOut.close();

        //Platform.exit();

    }


    private ObservableList<ScanActivity> getLogActivity() {
    	ObservableList<ScanActivity> scanActivityList = FXCollections.observableArrayList();
    	List<ScanActivity> logActivityDetailDecrList = new ArrayList<ScanActivity>();
    	List<ScanActivity> scanList = new ArrayList<ScanActivity>();
		logActivityDetailDecrList = scanActivityService.findAll();
		ScanActivity scanActivityLocal= null;
		for(ScanActivity scanActivity:logActivityDetailDecrList) {
			scanActivityLocal= new ScanActivity();
			scanActivityLocal.setScanTypeDetail(JasyptConfig.decryptKey(scanActivity.getScanTypeDetail()));
			scanActivityLocal.setScanDetail(JasyptConfig.decryptKey(scanActivity.getScanDetail()));
			if(null!=scanActivity.getScanPath() && !scanActivity.getScanPath().isEmpty()) {
				scanActivityLocal.setScanPath(JasyptConfig.decryptKey(scanActivity.getScanPath()));
			}else {
				scanActivityLocal.setScanPath("");
			}
			
			if(null!=scanActivity.getType() && !scanActivity.getType().isEmpty()) {
				scanActivityLocal.setType(scanActivity.getType());
			}else {
				scanActivityLocal.setType("");
			}
			scanActivityLocal.setStatus(scanActivity.getStatus());
			if(null!=scanActivity.getRemarks() && !scanActivity.getRemarks().isEmpty()) {
			scanActivityLocal.setRemarks(JasyptConfig.decryptKey(scanActivity.getRemarks()));
			}else {
				scanActivityLocal.setRemarks("");
			}
			scanActivityLocal.setTimestamp(scanActivity.getTimestamp());
			scanList.add(scanActivityLocal);
		}
		scanActivityList.addAll(scanList);

        return scanActivityList;
    }

    
}
