package com.dcardprocessing.bean;
import java.awt.Checkbox;

import javax.persistence.Transient;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;


public class ScannedDBMySQL {
	private String tableName;
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getPortAddress() {
		return portAddress;
	}
	public void setPortAddress(String portAddress) {
		this.portAddress = portAddress;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	private String ipAddress;
	private String portAddress;
	private String type;
	public String getIsDeleted() {
		return isDeleted;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	private long id;

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	private String isDeleted;

	public int getUserScanActivityId() {
		return userScanActivityId;
	}
	public void setUserScanActivityId(int userScanActivityId) {
		this.userScanActivityId = userScanActivityId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	private int userScanActivityId;
	private String status;
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	private String remarks;
	@Transient
	private TextField userremarks;

	
	public TextField getUserremarks() {
		return userremarks;
	}
	public void setUserremarks(TextField userremarks) {
		this.userremarks = userremarks;
	}

	private CheckBox select;
	public ScannedDBMySQL(){
		this.select = new CheckBox();
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


}
