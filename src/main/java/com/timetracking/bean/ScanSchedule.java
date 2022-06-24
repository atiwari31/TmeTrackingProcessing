package com.dcardprocessing.bean;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

@Entity
@Table(name="scan_schedule")
public class ScanSchedule {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private long id;
	
	private Date createdDate ;
	private Date updatedDate ;
	private Date scanDate ;
	private int userId ;
	private String userName;
	private String scanTime;
	private String scanType;
	private boolean active;
	
	@Transient
	CheckBox scanInActiveBox = new CheckBox();

	@Transient
	private String scanDateStr ;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public Date getScanDate() {
		return scanDate;
	}
	public void setScanDate(Date scanDate) {
		this.scanDate = scanDate;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getScanTime() {
		return scanTime;
	}
	public void setScanTime(String scanTime) {
		this.scanTime = scanTime;
	}
	public String getScanDateStr() {
		return scanDateStr;
	}
	public void setScanDateStr(String scanDateStr) {
		this.scanDateStr = scanDateStr;
	}
	public String getScanType() {
		return scanType;
	}
	public void setScanType(String scanType) {
		this.scanType = scanType;
	}

	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public CheckBox getScanInActiveBox() {
		return scanInActiveBox;
	}
	public void setScanInActiveBox(CheckBox scanInActiveBox) {
		this.scanInActiveBox = scanInActiveBox;
	}
	
	
}
