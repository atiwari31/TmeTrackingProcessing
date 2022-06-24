package com.dcardprocessing.controller;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.dcardprocessing.JasyptConfig;
import com.dcardprocessing.bean.User;
import com.dcardprocessing.config.StageManager;
import com.dcardprocessing.service.UserService;
import com.dcardprocessing.util.JSONParserUtility;
import com.dcardprocessing.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;



@Controller
public class LoginController implements Initializable{

	@FXML
    private Button btnLogin;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    private Label lblLogin = new Label();
    
    @Autowired
    private UserService userService;
    
    @Lazy
    @Autowired
    private StageManager stageManager;
    
    private static User userInstance= new User();
    
    
        
	/**
	 * @return the userInstance
	 */
	public static User getUserInstance() {
		return userInstance;
	}

	@FXML
    private void login(ActionEvent event) throws IOException{
		
	   	

	   	if (validate("Email", getUsername(), "[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+")
				&& emptyValidation("Password", getPassword().isEmpty())) {
	   		User user = userService.authenticateUser(getUsername(), getPassword());
	   		if(user==null) {
	   			
	   			stageManager.switchScene(FxmlView.LOGIN);	
	   			lblLogin.setText("Invalid Username/Password.");
	   		}
	   		else if((null!=user) && "Admin".equals(user.getRole()) || "Super".equals(user.getRole())) {
    		userInstance=user;
    		if(user != null && getPassword().equals(user.getPassword())){
    			userInstance.setFirstName(JasyptConfig.decryptKey(user.getFirstName()));
        	   	userInstance.setLastName(JasyptConfig.decryptKey(user.getLastName()));
        	   	userInstance.setEmail(user.getEmail());
        	   	userInstance.setId(user.getId());
    			stageManager.switchScene(FxmlView.ADMIN);
    		}
    		username.setCursor(Cursor.DEFAULT);
    		lblLogin.setText("Invalid Username/Password. Please try again.");
    	}else if(user != null && getPassword().equals(JasyptConfig.decryptKey(user.getPassword()))) {
    		
    		userInstance.setFirstName(JasyptConfig.decryptKey(user.getFirstName()));
    	   	userInstance.setLastName(JasyptConfig.decryptKey(user.getLastName()));
    	   	userInstance.setEmail(user.getEmail());
    		userInstance.setId(user.getId());
    		stageManager.switchScene(FxmlView.USER_FILE_SCAN);	
    	}else {
    		stageManager.switchScene(FxmlView.LOGIN);	
    		lblLogin.setText("Invalid Username/Password. Please try again.");
    	}
	}
   }

	private boolean emptyValidation(String field, boolean empty) {
		if (!empty) {
			return true;
		} else {
			validationAlert(field, true);
			return false;
		}
	}
	
	private boolean validate(String field, String value, String pattern) {
		if (!value.isEmpty()) {
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(value);
			if (m.find() && m.group().equals(value)) {
				return true;
			} else {
				validationAlert(field, false);
				return false;
			}
		} else {
			validationAlert(field, true);
			return false;
		}
	}
	private void validationAlert(String field, boolean empty) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Validation Error");
		alert.setHeaderText(null);
		if (field.equals("Role"))
			alert.setContentText("Please Select " + field);
		else {
			if (empty)
				alert.setContentText("Please Enter " + field);
			else
				alert.setContentText("Please Enter Valid " + field);
		}
		alert.showAndWait();
	}
	
	public String getPassword() {
		return password.getText();
	}

	public String getUsername() {
		return username.getText();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	@FXML
	public void cancel(){
		username.clear();
		password.clear();
		
	}
}
