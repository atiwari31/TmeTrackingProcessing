package com.dcardprocessing.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;

import com.dcardprocessing.JasyptConfig;
import com.dcardprocessing.bean.LogActivityDetail;
import com.dcardprocessing.bean.LogFile;
import com.dcardprocessing.bean.ScanActivity;
import com.dcardprocessing.bean.ScanSchedule;
import com.dcardprocessing.bean.ScannedDBMySQL;
import com.dcardprocessing.bean.ScannedFile;
import com.dcardprocessing.bean.User;
import com.dcardprocessing.bean.UserScanActivity;
import com.dcardprocessing.config.StageManager;
import com.dcardprocessing.service.IPScanService;
import com.dcardprocessing.service.LogActivityDetailService;
import com.dcardprocessing.service.ScanActivityService;
import com.dcardprocessing.service.ScanScheduleService;
import com.dcardprocessing.service.UserScanActivityService;
import com.dcardprocessing.service.UserService;
import com.dcardprocessing.util.LogActivityEnum;
import com.dcardprocessing.util.RemoteConnection;
import com.dcardprocessing.util.TableViewExample;
import com.dcardprocessing.util.ThreadExecutor;
import com.dcardprocessing.util.Utility;
import com.dcardprocessing.view.FxmlView;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
@Controller
public class UserController implements Initializable {

	@FXML
	private Button btnLogout;

	@FXML
	private Button backToLog;

	@FXML
	private Button btnReview;

	@FXML
	private Label userId;

	@FXML
	private TextField firstName;

	@FXML
	private TextField serverAddress;
	@FXML
	private TextField serverPort;

	@FXML
	private Button btnNext;
	@FXML
	private Button btnBrowse;
	
	@FXML
	private TextField dbUser;
	@FXML
	private TextField dbPassword;

	@FXML
	private TextField dbName;

	@FXML
	private TextField lastName;

	@FXML
	private DatePicker dob;

	@FXML
	private RadioButton rbMale;

	@FXML
	private RadioButton rbFile;

	@FXML
	private RadioButton rbMySql;
	
	
	@FXML
	private RadioButton rbScanFile;

	@FXML
	private RadioButton rbScanMySql;
	
	@FXML
	private RadioButton rbScanIP;

	@FXML
	private RadioButton rbOracle;

	@FXML
	private RadioButton rbIP;
	@FXML
	private RadioButton rbreviewFile;

	@FXML
	private RadioButton rbreviewIP;
	@FXML
	private RadioButton rbreviewMySql;
	@FXML
	private RadioButton rbreviewOracle;

	@FXML
	private ToggleGroup profile;

	@FXML
	private ToggleGroup adminProfile;

	@FXML
	private ToggleGroup gender;
	@FXML
	private ToggleGroup toggleGroup1;
	@FXML
	private RadioButton radioButtonfile;
	@FXML
	private RadioButton radioButtonip;

	@FXML
	private RadioButton radioButtonMySQL;

	@FXML
	private RadioButton radioButtonOracle;

	@FXML
	private RadioButton rbFemale;

	@FXML
	private ComboBox<String> cbRole;

	@FXML
	private TextField email;

	@FXML
	private TextField ipAddressTo;

	@FXML
	private TextField ipAddressFrom;
	@FXML
	private TextField scanTime;
	
	@FXML
	private TextField selectFile;

	@FXML
	private PasswordField password;

	@FXML
	private Button reset;

	@FXML
	private Button saveUser;

	@FXML
	private Button btnApproveFiles;

	@FXML
	private Button btnSaveUserRemark;
	@FXML
	private Button btnSaveRemarkSQL;

	@FXML
	private Button scanFiles;

	@FXML
	private Button scanDBMySQL;

	@FXML
	private Button connectUser;
	
	@FXML
	private Button scanStop;
	

	@FXML
	private TableView<User> userTable;

	@FXML
	private TableView<ScanActivity> scanUserFileTable;
	
	@FXML
	private TableView<ScanSchedule> scanScheduleTable;
	
	@FXML
	private TableColumn<ScanSchedule, String> colScanScheduleDate;
	
	@FXML
	private TableColumn<ScanSchedule, String> colScanCreatedDate;
	
	@FXML
	private TableColumn<ScanSchedule, Integer> scanScheduleId;
	
	@FXML
	private TableColumn<ScanSchedule, String> scanUserName;
	
	@FXML
	private TableColumn<ScanSchedule, String> colScanScheduleType;
	
	@FXML
	private TableColumn<ScanSchedule, CheckBox> scanInActiveBox;
	
	@FXML
	private TableView<ScanActivity> scanUserIPFileTable;
	
	@FXML
	private TableView<ScanActivity> scanActivityTable;
	
	@FXML
	private TableView<ScanActivity> scanActivityTableIPAdmin;

	@FXML
	private TableView<ScanActivity> scanLogTable;

	@FXML
	private TableView<ScannedFile> scanAdminFileTable;

	@FXML
	private TableView<ScanActivity> scanMySQLTable;

	@FXML
	private TableView<LogActivityDetail> logFileTable;

	@FXML
	private TableColumn<User, Long> colUserId;

	@FXML
	private TableColumn<User, String> colFirstName;

	@FXML
	private TableColumn<ScanActivity, String> colIpAddress;
	
	@FXML
	private TableColumn<ScanActivity, String> colIPStatus;
	@FXML
	private TableColumn<ScanActivity, String> colIPTimeStamp;

	@FXML
	private TableColumn<ScanActivity, String> colscanDetail;

	@FXML
	private TableColumn<ScanActivity, String> colScanTypeDetail;
	@FXML
	private TableColumn<ScanActivity, String> colScanDetail;

	@FXML
	private TableColumn<ScanActivity, String> colStatus;

	@FXML
	private TableColumn<ScanActivity, String> colScanType;
	
	@FXML
	private TableColumn<ScanActivity, String> colIPScanType;

	@FXML
	private TableColumn<ScannedDBMySQL, String> colServerAddress;

	@FXML
	private TableColumn<ScanActivity, Long> colId;

	@FXML
	private TableColumn<ScanActivity, String> colAdminSelectFileCB;

	@FXML
	private TableColumn<ScanActivity, String> colLogScanTypeDetail;
	@FXML
	private TableColumn<ScanActivity, String> colLogScanDetail;
	@FXML
	private TableColumn<ScanActivity, String> colLogScanType;
	@FXML
	private TableColumn<ScanActivity, String> colLogStatus;

	@FXML
	private TableColumn<ScanActivity, String> colSelectFileCB;
    
	@FXML
	private TableColumn<ScanActivity, String> colComboFile;
	
	@FXML
	private TableColumn<ScanActivity, Long> colScanId;

	@FXML
	private TableColumn<ScannedDBMySQL, String> colSelectMySQLCB;

	@FXML
	private TableColumn<ScanActivity, String> colType;

	@FXML
	private TableColumn<ScanActivity, String> colRemarks;
	
	@FXML
	private TableColumn<ScanActivity, String> colScanDate;

	@FXML
	private TableColumn<ScannedDBMySQL, String> colRemarksSQL;

	@FXML
	private TableColumn<ScanActivity, String> colAdminRemarks;
	
	@FXML
	private TableColumn<ScanActivity, String> colAdminRemarksText;
	
	@FXML
	private TableColumn<ScanActivity, String> colUserRemarks;

	@FXML
	private TableColumn<ScannedDBMySQL, String> colMySQLType;
	
	@FXML
	private TableColumn<ScannedDBMySQL, String> colMySQLStatus;
	
	@FXML
	private TableColumn<ScannedDBMySQL, Date> colMySQLScanDate;

	@FXML
	private TableColumn<LogActivityDetail, String> colFilePathLog;

	@FXML
	private TableColumn<LogActivityDetail, String> colActivityNameLog;

	@FXML
	private TableColumn<ScanActivity, Date> colDateTime;

	@FXML
	private TableColumn<LogActivityDetail, Date> colActivityDateLog;

	@FXML
	private TableColumn<LogActivityDetail, String> colUserNameLog;

	@FXML
	private TableColumn<LogFile, String> colFileName;
	@FXML
	private TableColumn<ScannedDBMySQL, String> colTableName;
	@FXML
	private TableColumn<User, String> colLastName;

	@FXML
	private TableColumn<User, LocalDate> colDOB;

	@FXML
	private TableColumn<User, String> colGender;

	@FXML
	private TableColumn<User, String> colRole;

	@FXML
	private TableColumn<User, String> colEmail;

	@FXML
	private TableColumn<User, Boolean> colEdit;
	@FXML
	CheckBox cbRemarksAll = new CheckBox(); 
	@FXML
	CheckBox cbRemarksAllMYSQL = new CheckBox(); 
	
	@FXML
	CheckBox cbRemarksAllAdminMYSQL = new CheckBox(); 
	
	/*** IP ADMIN Attribute ***/
	@FXML
	private TableColumn<ScanActivity, String> colAdminSelectFileCBIP;
	@FXML
	private TableColumn<ScanActivity, String> colScanTypeDetailIP;

	@FXML
	private TableColumn<ScanActivity, String> colScanDetailIP;
	
	@FXML
	private TableColumn<ScanActivity, String> colScanTypeIP;
	
	@FXML
	private TableColumn<ScanActivity, String> colUserRemarksIP;
	
	@FXML
	private TableColumn<ScanActivity, String> colAdminRemarksIP;
	@FXML
	private TableColumn<ScanActivity, String> scanPath;
	
	
	/**** End Here *****/
	
	/*** MYSQL admin log review ****/
	@FXML
	private TableView<ScanActivity> scanActivityTableMYSQLAdmin;
	@FXML
	private TableColumn<ScanActivity, String> colAdminSelectFileCBMYSQL;
	@FXML
	private TableColumn<ScanActivity, String> colScanTypeDetailMYSQL;
	@FXML
	private TableColumn<ScanActivity, String> colScanDetailMYSQL;
	@FXML
	private TableColumn<ScanActivity, String> colScanTypeMYSQL;
	@FXML
	private TableColumn<ScanActivity, String> colUserRemarksMYSQL;
	@FXML
	private TableColumn<ScanActivity, String> colAdminRemarksMYSQL;
	
	/****** END  ******/
	
	
	@FXML
	private MenuItem deleteUsers;

	@FXML
	private AnchorPane content;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private UserService userService;
	
	@Autowired
	private ScanScheduleService scanScheduleService;
	
	@Autowired
	private IPScanService iPScanService;

	@Autowired
	private LogActivityDetailService logActivityDetailService;
	@Autowired
	private ScanActivityService scanActivityService;
	@Autowired
	private UserScanActivityService userScanActivityService;

	private ObservableList<User> userList = FXCollections.observableArrayList();
	private ObservableList<ScanActivity> scannedFileList = FXCollections.observableArrayList();
	private ObservableList<ScanActivity> scannedFileListIP = FXCollections.observableArrayList();
	private ObservableList<ScanActivity> scannedAdminFileList = FXCollections.observableArrayList();

	private ObservableList<ScanActivity> scannedMySQLList = FXCollections.observableArrayList();
	private ObservableList<ScanActivity> scannedIPListAdmin = FXCollections.observableArrayList();
	
	private ObservableList<ScanActivity> scannedMYSQLListAdmin = FXCollections.observableArrayList();
	
	private ObservableList<String> roles = FXCollections.observableArrayList("User", "Admin");
	private ObservableList<LogActivityDetail> logFileList = FXCollections.observableArrayList();
	private ObservableList<ScanSchedule> scanScheduleList = FXCollections.observableArrayList();
	
	@Autowired
	private ThreadExecutor threadExecutor;
	@Autowired
	private RemoteConnection remoteConnection;
	/*** IP address Filed Start here **/
	@FXML
	DatePicker datePicker = new DatePicker();
	
	
	@FXML
	private TextField ipAddressText;
	@FXML
	private TextField toAddressScanText;
	@FXML
	private TextField userNameIPText;
	@FXML
	private TextField passwordIPText;
	@Autowired
	private IPScanService scanService;
	@FXML
	private ToggleGroup toggleFilescan;
	@FXML
	private RadioButton rbDirectoryScan;
	@FXML
	private RadioButton rbFileScan;
	@FXML
	ProgressIndicator progress = new ProgressIndicator();
	
	@FXML
	private Button btnScanSchedule;
	@Autowired
	private TableViewExample tableViewExample;
	
	
	/** Remote Connection Variable **/
	@FXML
	private TextField  fromIpAddressText;
	@FXML
	private TextField toIpAddressText;
	@FXML
	private TextField remoteUserNameText;
	@FXML
	private TextField remotePasswordText;
	
	/** Remote Connection End here **/
	
	/*** IP address Filed End here **/
	
	@FXML
	private void exit(ActionEvent event) {
		Platform.exit();
	}

	/**
	 * Logout and go to the login page
	 */
	@FXML
	private void logout(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.LOGIN);
	}

	@FXML
	private void getUserDetail(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.ADMIN);
	}
	@FXML
	private void home(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.USER_FILE_SCAN);
		rbFile.setSelected(true);
		loadUserScannedFiles();
	}
	
	@FXML
	private void handleRadio(ActionEvent event) throws IOException {

		if (rbFile.isSelected()) {
			stageManager.switchScene(FxmlView.USER_FILE_SCAN);
			rbFile.setSelected(true);
			loadUserScannedFiles();
		}
		if (rbMySql.isSelected()) {
			stageManager.switchScene(FxmlView.MYSQL);
			rbMySql.setSelected(true);
			loadUserScannedSQL();
		}
	
		if (rbIP.isSelected()) {
			stageManager.switchScene(FxmlView.IP);
			rbIP.setSelected(true);
			loadUserScannedIPFiles();
		}
	}
	@FXML
	private void ipActionView() {
		stageManager.switchScene(FxmlView.IP);
		rbIP.setSelected(true);
		loadUserScannedIPFiles();
	}
	@FXML
	private void excelReport() throws IOException {
		
		tableViewExample.excel();
		alert(null, "Report Generated", "Report Generated Succesfully in Download Folder");
	}
	@FXML
	private void handleReviewRadio(ActionEvent event) throws IOException {

		if (radioButtonfile.isSelected()) {
			stageManager.switchScene(FxmlView.REVIEW_FILE_SCAN);
		}
		if (radioButtonMySQL.isSelected()) {
			stageManager.switchScene(FxmlView.REVIEW_MYSQL);
		}

	}

	@FXML
	private void handleButtonAction(ActionEvent event) {
		Stage stage = (Stage) content.getScene().getWindow();
		if(rbFileScan.isSelected()) {
			FileChooser fileChooser = new FileChooser();
			stage.setTitle("JavaFX App");
			 //Button browse = new Button("Select File");
			btnBrowse.setOnAction(e -> {
		            File selectedFile = fileChooser.showOpenDialog(stage);
		            selectFile.setText(selectedFile.getAbsolutePath());
		        });
			
		}else {
			final DirectoryChooser directoryChooser = new DirectoryChooser();
			File file = directoryChooser.showDialog(stage);
			if (file != null) {
				System.out.println("file>>>>>>>>>>>" + file.getAbsolutePath());
				selectFile.setText(file.getAbsolutePath());
			}
		}
		
	}
	@FXML
	private void handleRadioBrowse(ActionEvent event) throws IOException {
		selectFile.setText(null);
	}
	@FXML
	private void deleteAllFileIP() {
		
		
		String filePath = null;
		String remarksValue = null;

		ObservableList<ScanActivity> data = scanActivityTableIPAdmin.getItems();
		/*********************/
		ScanActivity scanActivityLocal = null;
		
		for (ScanActivity item : data) {

			if (item.getSelect().isSelected() == true) {

				if (item.getRemarks() == null) {
					remarksValue = "";

				} else {

					remarksValue = item.getRemarks().toString();
				}

				scanActivityLocal = new ScanActivity();
				iPScanService.remoteConnectionDeleteFile(item.getId());
				scanActivityLocal.setId(item.getId());
				scanActivityLocal.setScanTypeId(item.getScanTypeId());
				scanActivityLocal.setScanTypeDetail(JasyptConfig.encryptKey(item.getScanTypeDetail().toString()));
				scanActivityLocal.setScanDetail(JasyptConfig.encryptKey(item.getScanDetail().toString()));
				//scanActivityLocal.setScanPath(JasyptConfig.encryptKey(item.getScanPath().toString()));
				scanActivityLocal.setTimestamp(new Date());
				scanActivityLocal.setIsDeleted("false");
				if(null!=item.getComboBox().getSelectionModel().getSelectedItem()) {
					scanActivityLocal.setType(item.getComboBox().getSelectionModel().getSelectedItem().toString());
				}else {
					scanActivityLocal.setType(null);
				}
				ScanActivity scanActivity=scanActivityService.find(item.getId());
				if(null!=scanActivity.getAdminRemarks()) {
					scanActivityLocal.setAdminRemarks(scanActivity.getAdminRemarks());
				}
				scanActivityLocal.setStatus("REQUEST FOR APPROVAL");
				scanActivityLocal.setCurrentStatus("DELETED");
				scanActivityLocal.setUserScanActivityId(item.getUserScanActivityId());

				scanActivityService.save(scanActivityLocal);
			}
		}
		/**************************/
		ScanActivity scanActivity = null;
		Boolean deleteFlag=false;
		for (ScanActivity item1 : data) {

			if (item1.getSelect().isSelected() == true) {

				if (item1.getRemarks() == null) {
					remarksValue = "";

				} else {

					remarksValue = item1.getRemarks().toString();
				}

				scanActivity = new ScanActivity();
			    //scanActivity.setId(item1.getId());
				scanActivity.setScanTypeId(item1.getScanTypeId());
				scanActivity.setScanTypeDetail(JasyptConfig.encryptKey(item1.getScanTypeDetail().toString()));
				scanActivity.setScanDetail(JasyptConfig.encryptKey(item1.getScanDetail().toString()));
				//scanActivity.setScanPath(JasyptConfig.encryptKey(item1.getScanPath().toString()));
				scanActivity.setTimestamp(new Date());
				scanActivity.setIsDeleted("true");
				if(null!=item1.getComboBox().getSelectionModel().getSelectedItem()) {
					scanActivity.setType(item1.getComboBox().getSelectionModel().getSelectedItem().toString());
				}else {
					scanActivity.setType(null);
				}
				if(null!=item1.getRemarks()) {
					scanActivity.setRemarks(JasyptConfig.encryptKey(item1.getRemarks().toString()));
				}
				scanActivity.setStatus("DELETED");
				scanActivity.setUserScanActivityId(item1.getUserScanActivityId());
				if(null!=item1.getAdminremarks().getText()) {
					scanActivity.setAdminRemarks(JasyptConfig.encryptKey(item1.getAdminremarks().getText().toString()));
					}
				scanActivityService.save(scanActivity);
				//filePath = item1.getScanPath();
				filePath = item1.getScanTypeDetail().toString()+item1.getScanDetail().toString();
				System.out.println("Deleted filename are:" + filePath);
				Utility.deleteFile(filePath);

				LogActivityDetail logActivityDetail = new LogActivityDetail();
				logActivityDetail.setActivityName(LogActivityEnum.DELETE.toString());
				logActivityDetail.setActivityDate(new Date());
				logActivityDetail.setScanActivityId((int) item1.getId());
				scanActivityService.updateByUserScanActivityId(item1.getUserScanActivityId());
				isDeletedUpdate(item1.getId());
				deleteFlag=true;

			}
		}
		if(deleteFlag) {
			alert(null, "Files deleted!", "File deleted successfully!");
			ActionEvent event = null;
			 reviewIP(event);
			
		}else {
			alert(null, "Files deleted!", "Please select file");
		}
		
		
	}

	@FXML
	private void deleteAllFile() {
		
		
		String filePath = null;
		String remarksValue = null;

		List<ScanActivity> scannedfiles = scanActivityTable.getSelectionModel().getSelectedItems();
		ObservableList<ScanActivity> data = scanActivityTable.getItems();
		/*********************/
		ScanActivity scanActivityLocal = null;
		
		for (ScanActivity item : data) {

			if (item.getSelect().isSelected() == true) {

				if (item.getRemarks() == null) {
					remarksValue = "";

				} else {

					remarksValue = item.getRemarks().toString();
				}

				scanActivityLocal = new ScanActivity();
				scanActivityLocal.setId(item.getId());
				scanActivityLocal.setScanTypeId(item.getScanTypeId());
				scanActivityLocal.setScanTypeDetail(JasyptConfig.encryptKey(item.getScanTypeDetail().toString()));
				scanActivityLocal.setScanDetail(JasyptConfig.encryptKey(item.getScanDetail().toString()));
				scanActivityLocal.setScanPath(JasyptConfig.encryptKey(item.getScanPath().toString()));
				scanActivityLocal.setTimestamp(new Date());
				scanActivityLocal.setIsDeleted("false");
				if(null!=item.getComboBox().getSelectionModel().getSelectedItem()) {
					scanActivityLocal.setType(item.getComboBox().getSelectionModel().getSelectedItem().toString());
				}else {
					scanActivityLocal.setType(null);
				}
				ScanActivity scanActivity=scanActivityService.find(item.getId());
				if(null!=scanActivity.getAdminRemarks()) {
					scanActivityLocal.setAdminRemarks(scanActivity.getAdminRemarks());
				}
				scanActivityLocal.setStatus("REQUEST FOR APPROVAL");
				scanActivityLocal.setCurrentStatus("DELETED");
				scanActivityLocal.setUserScanActivityId(item.getUserScanActivityId());

				scanActivityService.save(scanActivityLocal);
			}
		}
		/**************************/
		ScanActivity scanActivity = null;
		Boolean deleteFlag=false;
		for (ScanActivity item1 : data) {

			if (item1.getSelect().isSelected() == true) {

				if (item1.getRemarks() == null) {
					remarksValue = "";

				} else {

					remarksValue = item1.getRemarks().toString();
				}

				scanActivity = new ScanActivity();
			    //scanActivity.setId(item1.getId());
				scanActivity.setScanTypeId(item1.getScanTypeId());
				scanActivity.setScanTypeDetail(JasyptConfig.encryptKey(item1.getScanTypeDetail().toString()));
				scanActivity.setScanDetail(JasyptConfig.encryptKey(item1.getScanDetail().toString()));
				scanActivity.setScanPath(JasyptConfig.encryptKey(item1.getScanPath().toString()));
				scanActivity.setTimestamp(new Date());
				scanActivity.setIsDeleted("true");
				if(null!=item1.getComboBox().getSelectionModel().getSelectedItem()) {
					scanActivity.setType(item1.getComboBox().getSelectionModel().getSelectedItem().toString());
				}else {
					scanActivity.setType(null);
				}
				if(null!=item1.getRemarks()) {
					scanActivity.setRemarks(JasyptConfig.encryptKey(item1.getRemarks().toString()));
				}
				scanActivity.setStatus("DELETED");
				scanActivity.setUserScanActivityId(item1.getUserScanActivityId());
				if(null!=item1.getAdminremarks().getText()) {
					scanActivity.setAdminRemarks(JasyptConfig.encryptKey(item1.getAdminremarks().getText().toString()));
					}
				scanActivityService.save(scanActivity);
				filePath = item1.getScanPath();
				System.out.println("Deleted filename are:" + filePath);
				Utility.deleteFile(filePath);

				LogActivityDetail logActivityDetail = new LogActivityDetail();
				logActivityDetail.setActivityName(LogActivityEnum.DELETE.toString());
				logActivityDetail.setActivityDate(new Date());
				logActivityDetail.setScanActivityId((int) item1.getId());
				scanActivityService.updateByUserScanActivityId(item1.getUserScanActivityId());
				isDeletedUpdate(item1.getId());
				deleteFlag=true;

			}
		}
		if(deleteFlag) {
			alert(null, "Files deleted!", "File deleted successfully!");
			ActionEvent event = null;
		
				reviewFile(event);
			
		}else {
			alert(null, "Files deleted!", "Please select file");
		}
		
		
	}
	
	

	private void isDeletedUpdate(Long id) {
		ScanActivity scanActivity=scanActivityService.find(id);
		scanActivity.setIsDeleted("false");
		scanActivityService.save(scanActivity);
	}
	
	@FXML
	void reset(ActionEvent event) {
		clearFields();
	}

	@FXML
	void approveFiles(ActionEvent event) {
		Boolean checkFlag=false;
		scanUserFileTable.setEditable(true);

		List<ScanActivity> scannedfiles = scanUserFileTable.getSelectionModel().getSelectedItems();
		ObservableList<ScanActivity> data = scanUserFileTable.getItems();
		ScanActivity scanActivity = null;
		ScanActivity scanActivityUpdated = null;
		
		for (ScanActivity item : data) {
			if (item.getSelect().isSelected() == true) {
				scanActivity = new ScanActivity();
				// scanActivity.setId(item.getId());
				scanActivity.setScanTypeId(2);
				scanActivity.setScanTypeDetail(JasyptConfig.encryptKey(item.getScanTypeDetail().toString()));
				scanActivity.setScanDetail(JasyptConfig.encryptKey(item.getScanDetail().toString()));
				scanActivity.setScanPath(JasyptConfig.encryptKey(item.getScanPath().toString()));
				scanActivity.setTimestamp(new Date());
				scanActivity.setIsDeleted("true");
				if(null!=item.getComboBox().getSelectionModel().getSelectedItem()) {
				scanActivity.setType(item.getComboBox().getSelectionModel().getSelectedItem().toString());
				}else {
					scanActivity.setType(null);
				}
				scanActivity.setRemarks(JasyptConfig.encryptKey(item.getUserremarks().getText()));
				scanActivity.setStatus("REQUEST FOR APPROVAL");
				scanActivity.setUserScanActivityId(item.getUserScanActivityId());
				scanActivityService.save(scanActivity);
				scanActivityUpdated = new ScanActivity();
				scanActivityUpdated=scanActivity;
				scanActivity.setIsDeleted("false");
				scanActivityUpdated.setStatus("SCAN");
				scanActivityUpdated.setId(item.getId());
				scanActivityUpdated.setRemarks(JasyptConfig.encryptKey(item.getUserremarks().getText()));
				scanActivityUpdated.setCurrentStatus("REQUEST FOR APPROVAL");
				scanActivityService.save(scanActivityUpdated);
				checkFlag=true;
				
				
			}
		}
		if(checkFlag) {
			alert(null, "Files Approves", "Send Files Succesfully For Approval");
			
		}else {
			alert(null, "Files Approves", "Please select file");
		}
		LogActivityDetail logActivityDetail = new LogActivityDetail();
		logActivityDetail.setActivityName(LogActivityEnum.REQUEST.toString());
		logActivityDetail.setActivityDate(new Date());
		loadUserScannedFiles();
		
	}
	@FXML
	void approveFilesIP(ActionEvent event) {
		
		Boolean checkFlag=false;
		scanUserIPFileTable.setEditable(true);

		List<ScanActivity> scannedfiles = scanUserIPFileTable.getSelectionModel().getSelectedItems();
		ObservableList<ScanActivity> data = scanUserIPFileTable.getItems();
		ScanActivity scanActivity = null;
		ScanActivity scanActivityUpdated = null;
		
		for (ScanActivity item : data) {
			if (item.getSelect().isSelected() == true) {
				scanActivity = new ScanActivity();
				// scanActivity.setId(item.getId());
				ScanActivity scanActivityLocal=scanActivityService.find(item.getId());
				scanActivity.setScanTypeId(1);
				scanActivity.setScanTypeDetail(JasyptConfig.encryptKey(item.getScanTypeDetail().toString()));
				scanActivity.setScanDetail(JasyptConfig.encryptKey(item.getScanDetail().toString()));
				scanActivity.setDbUserName(scanActivityLocal.getDbUserName());
				scanActivity.setDbPassword(scanActivityLocal.getDbPassword());
				scanActivity.setIpAddress(scanActivityLocal.getIpAddress());
				//scanActivity.setScanPath(JasyptConfig.encryptKey(item.getScanPath().toString()));
				scanActivity.setTimestamp(new Date());
				scanActivity.setIsDeleted("true");
				if(null!=item.getComboBox().getSelectionModel().getSelectedItem()) {
				scanActivity.setType(item.getComboBox().getSelectionModel().getSelectedItem().toString());
				}else {
					scanActivity.setType(null);
				}
				scanActivity.setRemarks(JasyptConfig.encryptKey(item.getUserremarks().getText()));
				scanActivity.setStatus("REQUEST FOR APPROVAL");
				scanActivity.setUserScanActivityId(item.getUserScanActivityId());
				scanActivityService.save(scanActivity);
				scanActivityUpdated = new ScanActivity();
				scanActivityUpdated=scanActivity;
				scanActivity.setIsDeleted("false");
				scanActivityUpdated.setStatus("SCAN");
				scanActivityUpdated.setId(item.getId());
				scanActivityUpdated.setRemarks(JasyptConfig.encryptKey(item.getUserremarks().getText()));
				scanActivityUpdated.setCurrentStatus("REQUEST FOR APPROVAL");
				scanActivityService.save(scanActivityUpdated);
				checkFlag=true;
				
				
			}
		}
		if(checkFlag) {
			alert(null, "Files Approves", "Send Files Succesfully For Approval");
			
			
		}else {
			alert(null, "Files Approves", "Please select file");
		}
		LogActivityDetail logActivityDetail = new LogActivityDetail();
		logActivityDetail.setActivityName(LogActivityEnum.REQUEST.toString());
		logActivityDetail.setActivityDate(new Date());
		loadUserScannedIPFiles();
		
	}

	
	
	
	@FXML
	void saveAdminRemark(ActionEvent event) {
		Boolean checkFlag=false;
		scanActivityTable.setEditable(true);
		ObservableList<ScanActivity> data = scanActivityTable.getItems();
		ScanActivity scanActivity = null;
		for (ScanActivity item : data) {
			scanActivity = scanActivityService.find(item.getId());
			if(cbRemarksAll.isSelected() && scanActivity.getStatus().equalsIgnoreCase("REQUEST FOR APPROVAL")) {
				saveAdminRemarkAll(event);
				checkFlag=true;
				break;
			}else if (item.getSelect().isSelected() == true) {
				scanActivity = new ScanActivity();
				scanActivity = scanActivityService.find(item.getId());
				//User Remarks
				if(null!=item.getComboBox().getSelectionModel().getSelectedItem()) {
					scanActivity.setType(item.getComboBox().getSelectionModel().getSelectedItem().toString());
				}else {
					scanActivity.setType(null);
				}
				scanActivity.setAdminRemarks(JasyptConfig.encryptKey(item.getAdminremarks().getText()));
				scanActivityService.save(scanActivity);
				checkFlag=true;
			}
			
		}
		if(checkFlag) {
			alert(null, "Save Record!", "Save Record successfully!");
			
		}else {
			alert(null, "Files Approves", "Please select file");
		}
				
	}
	
	@FXML
	void saveAdminRemarkIP(ActionEvent event) {
		Boolean checkFlag=false;
		scanActivityTableIPAdmin.setEditable(true);
		ObservableList<ScanActivity> data = scanActivityTableIPAdmin.getItems();
		ScanActivity scanActivity = null;
		for (ScanActivity item : data) {
			scanActivity = scanActivityService.find(item.getId());
			if(cbRemarksAll.isSelected() && scanActivity.getStatus().equalsIgnoreCase("REQUEST FOR APPROVAL")) {
				saveAdminRemarkAllIP(event);
				checkFlag=true;
				break;
			}else if (item.getSelect().isSelected() == true) {
				scanActivity = new ScanActivity();
				scanActivity = scanActivityService.find(item.getId());
				//User Remarks
				if(null!=item.getComboBox().getSelectionModel().getSelectedItem()) {
					scanActivity.setType(item.getComboBox().getSelectionModel().getSelectedItem().toString());
				}else {
					scanActivity.setType(null);
				}
				scanActivity.setAdminRemarks(JasyptConfig.encryptKey(item.getAdminremarks().getText()));
				scanActivityService.save(scanActivity);
				checkFlag=true;
			}
			
		}
		if(checkFlag) {
			alert(null, "Save Record!", "Save Record successfully!");
			
		}else {
			alert(null, "Files Approves", "Please select file");
		}
				
	}

	@FXML
	void saveUserRemark(ActionEvent event) {
	
		scanUserFileTable.setEditable(true);
		ObservableList<ScanActivity> data = scanUserFileTable.getItems();
		ScanActivity scanActivity = null;

			
	boolean isSelected=false;
		for (ScanActivity item : data) {
			if(item.getSelect().isSelected()) {
			if(cbRemarksAll.isSelected() && item.getStatus().equalsIgnoreCase("Scan")) {
				saveUserRemarkAll(event);
				alert(null, "Save Remarks!", "Saved Successfully!");
				break;
			}
			
			else if (item.getSelect().isSelected() == true) {
				scanActivity = new ScanActivity();
				scanActivity = scanActivityService.find(item.getId());
				//User Remarks
				if(null!=item.getComboBox().getSelectionModel().getSelectedItem()) {
					scanActivity.setType(item.getComboBox().getSelectionModel().getSelectedItem().toString());
				}else {
					scanActivity.setType(null);
				}
				scanActivity.setRemarks(JasyptConfig.encryptKey(item.getUserremarks().getText()));
				scanActivityService.save(scanActivity);
				alert(null, "Save Remarks!", "Saved Successfully!");
			}
			isSelected=true;
		}
		}
		if(!isSelected) {
			alert(null, "Select Record!", "Please Select Record!");
		}
		loadUserScannedFiles();
	}
	
	@FXML
	void saveUserRemarkAll(ActionEvent event) {
		scanUserFileTable.setEditable(true);
		List<ScanActivity> scannedfiles = scanUserFileTable.getSelectionModel().getSelectedItems();
		ObservableList<ScanActivity> data = scanUserFileTable.getItems();
		ScanActivity scanActivity = null;
		
		String remakrsAll=null;
		for (ScanActivity item : data) {
			if(null!=item.getUserremarks().getText() && item.getStatus().equalsIgnoreCase("Scan")) {
				remakrsAll =item.getUserremarks().getText();
				break;
			}
			}
		for (ScanActivity item : data) {
				scanActivity = new ScanActivity();
				scanActivity = scanActivityService.find(item.getId());
				//User Remarks
				if(null!=item.getComboBox().getSelectionModel().getSelectedItem())
				scanActivity.setType(item.getComboBox().getSelectionModel().getSelectedItem().toString());
				if(item.getStatus().equalsIgnoreCase("Scan")) {
				 scanActivity.setRemarks(JasyptConfig.encryptKey(remakrsAll));
				}
				scanActivityService.save(scanActivity);
				
			}
	}

	@FXML
	void saveAdminRemarkAll(ActionEvent event) {
		scanActivityTable.setEditable(true);
		ObservableList<ScanActivity> data = scanActivityTable.getItems();
		ScanActivity scanActivity = null;
		
		String remakrsAll=null;
		for (ScanActivity item : data) {
			scanActivity = new ScanActivity();
			scanActivity = scanActivityService.find(item.getId());
			if(null!=scanActivity.getStatus() && scanActivity.getStatus().equalsIgnoreCase("REQUEST FOR APPROVAL")) {
				remakrsAll =item.getAdminremarks().getText();
				break;
			}
			}
		for (ScanActivity item : data) {
				scanActivity = new ScanActivity();
				scanActivity = scanActivityService.find(item.getId());
				//User Remarks
				if(null!=item.getComboBox().getSelectionModel().getSelectedItem())
				scanActivity.setType(item.getComboBox().getSelectionModel().getSelectedItem().toString());
				if(scanActivity.getStatus().equalsIgnoreCase("REQUEST FOR APPROVAL")) {
				 scanActivity.setAdminRemarks(JasyptConfig.encryptKey(remakrsAll));
				}
				scanActivityService.save(scanActivity);
			}
		loadAdminScannedFiles();		
		//alert(null, "Save Remarks!", "Remarks added successfully!");

	}
	@FXML
	void saveAdminRemarkAllIP(ActionEvent event) {
		scanActivityTable.setEditable(true);
		ObservableList<ScanActivity> data = scanActivityTableIPAdmin.getItems();
		ScanActivity scanActivity = null;
		
		String remakrsAll=null;
		for (ScanActivity item : data) {
			scanActivity = new ScanActivity();
			scanActivity = scanActivityService.find(item.getId());
			if(null!=scanActivity.getStatus() && scanActivity.getStatus().equalsIgnoreCase("REQUEST FOR APPROVAL")) {
				remakrsAll =item.getAdminremarks().getText();
				break;
			}
			}
		for (ScanActivity item : data) {
				scanActivity = new ScanActivity();
				scanActivity = scanActivityService.find(item.getId());
				//User Remarks
				if(null!=item.getComboBox().getSelectionModel().getSelectedItem())
				scanActivity.setType(item.getComboBox().getSelectionModel().getSelectedItem().toString());
				if(scanActivity.getStatus().equalsIgnoreCase("REQUEST FOR APPROVAL")) {
				 scanActivity.setAdminRemarks(JasyptConfig.encryptKey(remakrsAll));
				}
				scanActivityService.save(scanActivity);
			}
		loadAdminReviewIP();		
	}

	@FXML
	void saveUserRemarkSQL(ActionEvent event) {

		scanMySQLTable.setEditable(true);
		List<ScanActivity> scannedDBMySQL = scanMySQLTable.getSelectionModel().getSelectedItems();
		ObservableList<ScanActivity> data = scanMySQLTable.getItems();
		ScanActivity scanActivity = null;
		for (ScanActivity item : data) {
			if (item.getSelect().isSelected() == true) {
				scanActivity = new ScanActivity();
				scanActivity.setId(item.getId());
				scanActivity.setScanTypeId(3);
				scanActivity.setScanTypeDetail(JasyptConfig.encryptKey(item.getScanTypeDetail()));
				scanActivity.setScanDetail(JasyptConfig.encryptKey(item.getScanDetail()));
				scanActivity.setType(item.getType());
				scanActivity.setIsDeleted(item.getIsDeleted());
				scanActivity.setTimestamp(new Date());
				scanActivity.setRemarks(JasyptConfig.encryptKey(item.getUserremarks().getText()));
				scanActivity.setUserScanActivityId(item.getUserScanActivityId());
				scanActivity.setStatus(item.getStatus());
				scanActivityService.save(scanActivity);

			}
		}
		alert(null, "Save Remarks!", "Remarks added successfully!");
	}

	@FXML
	void approveFilesMYSQL(ActionEvent event) {
		
		Boolean checkFlag=false;
		scanMySQLTable.setEditable(true);

		List<ScanActivity> scannedfiles = scanMySQLTable.getSelectionModel().getSelectedItems();
		ObservableList<ScanActivity> data = scanMySQLTable.getItems();
		ScanActivity scanActivity = null;
		ScanActivity scanActivityUpdated = null;
		
		for (ScanActivity item : data) {
			if (item.getSelect().isSelected() == true) {
				scanActivity = new ScanActivity();
				// scanActivity.setId(item.getId());
				scanActivity.setScanTypeId(3);
				scanActivity.setScanTypeDetail(JasyptConfig.encryptKey(item.getScanTypeDetail().toString()));
				scanActivity.setScanDetail(JasyptConfig.encryptKey(item.getScanDetail().toString()));
				//scanActivity.setScanPath(JasyptConfig.encryptKey(item.getScanPath().toString()));
				scanActivity.setTimestamp(new Date());
				scanActivity.setIsDeleted("true");
				if(null!=item.getComboBox().getSelectionModel().getSelectedItem()) {
				scanActivity.setType(item.getComboBox().getSelectionModel().getSelectedItem().toString());
				}else {
					scanActivity.setType(null);
				}
				scanActivity.setRemarks(JasyptConfig.encryptKey(item.getUserremarks().getText()));
				scanActivity.setStatus("REQUEST FOR APPROVAL");
				scanActivity.setUserScanActivityId(item.getUserScanActivityId());
				scanActivityService.save(scanActivity);
				scanActivityUpdated = new ScanActivity();
				scanActivityUpdated=scanActivity;
				scanActivity.setIsDeleted("false");
				scanActivityUpdated.setStatus("SCAN");
				scanActivityUpdated.setId(item.getId());
				scanActivityUpdated.setRemarks(JasyptConfig.encryptKey(item.getUserremarks().getText()));
				scanActivityUpdated.setCurrentStatus("REQUEST FOR APPROVAL");
				scanActivityService.save(scanActivityUpdated);
				checkFlag=true;
				
				
			}
		}
		if(checkFlag) {
			alert(null, "Files Approves", "Send Files Succesfully For Approval");
			
			  clearMYSQLField();
			
			
		}else {
			alert(null, "Files Approves", "Please select file");
		}
		LogActivityDetail logActivityDetail = new LogActivityDetail();
		logActivityDetail.setActivityName(LogActivityEnum.REQUEST.toString());
		logActivityDetail.setActivityDate(new Date());
		loadUserScannedSQL();
		
	}
	@FXML
	public void clearMYSQLField(){
		dbName.clear();
		dbPassword.clear();
		dbUser.clear();
		serverPort.clear();
		serverAddress.clear();
	}
	
	
	
	@FXML
	Task copyWorker;
	
    @FXML
	void scanFiles(ActionEvent event) throws Exception{
    	
		scanFiles.setOnAction(new EventHandler<ActionEvent>() {
			
            public void handle(ActionEvent event) {
            	alert(null, "Start Scanning Progress", "Start Scanning Progress....");
              
                copyWorker = createWorker();
            
                copyWorker.messageProperty().addListener(new ChangeListener<String>() {
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        System.out.println(newValue);
                        UserScanActivity userScanActivity = new UserScanActivity();
                		userScanActivity.setFilePath(selectFile.getText());
                		userScanActivity.setTimestamp(new Date());
                		userScanActivityService.save(userScanActivity);
                		try {
                			if(loadScannedFiles(getSelectFile(), userScanActivityService.findMaxID())){
                				LogActivityDetail logActivityDetail = new LogActivityDetail();
                				logActivityDetail.setUserName(JasyptConfig.encryptKey(LoginController.getUserInstance().getFirstName() + "  "
                						+ LoginController.getUserInstance().getLastName()));
                				logActivityDetail.setActivityDate(new Date());
                				logActivityDetail.setActivityName(JasyptConfig.encryptKey("SCAN"));
                				logActivityDetail.setScanRandomId(getRandom(userScanActivityService.findMaxID()).toString());
                				logActivityDetail.setFilePath(JasyptConfig.encryptKey(selectFile.getText()));
                				logActivityDetail.setScanActivityId(userScanActivityService.findMaxID());
                				logActivityDetailService.save(logActivityDetail);
                			}
                		} catch (InterruptedException | ExecutionException e) {
                			e.printStackTrace();
                		}
                		loadUserScannedFiles();
                		alert(null, "Scanned Files", "Scanned Files Successfully....Stop Scaning");
                    }
                });
                new Thread(copyWorker).start();
            }
        });

	}
            
	@FXML
	void scanStop(ActionEvent event) {
		alert(null, "Stoped Scanning Progress", "Stoped Scanning Progress");
	
        copyWorker.cancel();
  
        for (Thread t : Thread.getAllStackTraces().keySet()) 
        {  if (t.getState()==Thread.State.RUNNABLE) 
             t.stop(); 
        }
    
	}
	/**
	 * This is IP address scanning Code
	 * @param max
	 * @return
	 */
	@FXML
	void scanFilesIPAddress(ActionEvent event) {
		
		if(null==ipAddressText.getText() || ipAddressText.getText().isEmpty()) {
			 alert(null, "Ip Adderss", "IP Address Should Not Blank");
			 return;
		}
		if(null==userNameIPText.getText() || userNameIPText.getText().isEmpty()) {
			 alert(null, "User Name", "User Name Should Not Blank");
			 return;
		}
		if(null==passwordIPText.getText() || passwordIPText.getText().isEmpty()) {
			 alert(null, "Password", "Password Should Not Blank");
			 return;
		}
		alert(null, "Remote Connection", "Please wait........");
	
		scannedFileListIP.clear();
		List<ScanActivity> scanActivityList = new ArrayList<ScanActivity>();
		try {
			UserScanActivity userScanActivity = new UserScanActivity();
			userScanActivity.setFilePath(ipAddressText.getText());
			userScanActivity.setTimestamp(new Date());
			
			userScanActivityService.save(userScanActivity);
			scanActivityList = scanService.ipAddressScan(ipAddressText.getText(), toAddressScanText.getText().trim(),userNameIPText.getText().trim(), passwordIPText.getText());
			scanActivityService.fileScaningIP(ipAddressText.getText().trim(),scanActivityList,userScanActivityService.findMaxID());
			scannedFileListIP.addAll(scanActivityList);
			scanUserIPFileTable.setItems(scannedFileListIP);
			loadUserScannedIPFiles();
			if(!scanActivityList.isEmpty()) {
				
				LogActivityDetail logActivityDetail = new LogActivityDetail();
				logActivityDetail.setUserName(JasyptConfig.encryptKey(LoginController.getUserInstance().getFirstName() + "  "
						+ LoginController.getUserInstance().getLastName()));
				logActivityDetail.setActivityDate(new Date());
				logActivityDetail.setActivityName(JasyptConfig.encryptKey("SCAN"));
				logActivityDetail.setScanRandomId(getRandom(userScanActivityService.findMaxID()).toString());
				logActivityDetail.setFilePath(JasyptConfig.encryptKey(ipAddressText.getText()));
				logActivityDetail.setScanActivityId(userScanActivityService.findMaxID());
				logActivityDetailService.save(logActivityDetail);
				alert(null, "Scanned Files", "IP Address Scanned Files Successfully");
			}
			clearIPScanFiled();
		}catch(Exception e) {
			e.printStackTrace();
		}

	}

	public static String getRandom(int max) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMddhhmmss");
		String dateAsString = simpleDateFormat.format(new Date());
		return "scan@" + (int) (Math.random() * max) + dateAsString;
	}

	@FXML
	void  clearScanDBMySQL(ActionEvent event) {
		serverAddress.clear();
		serverPort.clear();
		dbUser.clear();
		dbPassword.clear();
		dbName.clear();
	}
	
	
	@FXML
	private void scanDBMySQL() {

		UserScanActivity userScanActivity = new UserScanActivity();
		userScanActivity.setFilePath(serverAddress.getText());

		userScanActivity.setTimestamp(new Date());
		userScanActivityService.save(userScanActivity);
		loadScannedDBMySQL(getSelectServer(), getSelectPort(), getSelectdbUser(), getSelectdbPassword(),
				getSelectdbName(), "", userScanActivityService.findMaxID());

		LogActivityDetail logActivityDetail = new LogActivityDetail();
		logActivityDetail.setUserName(JasyptConfig.encryptKey(LoginController.getUserInstance().getFirstName() + "  "
				+ LoginController.getUserInstance().getLastName()));
		logActivityDetail.setActivityDate(new Date());
		logActivityDetail.setActivityName(JasyptConfig.encryptKey("SCAN"));
		logActivityDetail.setScanRandomId(getRandom(userScanActivityService.findMaxID()).toString());
		logActivityDetail.setFilePath(JasyptConfig.encryptKey(getSelectServer()));
		logActivityDetail.setScanActivityId(userScanActivityService.findMaxID());
		logActivityDetailService.save(logActivityDetail);
		alert(null, "MySQL Database Scan", "MySQL Database Scanned successfully!");
		loadUserScannedSQL();

	}

	@FXML
	private void saveUser(ActionEvent event) {

		if (validate("First Name", getFirstName(), "[a-zA-Z]+") && validate("Last Name", getLastName(), "[a-zA-Z]+")

				&& emptyValidation("Role", getRole() == null)) {

			
				if (validate("Email", getEmail(), "[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+")
						&& emptyValidation("Password", getPassword().isEmpty())) {
					User user = new User();
					user.setFirstName(JasyptConfig.encryptKey(getFirstName()));
					user.setLastName(JasyptConfig.encryptKey(getLastName()));
					if(getRole().equalsIgnoreCase("Admin")) {
						user.setPassword(getPassword());
					}else{
						user.setPassword(JasyptConfig.encryptKey(getPassword()));
					}
					user.setRole(getRole());
					user.setEmail(getEmail());
					user.setCreatedBy(Integer.parseInt(String.valueOf(LoginController.getUserInstance().getId())));
					user.setCreatedDate(new Date().toString());
					User newUser = userService.save(user);
					alert(newUser, "User Saved", "User Saved Succesfully");
				}
			else {
				User user = userService.find(Long.parseLong(userId.getText()));
				user.setFirstName(JasyptConfig.encryptKey(getFirstName()));
				user.setLastName(JasyptConfig.encryptKey(getLastName()));
				user.setCreatedBy(Integer.parseInt(String.valueOf(LoginController.getUserInstance().getId())));
				user.setRole(JasyptConfig.encryptKey(getRole()));
				user.setUpdateDate(new Date().toString());
				User updatedUser = userService.update(user);
				updateAlert(updatedUser);
			}

			clearFields();
			loadUserDetails();
		}

	}

	@FXML
	private void reviewFile(ActionEvent event) {
		stageManager.switchScene(FxmlView.ADMIN_REVIEW_FILE_SCAN);
		loadAdminScannedFiles();
	}

	@FXML
	private void reviewIP(ActionEvent event) {
		stageManager.switchScene(FxmlView.ADMIN_REVIEW_FILE_SCAN_IP);
		loadAdminReviewIP();

	}
	
	@FXML
	private void reviewAdminMySQL(ActionEvent event) {
		stageManager.switchScene(FxmlView.REVIEW_MYSQL);
		
		loadAdminReviewMYSQL();
	}
	
	@FXML
	private void backtoAdminLogs(ActionEvent event) {
		stageManager.switchScene(FxmlView.ADMIN_LOG);
		loadLogFiles();
	}

	@FXML
	private void reviewMySQL(ActionEvent event) {
		stageManager.switchScene(FxmlView.ADMIN_REVIEW_FILE_SCAN);
		loadAdminReviewMySQL();
	}
	
	@FXML
	private void remoteConnection(ActionEvent event) {
		stageManager.switchScene(FxmlView.REMOTE_CONNECTION);
	}
	@FXML
	private void scanSchedule(ActionEvent event) {
		datePicker.setValue(LocalDate.now(Clock.systemDefaultZone()));
		stageManager.switchScene(FxmlView.SCAN_SCHEDULE);
	}
	@FXML
	private void scanScheduleDetails(ActionEvent event) {
		
		stageManager.switchScene(FxmlView.SCAN_SCHEDULE_DETAILS);
	}
	private void loadScanSchedule() {
		scanScheduleList.clear();
		CheckBox scanInActiveBox = null;
		List<ScanSchedule> scanScheduleListL=scanScheduleService.getScheduleByUserId((int)LoginController.getUserInstance().getId());
		List<ScanSchedule> scanScheduleListL2= new ArrayList<ScanSchedule>();
		ScanSchedule scanScheduleL=null;
		for(ScanSchedule scanSchedule:scanScheduleListL) {
			scanScheduleL= new ScanSchedule();
			scanInActiveBox = new CheckBox();
			scanScheduleL.setId(scanSchedule.getId());
			scanScheduleL.setUserName(scanSchedule.getUserName());
			scanScheduleL.setCreatedDate(scanSchedule.getCreatedDate());
			scanScheduleL.setScanDateStr(scanSchedule.getScanDate() + " " +scanSchedule.getScanTime());
			
			if("I".equalsIgnoreCase(scanSchedule.getScanType())) {
				scanScheduleL.setScanType("IP Schedule");
			}
			if("M".equalsIgnoreCase(scanSchedule.getScanType())) {
				scanScheduleL.setScanType("Database Schedule");
			}	
			if("F".equalsIgnoreCase(scanSchedule.getScanType())) {
				scanScheduleL.setScanType("File Schedule");
			}
			//scanInActiveBox.setVisible(true);
			scanScheduleL.setScanInActiveBox(scanInActiveBox);
			scanScheduleListL2.add(scanScheduleL);
		}
		scanScheduleList.addAll(scanScheduleListL2);
		scanScheduleTable.setItems(scanScheduleList);
	}
	/** Scan Schedule functionality **/
	@FXML
	void scanScheduleDate(ActionEvent event) {
		if(datePicker.getValue()==null) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Time Schedule!");
			alert.setHeaderText(null);
			alert.setContentText("Please Select Schedule Date:");
			alert.showAndWait();
		}
		ScanSchedule scanSchedule = new ScanSchedule();
		LocalDate localDate=datePicker.getValue();
		Date scandate = java.sql.Date.valueOf(localDate);
		Date createddate = java.sql.Date.valueOf(LocalDate.now());
		scanSchedule.setUserName(LoginController.getUserInstance().getFirstName() + "  "
				+ LoginController.getUserInstance().getLastName());
		scanSchedule.setScanDate(scandate);
		scanSchedule.setUserId((int)LoginController.getUserInstance().getId());
		scanSchedule.setCreatedDate(createddate);
		scanSchedule.setScanTime(scanTime.getText());
		if(rbScanFile.isSelected()) {
			scanSchedule.setScanType("F");
		}if(rbScanIP.isSelected()) {
			scanSchedule.setScanType("I");
		}
		if(rbScanMySql.isSelected()) {
			scanSchedule.setScanType("M");
		}
		scanSchedule.setActive(true);
		scanScheduleService.save(scanSchedule);
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Scan Schedule!");
		alert.setHeaderText(null);
		alert.setContentText("Scan Schedule Done!");
		alert.showAndWait();
		datePicker.setValue(null);
		scanTime.clear();
	}
	
	@FXML
	private void logFile(ActionEvent event) {
		stageManager.switchScene(FxmlView.ADMIN_LOG);
		loadLogFiles();
		
		logFileTable.getSelectionModel().setCellSelectionEnabled(true);
		colActivityNameLog.setCellFactory(tc -> {
			TableCell<LogActivityDetail, String> cell = new TableCell<LogActivityDetail, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					setText(empty ? null : item);
				}
			};
			cell.setOnMouseClicked(e -> {
				if (!cell.isEmpty()) {
					String filepath = cell.getItem() + "/"
							+ logFileTable.getSelectionModel().getSelectedItem().getScanActivityId();
					System.out.println("---filepath--" + filepath);

					try {
						scannedAdminFileList.clear();
						stageManager.switchScene(FxmlView.ADMIN_LOG_DETAIL);
						List<ScanActivity> scanActivityList = new ArrayList<ScanActivity>();
						scanActivityList = scanActivityService.findByUserScanActivityId(
								logFileTable.getSelectionModel().getSelectedItem().getScanActivityId());
						scannedAdminFileList.addAll(scanActivityList);
						scanLogTable.setItems(scannedAdminFileList);

					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
			return cell;
		});

	}

	@FXML
	private void loadAdminDetails(int scanActivityId) throws IOException {

		scannedAdminFileList.clear();
		stageManager.switchScene(FxmlView.ADMIN_LOG_DETAIL);
		List<ScanActivity> scanActivityList = new ArrayList<ScanActivity>();
		scanActivityList = scanActivityService.findByUserScanActivityId(scanActivityId);
		scannedAdminFileList.addAll(scanActivityList);
		scanLogTable.setItems(scannedAdminFileList);

	}
	
	private void setScanScheduleProperties() {
		try {
			scanUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
			colScanScheduleType.setCellValueFactory(new PropertyValueFactory<>("scanType"));
			scanScheduleId.setCellValueFactory(new PropertyValueFactory<>("id"));
			colScanScheduleDate.setCellValueFactory(new PropertyValueFactory<>("scanDateStr"));
			colScanCreatedDate.setCellValueFactory(new PropertyValueFactory<>("createdDate"));
			scanInActiveBox.setCellValueFactory(new PropertyValueFactory<>("scanInActiveBox"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@FXML
	private void connectUser(ActionEvent event) {
		alert(null, "User Connect", "User Connect SuccessFully");
	}

	@FXML
	private void deleteUsers(ActionEvent event) {
		List<User> users = userTable.getSelectionModel().getSelectedItems();

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText(null);
		alert.setContentText("Are you sure you want to delete selected?");
		Optional<ButtonType> action = alert.showAndWait();

		if (action.get() == ButtonType.OK)
			userService.deleteInBatch(users);

		loadUserDetails();
	}

	private void clearFields() {
		firstName.clear();
		lastName.clear();
		cbRole.getSelectionModel().clearSelection();
		email.clear();
		password.clear();
	}
	@FXML
	void clearScanFiled() {
		selectFile.clear();
	}


	private void updateAlert(User user) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("User updated successfully.");
		alert.setHeaderText(null);
		alert.setContentText("The user " + user.getFirstName() + " " + user.getLastName() + " has been updated.");
		alert.showAndWait();
	}

	private void fileScanEmptyAlert() {

		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Files Scaned Successfully!");
		alert.setHeaderText(null);
		alert.setContentText("File not found!");
		alert.showAndWait();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		if (cbRole != null) {
			cbRole.setItems(roles);
		}
		if (userTable != null) {
			userTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			setColumnProperties();
			// Add all users into table
			loadUserDetails();
		}

		if (scanUserFileTable != null) {
			scanUserFileTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			setScanFileColumnProperties();
			loadUserScannedFiles();

		}
		if (scanActivityTable != null) {
			scanActivityTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			setAdminScanFileColumnProperties();
		
		}
		if (scanActivityTableIPAdmin != null) {
			scanActivityTableIPAdmin.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			setAdminIPScanFileColumnProperties();
		}


		if (scanLogTable != null) {
			scanLogTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			setAdminScanLogColumnProperties();
		}

		if (scanMySQLTable != null) {
			scanMySQLTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			setScanMySQLColumnProperties();
			loadUserScannedSQL();
		}

		if (scanActivityTableMYSQLAdmin != null) {
			scanActivityTableMYSQLAdmin.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			setAdminMYSQLScanFileColumnProperties();
			loadAdminReviewMYSQL();
		}
		
		if (logFileTable != null) {
			logFileTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

			setLogFileColumnProperties();
		}
		
		if (scanUserIPFileTable != null) {
			scanUserIPFileTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			setScanFileIPColumnProperties();
			loadUserScannedIPFiles();
			
		}
		
		if (scanScheduleTable != null) {
			scanScheduleTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			setScanScheduleProperties();
			loadScanSchedule();
			
		}
	}

	/*
	 * Set All userTable column properties
	 */
	private void setColumnProperties() {
		try {
			colUserId.setCellValueFactory(new PropertyValueFactory<>("id"));
			colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
			colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
			colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
			colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
			colEdit.setCellFactory(cellFactory);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	Callback<TableColumn<User, Boolean>, TableCell<User, Boolean>> cellFactory = new Callback<TableColumn<User, Boolean>, TableCell<User, Boolean>>() {
		@Override
		public TableCell<User, Boolean> call(final TableColumn<User, Boolean> param) {
			final TableCell<User, Boolean> cell = new TableCell<User, Boolean>() {
				Image imgEdit = new Image(getClass().getResourceAsStream("/images/edit.png"));
				final Button btnEdit = new Button();

				@Override
				public void updateItem(Boolean check, boolean empty) {
					super.updateItem(check, empty);
					if (empty) {
						setGraphic(null);
						setText(null);
					} else {
						btnEdit.setOnAction(e -> {
							User user = getTableView().getItems().get(getIndex());
							updateUser(user);
						});

						btnEdit.setStyle("-fx-background-color: transparent;");
						ImageView iv = new ImageView();
						iv.setImage(imgEdit);
						iv.setPreserveRatio(true);
						iv.setSmooth(true);
						iv.setCache(true);
						btnEdit.setGraphic(iv);

						setGraphic(btnEdit);
						setAlignment(Pos.CENTER);
						setText(null);
					}
				}

				private void updateUser(User user) {
					//userId.setText(Long.toString(user.getId()));
					firstName.setText(user.getFirstName());
					lastName.setText(user.getLastName());
					email.setText(user.getEmail());
					password.setText(user.getPassword());
					cbRole.getSelectionModel().select(user.getRole());
				}
			};
			return cell;
		}
	};

	private void setScanFileColumnProperties() {

		try {
			colSelectFileCB.setCellValueFactory(new PropertyValueFactory<>("select"));
			scanPath.setCellValueFactory(new PropertyValueFactory<>("scanPath"));
			colscanDetail.setCellValueFactory(new PropertyValueFactory<>("scanDetail"));
			colComboFile.setCellValueFactory(new PropertyValueFactory<>("comboBox"));
			colRemarks.setCellValueFactory(new PropertyValueFactory<>("userremarks"));
			colScanDate.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
			colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	  public Task createWorker() {
	        return new Task() {
	            @Override
	            protected Object call() throws Exception {
	                for (int i = 0; i < 10; i++) {
	                    Thread.sleep(20);
	                    updateMessage("20 milliseconds");
	                    updateProgress(i + 1, 10);
	                }
	                return true;
	            }
	        };
	    }
	  
	  
	
	
	
	
	private void setScanFileIPColumnProperties() {

		try {
			colSelectFileCB.setCellValueFactory(new PropertyValueFactory<>("select"));
			colIpAddress.setCellValueFactory(new PropertyValueFactory<>("scanTypeDetail"));
			colscanDetail.setCellValueFactory(new PropertyValueFactory<>("scanDetail"));
			colIPScanType.setCellValueFactory(new PropertyValueFactory<>("comboBox"));
			colIPStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
			colIPTimeStamp.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
			colRemarks.setCellValueFactory(new PropertyValueFactory<>("userremarks"));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void setAdminScanLogColumnProperties() {

		try {
			colLogScanTypeDetail.setCellValueFactory(new PropertyValueFactory<>("scanTypeDetail"));
			colLogScanDetail.setCellValueFactory(new PropertyValueFactory<>("scanDetail"));
			colLogScanType.setCellValueFactory(new PropertyValueFactory<>("type"));
			colLogStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
			colDateTime.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
			colAdminRemarksText.setCellValueFactory(new PropertyValueFactory<>("adminRemarks"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void setAdminScanFileColumnProperties() {

		try {
			colAdminSelectFileCB.setCellValueFactory(new PropertyValueFactory<>("select"));
			colScanTypeDetail.setCellValueFactory(new PropertyValueFactory<>("scanPath"));
			colScanDetail.setCellValueFactory(new PropertyValueFactory<>("scanDetail"));
			colScanType.setCellValueFactory(new PropertyValueFactory<>("comboBox"));
			colUserRemarks.setCellValueFactory(new PropertyValueFactory<>("remarks"));
			colAdminRemarks.setCellValueFactory(new PropertyValueFactory<>("adminremarks"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private void setAdminIPScanFileColumnProperties() {

		try {
			colAdminSelectFileCBIP.setCellValueFactory(new PropertyValueFactory<>("select"));
			colScanTypeDetailIP.setCellValueFactory(new PropertyValueFactory<>("scanTypeDetail"));
			colScanDetailIP.setCellValueFactory(new PropertyValueFactory<>("scanDetail"));
			colScanTypeIP.setCellValueFactory(new PropertyValueFactory<>("comboBox"));
			colUserRemarksIP.setCellValueFactory(new PropertyValueFactory<>("remarks"));
			colAdminRemarksIP.setCellValueFactory(new PropertyValueFactory<>("adminremarks"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private void setAdminMYSQLScanFileColumnProperties() {

		try {
			colAdminSelectFileCBMYSQL.setCellValueFactory(new PropertyValueFactory<>("select"));
			colScanTypeDetailMYSQL.setCellValueFactory(new PropertyValueFactory<>("scanTypeDetail"));
			colScanDetailMYSQL.setCellValueFactory(new PropertyValueFactory<>("scanDetail"));
			colScanTypeMYSQL.setCellValueFactory(new PropertyValueFactory<>("comboBox"));
			colUserRemarksMYSQL.setCellValueFactory(new PropertyValueFactory<>("remarks"));
			colAdminRemarksMYSQL.setCellValueFactory(new PropertyValueFactory<>("adminremarks"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void setScanMySQLColumnProperties() {

		try {
			colSelectMySQLCB.setCellValueFactory(new PropertyValueFactory<>("select"));
			colServerAddress.setCellValueFactory(new PropertyValueFactory<>("scanTypeDetail"));
			colTableName.setCellValueFactory(new PropertyValueFactory<>("scanDetail"));
			colMySQLStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
			colMySQLScanDate.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
			colMySQLType.setCellValueFactory(new PropertyValueFactory<>("comboBox"));
			colRemarksSQL.setCellValueFactory(new PropertyValueFactory<>("userremarks"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	private void setLogFileColumnProperties() {
		try {
			colActivityNameLog.setCellValueFactory(new PropertyValueFactory<>("activityName"));
			colActivityDateLog.setCellValueFactory(new PropertyValueFactory<>("activityDate"));
			colUserNameLog.setCellValueFactory(new PropertyValueFactory<>("userName"));
			colFilePathLog.setCellValueFactory(new PropertyValueFactory<>("filePath"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	Callback<TableColumn<ScannedFile, Boolean>, TableCell<ScannedFile, Boolean>> cellFactoryScan = new Callback<TableColumn<ScannedFile, Boolean>, TableCell<ScannedFile, Boolean>>() {
		@Override
		public TableCell<ScannedFile, Boolean> call(final TableColumn<ScannedFile, Boolean> param) {
			final TableCell<ScannedFile, Boolean> cell = new TableCell<ScannedFile, Boolean>() {
				Image imgEdit = new Image(getClass().getResourceAsStream("/images/edit.png"));
				final Button btnEdit = new Button();

				@Override
				public void updateItem(Boolean check, boolean empty) {
					super.updateItem(check, empty);
					if (empty) {
						setGraphic(null);
						setText(null);
					} else {
						btnEdit.setOnAction(e -> {
							ScannedFile user = getTableView().getItems().get(getIndex());
							// updateUser(user);
						});

						btnEdit.setStyle("-fx-background-color: transparent;");
						ImageView iv = new ImageView();
						iv.setImage(imgEdit);
						iv.setPreserveRatio(true);
						iv.setSmooth(true);
						iv.setCache(true);
						btnEdit.setGraphic(iv);

						setGraphic(btnEdit);
						setAlignment(Pos.CENTER);
						setText(null);
					}
				}

			};
			return cell;
		}
	};

	/*
	 * Add All users to observable list and update table
	 */
	private void loadUserDetails() {
		userList.clear();
		User user=null;
		List<User> userListLocal=null;
		//List<User> userListLocal = userService.findAll();
		if(LoginController.getUserInstance().getRole().equalsIgnoreCase("Super")) {
			 userListLocal = userService.findByAllAdmin();
		}else
		 {
			 userListLocal = userService.findByCreatedId(Integer.parseInt(String.valueOf(LoginController.getUserInstance().getId())));
		}
		
		for(User userLocal :userListLocal) {
			user= new User();
			user.setId(userLocal.getId());
			if(userLocal.getRole().equalsIgnoreCase("Admin")) {
			user.setFirstName(JasyptConfig.decryptKey(userLocal.getFirstName()));
			user.setLastName(JasyptConfig.decryptKey(userLocal.getLastName()));
			}else {
				user.setFirstName(JasyptConfig.decryptKey(userLocal.getFirstName()));
				user.setLastName(JasyptConfig.decryptKey(userLocal.getLastName()));
			}
			user.setEmail(userLocal.getEmail());
			user.setRole(userLocal.getRole());
			user.setPassword(userLocal.getPassword());
			userList.addAll(user);
		}
		userTable.setItems(userList);
	}

	private Boolean loadScannedFiles(String filePath, int userScanActivityId)
			throws InterruptedException, ExecutionException {
		
		scannedFileList.clear();
		 
		    
		List<ScanActivity> scannedFiles = new ArrayList<ScanActivity>();
	
		  
		/** Calling for scanning of folder and Drive **/
		List<ScanActivity> scannedFilesList = scanActivityService.fileScaning(selectFile.getText(), filePath,
				selectFile.getText(), scannedFiles, "Done", userScanActivityId);

		scannedFileList.addAll(scannedFilesList);
		scanUserFileTable.setItems(scannedFileList);

		scanPath.setCellFactory(tc -> {
			TableCell<ScanActivity, String> cell = new TableCell<ScanActivity, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					setText(empty ? null : item);
				}
			};
			cell.setOnMouseClicked(e -> {
				if (!cell.isEmpty()) {
					String filepath = "";
					filepath = cell.getItem(); //+ "/"+ scanUserFileTable.getSelectionModel().getSelectedItem().getScanDetail();
					File file = new File(filePath);

					System.out.println("---filepath--" + filepath);
					try {

						if ((new File(filepath)).exists()) {

							Process p = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + filepath);
							p.waitFor();

						} else {

							System.out.println("File does not exist");

						}

					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
			return cell;
		});

		if (!scannedFilesList.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
	@FXML
	private void loadUserScannedSQL() {
		scannedMySQLList.clear();
		List<ScanActivity> scanActivityList = new ArrayList<ScanActivity>();
		scanActivityList = scanActivityService.findByIsDeletedFlagMYSQL();
		scannedMySQLList.addAll(scanActivityList);
		scanMySQLTable.setItems(scannedMySQLList);
	}
	@FXML
	private void loadUserScanSQL() {
		scannedMySQLList.clear();
		List<ScanActivity> scanActivityList = new ArrayList<ScanActivity>();
		scanActivityList = scanActivityService.findByStatus();
		scannedMySQLList.addAll(scanActivityList);
		scanMySQLTable.setItems(scannedMySQLList);
	}
	/**
	 * 
	 */
	private void loadUserScannedIPFiles() {
		scannedFileListIP.clear();
		List<ScanActivity> scanActivityList = new ArrayList<ScanActivity>();
		scanActivityList = scanActivityService.findByUserIsDeletedFlagIP();
		scannedFileListIP.addAll(scanActivityList);
		scanUserIPFileTable.setItems(scannedFileListIP);
		colIpAddress.setCellFactory(tc -> {
			TableCell<ScanActivity, String> cell = new TableCell<ScanActivity, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					setText(empty ? null : item);
				}
			};
			cell.setOnMouseClicked(e -> {
				if (!cell.isEmpty()) {
					String filepath = cell.getItem() + "/"
							+ scanUserIPFileTable.getSelectionModel().getSelectedItem().getScanDetail();
					File file = new File(filepath);

					System.out.println("---filepath--" + filepath);
					// System.out.println("---file
					// name--"+scanFileTable.getSelectionModel().getSelectedItem().getFileName());
					try {

						if ((new File(filepath)).exists()) {

							Process p = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + filepath);
							p.waitFor();

						} else {

							System.out.println("File does not exist");

						}

					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
			return cell;
		});

	}
	private void loadUserScannedFiles() {
		scannedFileList.clear();
		List<ScanActivity> scanActivityList = new ArrayList<ScanActivity>();
		scanActivityList = scanActivityService.findByUserIsDeletedFlag();
		scannedFileList.addAll(scanActivityList);
		scanUserFileTable.setItems(scannedFileList);
		scanPath.setCellFactory(tc -> {
			TableCell<ScanActivity, String> cell = new TableCell<ScanActivity, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					setText(empty ? null : item);
				}
			};
			cell.setOnMouseClicked(e -> {
				if (!cell.isEmpty()) {
					String filepath = cell.getItem(); //+ "\\"+ scanUserFileTable.getSelectionModel().getSelectedItem().getScanDetail();
					File file = new File(filepath);

					System.out.println("---filepath--" + filepath);
					// System.out.println("---file
					// name--"+scanFileTable.getSelectionModel().getSelectedItem().getFileName());
					try {

						if ((new File(filepath)).exists()) {

							Process p = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + filepath);
							p.waitFor();

						} else {

							System.out.println("File does not exist");

						}

					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
			return cell;
		});

		if (CollectionUtils.isEmpty(scannedAdminFileList)) {
			// fileScanEmptyAlert();
		} else {

		}
	}
/** Start **/
	private void loadAdminScannedFiles() {
		scannedAdminFileList.clear();
		List<ScanActivity> scanActivityListDecr = scanActivityService.loadAdminScannedFiles();
		scannedAdminFileList.addAll(scanActivityListDecr);
		scanActivityTable.setItems(scannedAdminFileList);
		System.out.println("-----Deleted filename are:------");

		colScanTypeDetail.setCellFactory(tc -> {
			TableCell<ScanActivity, String> cell = new TableCell<ScanActivity, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					setText(empty ? null : item);
				}
			};
			cell.setOnMouseClicked(e -> {
				if (!cell.isEmpty()) {
					String filepath = cell.getItem();// + "/"+ scanActivityTable.getSelectionModel().getSelectedItem().getScanDetail();
					File file = new File(filepath);

					System.out.println("---filepath--" + filepath);
					try {

						if ((new File(filepath)).exists()) {

							Process p = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + filepath);
							p.waitFor();

						} else {

							System.out.println("File does not exist");

						}

					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
			return cell;
		});

		if (CollectionUtils.isEmpty(scannedAdminFileList)) {
			
		} else {

		}
	}

	private void loadAdminReviewMySQL() {
		scannedAdminFileList.clear();
		List<ScanActivity> scanActivityList = new ArrayList<ScanActivity>();
		scanActivityList = scanActivityService.loadAdminScannedFilesIP();
		scannedAdminFileList.addAll(scanActivityList);

		scanActivityTable.setItems(scannedAdminFileList);
		
	}
	private void loadAdminReviewIP() {
		scannedIPListAdmin.clear();
		List<ScanActivity> scanActivityList = new ArrayList<ScanActivity>();
		scanActivityList = scanActivityService.loadAdminScannedFilesIP();
		scannedIPListAdmin.addAll(scanActivityList);
		scanActivityTableIPAdmin.setItems(scannedIPListAdmin);
	}
	
	private void loadAdminReviewMYSQL() {
		scannedMYSQLListAdmin.clear();
		List<ScanActivity> scanActivityList = new ArrayList<ScanActivity>();
		scanActivityList = scanActivityService.loadAdminScannedFilesMYSQL();
		scannedMYSQLListAdmin.addAll(scanActivityList);
		scanActivityTableMYSQLAdmin.setItems(scannedMYSQLListAdmin);
	}
	
	@FXML
	private void loadScannedDBMySQL(String server, String port, String user, String password, String dbname,
			String remarks, int userScanActivityID) {
		scannedMySQLList.clear();

		List<ScanActivity> scannedMySQL = new ArrayList<ScanActivity>();
		scannedMySQL = Utility.DBprofile(server, port, user, password, dbname, remarks, scannedMySQL,
				userScanActivityID);
		scannedMySQLList.addAll(scannedMySQL);
		scanMySQLTable.setItems(scannedMySQLList);
		ScanActivity scanActivityLocal=null;
		for (ScanActivity scanActivity:scannedMySQLList) {
			scanActivityLocal = new ScanActivity();
			// scanActivity.setId(scannedFileList.get(0).getId());
			scanActivityLocal.setScanTypeId(3);
			scanActivityLocal.setScanTypeDetail(JasyptConfig.encryptKey(scanActivity.getScanTypeDetail()));
			scanActivityLocal.setScanDetail(JasyptConfig.encryptKey(scanActivity.getScanDetail()));
			scanActivityLocal.setTimestamp(new Date());
			scanActivityLocal.setIsDeleted("false");
			scanActivityLocal.setType(null);
			scanActivityLocal.setStatus("SCAN");
			scanActivityLocal.setCurrentStatus("SCAN");
			scanActivityLocal.setRemarks("");
			scanActivityLocal.setUserScanActivityId(userScanActivityID);

			scanActivityService.save(scanActivityLocal);
		}

		if (CollectionUtils.isEmpty(scannedMySQL)) {
			fileScanEmptyAlert();
		}
	}

	private void loadLogFiles() {
		logFileList.clear();
		List<LogActivityDetail> logActivityDetailDecrList = new ArrayList<LogActivityDetail>();
		logActivityDetailDecrList = logActivityDetailService.findAll();
		logFileList.addAll(logActivityDetailDecrList);
		logFileTable.setItems(logFileList);
	}
	
	/*
	 * Validations
	 */
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

	private boolean emptyValidation(String field, boolean empty) {
		if (!empty) {
			return true;
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

	

	
	
	/*** Remote Code start here ***/
	@FXML
	void clearRemoteFiled() {
	 fromIpAddressText.clear();
	 toIpAddressText.clear();
	 remoteUserNameText.clear();
	 remotePasswordText.clear();
	}
	@FXML
	void remoteConnectionTest(ActionEvent event) {
		if(null==fromIpAddressText.getText() || fromIpAddressText.getText().isEmpty())
		{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("IP Address!");
			alert.setHeaderText(null);
			alert.setContentText("IP address Should not be blank !");
			alert.showAndWait();
			return;
		}
		if(null==remoteUserNameText.getText() || remoteUserNameText.getText().isEmpty())
		{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("User Name!");
			alert.setHeaderText(null);
			alert.setContentText("User Name Should Not Blank !");
			alert.showAndWait();
			return;
		}
		if(null==remotePasswordText.getText() || remotePasswordText.getText().isEmpty())
		{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Password!");
			alert.setHeaderText(null);
			alert.setContentText("Password Should not be blank");
			alert.showAndWait();
			return;
		}
		List<String> ipList=new ArrayList<String>();
		String fromIpAddress=fromIpAddressText.getText();
		String toIpAddress=toIpAddressText.getText();
		String userName=remoteUserNameText.getText();
		String password=remotePasswordText.getText();
		int ipLastIndex= fromIpAddress.lastIndexOf(".");
		String ipLastIndexValue = fromIpAddress.substring(ipLastIndex+1, fromIpAddress.length());
		String ipStartValue = fromIpAddress.substring(0, ipLastIndex+1);
		if(null==toIpAddress || toIpAddress.isEmpty()) {
			ipList.add(fromIpAddress);
			 remoteConnection.remoteConnectionTest(ipList, userName, password);
		}else {
		int ipToLastIndex= toIpAddress.lastIndexOf(".");
		String toIpLastIndex = toIpAddress.substring(ipToLastIndex+1, toIpAddress.length());
		  
		  for(int k=Integer.valueOf(ipLastIndexValue);k<=Integer.valueOf(toIpLastIndex);k++){
			  ipList.add(ipStartValue+k);
		  }
		  remoteConnection.remoteConnectionTest(ipList, userName, password);
		}
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Remote Connection!");
			alert.setHeaderText(null);
			alert.setContentText("Remote Connection Report Generated in Download Folder");
			alert.showAndWait();
			 fromIpAddressText.clear();
			 toIpAddressText.clear();			 remoteUserNameText.clear();
			 remotePasswordText.clear();
	}
	
	/*** Remote Code start here ***/

	@FXML
	void clearIPScanFiled() {
		ipAddressText.clear();
		userNameIPText.clear();
		passwordIPText.clear();
		toAddressScanText.clear();
	}
	@FXML
	void saveUserRemarkIP(ActionEvent event) {
	
		scanUserIPFileTable.setEditable(true);
		ObservableList<ScanActivity> data = scanUserIPFileTable.getItems();
		ScanActivity scanActivity = null;

			
	boolean isSelected=false;
		for (ScanActivity item : data) {
			if(item.getSelect().isSelected()) {
			if(cbRemarksAll.isSelected() && item.getStatus().equalsIgnoreCase("Scan")) {
				saveUserRemarkAllIP(event);
				
				break;
			}
			
			else if (item.getSelect().isSelected() == true) {
				scanActivity = new ScanActivity();
				scanActivity = scanActivityService.find(item.getId());
				//User Remarks
				if(null!=item.getComboBox().getSelectionModel().getSelectedItem()) {
					scanActivity.setType(item.getComboBox().getSelectionModel().getSelectedItem().toString());
				}else {
					scanActivity.setType(null);
				}
				scanActivity.setRemarks(JasyptConfig.encryptKey(item.getUserremarks().getText()));
				scanActivityService.save(scanActivity);
				
			}
			isSelected=true;
		}
		}
		if(!isSelected) {
			alert(null, "Select Record!", "Please Select Record!");
		}else {
			alert(null, "Save Remarks!", "Saved Successfully!");
		}
		loadUserScannedIPFiles();
	}
	
	@FXML
	void saveUserRemarkAllIP(ActionEvent event) {
		scanUserIPFileTable.setEditable(true);
		List<ScanActivity> scannedfiles = scanUserIPFileTable.getSelectionModel().getSelectedItems();
		ObservableList<ScanActivity> data = scanUserIPFileTable.getItems();
		ScanActivity scanActivity = null;
		
		String remakrsAll=null;
		for (ScanActivity item : data) {
			if(null!=item.getUserremarks().getText() && item.getStatus().equalsIgnoreCase("Scan")) {
				remakrsAll =item.getUserremarks().getText();
				break;
			}
			}
		for (ScanActivity item : data) {
				scanActivity = new ScanActivity();
				scanActivity = scanActivityService.find(item.getId());
				//User Remarks
				if(null!=item.getComboBox().getSelectionModel().getSelectedItem())
				scanActivity.setType(item.getComboBox().getSelectionModel().getSelectedItem().toString());
				if(item.getStatus().equalsIgnoreCase("Scan")) {
				 scanActivity.setRemarks(JasyptConfig.encryptKey(remakrsAll));
				}
				scanActivityService.save(scanActivity);
				
			}
	}
	
	@FXML
	void saveUserRemarkMYSQL(ActionEvent event) {
	
		scanMySQLTable.setEditable(true);
		ObservableList<ScanActivity> data = scanMySQLTable.getItems();
		ScanActivity scanActivity = null;

			
	boolean isSelected=false;
		for (ScanActivity item : data) {
			if(item.getSelect().isSelected()) {
			if(cbRemarksAllMYSQL.isSelected() && item.getStatus().equalsIgnoreCase("Scan")) {
				saveUserRemarkAllMYSQL(event);
				
				break;
			}
			
			else if (item.getSelect().isSelected() == true) {
				scanActivity = new ScanActivity();
				scanActivity = scanActivityService.find(item.getId());
				//User Remarks
				if(null!=item.getComboBox().getSelectionModel().getSelectedItem()) {
					scanActivity.setType(item.getComboBox().getSelectionModel().getSelectedItem().toString());
				}else {
					scanActivity.setType(null);
				}
				scanActivity.setRemarks(JasyptConfig.encryptKey(item.getUserremarks().getText()));
				scanActivityService.save(scanActivity);
				
			}
			isSelected=true;
		}
		}
		if(!isSelected) {
			alert(null, "Select Record!", "Please Select Record!");
		}else {
			alert(null, "Save Remarks!", "Saved Successfully!");
		}
		loadUserScannedSQL();
	}
	
	@FXML
	void saveUserRemarkAllMYSQL(ActionEvent event) {
		scanMySQLTable.setEditable(true);
		ObservableList<ScanActivity> data = scanMySQLTable.getItems();
		ScanActivity scanActivity = null;
		
		String remakrsAll=null;
		for (ScanActivity item : data) {
			if(null!=item.getUserremarks().getText() && item.getStatus().equalsIgnoreCase("Scan")) {
					remakrsAll =item.getUserremarks().getText();
					break;
			}
			}
		for (ScanActivity item : data) {
				scanActivity = new ScanActivity();
				scanActivity = scanActivityService.find(item.getId());
				//User Remarks
				if(null!=item.getComboBox().getSelectionModel().getSelectedItem())
				scanActivity.setType(item.getComboBox().getSelectionModel().getSelectedItem().toString());
				if(item.getStatus().equalsIgnoreCase("Scan")) {
				 scanActivity.setRemarks(JasyptConfig.encryptKey(remakrsAll));
				}
				scanActivityService.save(scanActivity);
				
			}
	}
	
	
	@FXML
	void saveAdminRemarkMYSQL(ActionEvent event) {
		Boolean checkFlag=false;
		scanActivityTableMYSQLAdmin.setEditable(true);
		ObservableList<ScanActivity> data = scanActivityTableMYSQLAdmin.getItems();
		ScanActivity scanActivity = null;
		for (ScanActivity item : data) {
			scanActivity = scanActivityService.find(item.getId());
			if(cbRemarksAllAdminMYSQL.isSelected() && scanActivity.getStatus().equalsIgnoreCase("REQUEST FOR APPROVAL")) {
				saveAdminRemarkAllMYSQL(event);
				checkFlag=true;
				break;
			}else if (item.getSelect().isSelected() == true) {
				scanActivity = new ScanActivity();
				scanActivity = scanActivityService.find(item.getId());
				//User Remarks
				if(null!=item.getComboBox().getSelectionModel().getSelectedItem()) {
					scanActivity.setType(item.getComboBox().getSelectionModel().getSelectedItem().toString());
				}else {
					scanActivity.setType(null);
				}
				scanActivity.setAdminRemarks(JasyptConfig.encryptKey(item.getAdminremarks().getText()));
				scanActivityService.save(scanActivity);
				checkFlag=true;
			}
			
		}
		if(checkFlag) {
			alert(null, "Save Record!", "Save Record successfully!");
			
		}else {
			alert(null, "Files Approves", "Please select file");
		}
				
	}


@FXML
	void saveAdminRemarkAllMYSQL(ActionEvent event) {
	scanActivityTableMYSQLAdmin.setEditable(true);
		ObservableList<ScanActivity> data = scanActivityTableMYSQLAdmin.getItems();
		ScanActivity scanActivity = null;
		
		String remakrsAll=null;
		for (ScanActivity item : data) {
			scanActivity = new ScanActivity();
			scanActivity = scanActivityService.find(item.getId());
			if(null!=scanActivity.getStatus() && scanActivity.getStatus().equalsIgnoreCase("REQUEST FOR APPROVAL")) {
				remakrsAll =item.getAdminremarks().getText();
				break;
			}
			}
		for (ScanActivity item : data) {
				scanActivity = new ScanActivity();
				scanActivity = scanActivityService.find(item.getId());
				//User Remarks
				if(null!=item.getComboBox().getSelectionModel().getSelectedItem())
				scanActivity.setType(item.getComboBox().getSelectionModel().getSelectedItem().toString());
				if(scanActivity.getStatus().equalsIgnoreCase("REQUEST FOR APPROVAL")) {
				 scanActivity.setAdminRemarks(JasyptConfig.encryptKey(remakrsAll));
				}
				scanActivityService.save(scanActivity);
			}
		loadAdminReviewMYSQL();		
	}

@FXML
private void deleteAllFileMYSQL() {
	
	
	String filePath = null;
	String remarksValue = null;

	ObservableList<ScanActivity> data = scanActivityTableMYSQLAdmin.getItems();
	/*********************/
	ScanActivity scanActivityLocal = null;
	
	for (ScanActivity item : data) {

		if (item.getSelect().isSelected() == true) {

			if (item.getRemarks() == null) {
				remarksValue = "";

			} else {

				remarksValue = item.getRemarks().toString();
			}

			scanActivityLocal = new ScanActivity();
			scanActivityLocal.setId(item.getId());
			scanActivityLocal.setScanTypeId(item.getScanTypeId());
			scanActivityLocal.setScanTypeDetail(JasyptConfig.encryptKey(item.getScanTypeDetail().toString()));
			scanActivityLocal.setScanDetail(JasyptConfig.encryptKey(item.getScanDetail().toString()));
			//scanActivityLocal.setScanPath(JasyptConfig.encryptKey(item.getScanPath().toString()));
			scanActivityLocal.setTimestamp(new Date());
			scanActivityLocal.setIsDeleted("false");
			if(null!=item.getComboBox().getSelectionModel().getSelectedItem()) {
				scanActivityLocal.setType(item.getComboBox().getSelectionModel().getSelectedItem().toString());
			}else {
				scanActivityLocal.setType(null);
			}
			ScanActivity scanActivity=scanActivityService.find(item.getId());
			if(null!=scanActivity.getAdminRemarks()) {
				scanActivityLocal.setAdminRemarks(scanActivity.getAdminRemarks());
			}
			scanActivityLocal.setStatus("REQUEST FOR APPROVAL");
			scanActivityLocal.setCurrentStatus("DELETED");
			scanActivityLocal.setUserScanActivityId(item.getUserScanActivityId());

			scanActivityService.save(scanActivityLocal);
		}
	}
	/**************************/
	ScanActivity scanActivity = null;
	Boolean deleteFlag=false;
	for (ScanActivity item1 : data) {

		if (item1.getSelect().isSelected() == true) {

			if (item1.getRemarks() == null) {
				remarksValue = "";

			} else {

				remarksValue = item1.getRemarks().toString();
			}

			scanActivity = new ScanActivity();
		    //scanActivity.setId(item1.getId());
			scanActivity.setScanTypeId(item1.getScanTypeId());
			scanActivity.setScanTypeDetail(JasyptConfig.encryptKey(item1.getScanTypeDetail().toString()));
			scanActivity.setScanDetail(JasyptConfig.encryptKey(item1.getScanDetail().toString()));
			//scanActivity.setScanPath(JasyptConfig.encryptKey(item1.getScanPath().toString()));
			scanActivity.setTimestamp(new Date());
			scanActivity.setIsDeleted("true");
			if(null!=item1.getComboBox().getSelectionModel().getSelectedItem()) {
				scanActivity.setType(item1.getComboBox().getSelectionModel().getSelectedItem().toString());
			}else {
				scanActivity.setType(null);
			}
			if(null!=item1.getRemarks()) {
				scanActivity.setRemarks(JasyptConfig.encryptKey(item1.getRemarks().toString()));
			}
			scanActivity.setStatus("DELETED");
			scanActivity.setUserScanActivityId(item1.getUserScanActivityId());
			if(null!=item1.getAdminremarks().getText()) {
				scanActivity.setAdminRemarks(JasyptConfig.encryptKey(item1.getAdminremarks().getText().toString()));
				}
			scanActivityService.save(scanActivity);
			//filePath = item1.getScanPath();
			filePath = item1.getScanTypeDetail().toString()+item1.getScanDetail().toString();
			System.out.println("Deleted filename are:" + filePath);
			Utility.deleteFile(filePath);

			LogActivityDetail logActivityDetail = new LogActivityDetail();
			logActivityDetail.setActivityName(LogActivityEnum.DELETE.toString());
			logActivityDetail.setActivityDate(new Date());
			logActivityDetail.setScanActivityId((int) item1.getId());
			scanActivityService.updateByUserScanActivityId(item1.getUserScanActivityId());
			isDeletedUpdate(item1.getId());
			deleteFlag=true;

		}
	}
	if(deleteFlag) {
		alert(null, "Files deleted!", "File deleted successfully!");
		ActionEvent event = null;
		 reviewAdminMySQL(event);
		
	}else {
		alert(null, "Files deleted!", "Please select file");
	}
	
	
}


	
	public String getFirstName() {
		return firstName.getText();
	}

	public String getSelectFile() {
		return selectFile.getText();
	}

	public String getSelectServer() {
		return serverAddress.getText();
	}

	public String getSelectPort() {
		return serverPort.getText();
	}

	public String getSelectdbUser() {
		return dbUser.getText();
	}

	public String getSelectdbName() {
		return dbName.getText();
	}

	public String getSelectdbPassword() {
		return dbPassword.getText();
	}

	public String getLastName() {
		return lastName.getText();
	}

	

	public String getProfile() {
		return rbFile.isSelected() ? "File" : "Database";
	}

	public String getRole() {
		return cbRole.getSelectionModel().getSelectedItem();
	}

	public String getEmail() {
		return email.getText();
	}

	public String getPassword() {
		return password.getText();
	}

	private void alert(User user, String title, String text) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(text);
		alert.showAndWait();
	}

	public RadioButton getRbDirectoryScan() {
		return rbDirectoryScan;
	}

	public void setRbDirectoryScan(RadioButton rbDirectoryScan) {
		this.rbDirectoryScan = rbDirectoryScan;
	}

	public RadioButton getRbFileScan() {
		return rbFileScan;
	}

	public void setRbFileScan(RadioButton rbFileScan) {
		this.rbFileScan = rbFileScan;
	}
}
