package com.dcardprocessing.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
@Controller
public class ScaningController implements Initializable{
	
	@FXML
	private Label label;
	
	@FXML
	private AnchorPane anchorPane;
	
	@FXML
	private RadioButton rbFile;
	
	@FXML
	private RadioButton rbMySql;
	
	@FXML
	private RadioButton rbOracle;
		
	@FXML
	private RadioButton rbIP;
	@FXML
	private ToggleGroup profile;
	
	@FXML
	private TextField serverAddress;
	@FXML
	private TextField serverPort;
	
	@FXML
	private ToggleGroup adminProfile;
	
	@FXML
	private RadioButton rbreviewFile;
	@FXML
	private RadioButton rbreviewIP;
	@FXML
	private RadioButton rbreviewMySql;
	@FXML
	private RadioButton rbreviewOracle;
	
	@FXML
	private TextField dbUser;
	@FXML
	private TextField dbPassword;
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	@FXML
	private void handleButtonAction(ActionEvent event) {
		final DirectoryChooser directoryChooser= new DirectoryChooser();
		File file=directoryChooser.showDialog(null);
		
	}
	
}
