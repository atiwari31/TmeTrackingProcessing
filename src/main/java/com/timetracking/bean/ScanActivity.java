package com.dcardprocessing.bean;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.poi.ss.formula.functions.T;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;



@Entity
@Table(name="scan_activity")
public class ScanActivity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private long id;
	private int scanTypeId;
	private String scanTypeDetail;
	private String scanDetail;
	private String ipAddress;
	private String dbUserName;
	private String dbPassword;
	@Column(name = "is_deleted")
	private String isDeleted;
	private Date timestamp;
	private String type;
	private String scanPath;
	private int userScanActivityId;
	@Column(name = "update_status")
	private String status;
	@Column(name = "current_status")
	private  String currentStatus;
	@Column(name = "user_remarks")
	private String remarks;
	
	@Column(name = "admin_remarks")
	private String adminRemarks;
	
	@Column(name = "user_name")
	private String userName;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getUserScanActivityId() {
		return userScanActivityId;
	}
	public void setUserScanActivityId(int userScanActivityId) {
		this.userScanActivityId = userScanActivityId;
	}
 
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	@Transient
	private TextField userremarks;

	public TextField getUserremarks() {
		return userremarks;
	}
	public void setUserremarks(TextField userremarks) {
		this.userremarks = userremarks;
	}
	@Transient
	Hyperlink hyperlink = new Hyperlink();
	
	@Transient
	private TextField adminremarks;
	@Transient
	ObservableList<String> options = 
		    FXCollections.observableArrayList(
		        "True Positive",
		        "False Positive"
		        );
	@Transient ComboBox comboBox = new ComboBox(options);
	
	public TextField getAdminremarks() {
		return adminremarks;
	}
	public void setAdminremarks(TextField adminremarks) {
		this.adminremarks = adminremarks;
	}
	
	@Transient
	private CheckBox select;
	@Transient
	private CheckBox selectAll;
	public ScanActivity(){
		this.select = new CheckBox();
		this.adminremarks = new TextField();
		this.userremarks = new TextField();
	}
	
	
	public ComboBox getComboBox() {
		return comboBox;
	}
	/**
	 * @return the select
	 */
	public CheckBox getSelect() {
		return select;
	}

	/**
	 * @param select the select to set
	 */
	public void setSelect(CheckBox select) {
		this.select = select;
	}
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the scanTypeId
	 */
	public int getScanTypeId() {
		return scanTypeId;
	}
	/**
	 * @param scanTypeId the scanTypeId to set
	 */
	public void setScanTypeId(int scanTypeId) {
		this.scanTypeId = scanTypeId;
	}
	/**
	 * @return the scanTypeDetail
	 */
	public String getScanTypeDetail() {
		return scanTypeDetail;
	}
	/**
	 * @param scanTypeDetail the scanTypeDetail to set
	 */
	public void setScanTypeDetail(String scanTypeDetail) {
		this.scanTypeDetail = scanTypeDetail;
	}

	/**
	 * @return the ipAddress
	 */
	public String getIpAddress() {
		return ipAddress;
	}
	/**
	 * @param ipAddress the ipAddress to set
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	/**
	 * @return the dbUserName
	 */
	public String getDbUserName() {
		return dbUserName;
	}
	/**
	 * @param dbUserName the dbUserName to set
	 */
	public void setDbUserName(String dbUserName) {
		this.dbUserName = dbUserName;
	}
	/**
	 * @return the dbPassword
	 */
	public String getDbPassword() {
		return dbPassword;
	}
	/**
	 * @param dbPassword the dbPassword to set
	 */
	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}
	/**
	 * @return the isDeleted
	 */
	public String getIsDeleted() {
		return isDeleted;
	}
	/**
	 * @param isDeleted the isDeleted to set
	 */
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}
	/**
	 * @return the timestamp
	 */
	public Date getTimestamp() {
		return timestamp;
	}
	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the scanDetail
	 */
	public String getScanDetail() {
		return scanDetail;
	}
	/**
	 * @param scanDetail the scanDetail to set
	 */
	public void setScanDetail(String scanDetail) {
		this.scanDetail = scanDetail;
	}
	public String getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}
	public String getAdminRemarks() {
		return adminRemarks;
	}
	public void setAdminRemarks(String adminRemarks) {
		this.adminRemarks = adminRemarks;
	}
	public void setComboBox(ComboBox comboBox) {
		this.comboBox = comboBox;
	}
	public String getScanPath() {
		return scanPath;
	}
	public void setScanPath(String scanPath) {
		this.scanPath = scanPath;
	}
	public Hyperlink getHyperlink() {
		return hyperlink;
	}
	public void setHyperlink(Hyperlink hyperlink) {
		this.hyperlink = hyperlink;
	}
		
	
}
