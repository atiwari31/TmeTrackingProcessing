package com.dcardprocessing.bean;
import java.awt.Checkbox;

import javax.persistence.Transient;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;


public class ScannedFile {
	

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	private long id;

	private String fileName;
	private String ipAddress;
	private CheckBox select;
	private String type;


	private int userScanActivityId;

	public int getUserScanActivityId() {
		return userScanActivityId;
	}
	public void setUserScanActivityId(int userScanActivityId) {
		this.userScanActivityId = userScanActivityId;
	}

	private String status;

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	private TextField remarks;


	public TextField getRemarks() {
		return remarks;
	}
	public void setRemarks(TextField remarks) {
		this.remarks = remarks;
	}
	
	public ScannedFile(){
		this.select = new CheckBox();
		this.remarks = new TextField();
		
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
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
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
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

}
