package com.dcardprocessing.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;

@Controller
public class ScanScheduleController implements Initializable{

	@FXML
	DatePicker datePicker = new DatePicker();
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	@FXML
	void scanScheduleDate(ActionEvent event) {
		LocalDate localDate=datePicker.getValue();
		
	}

}
