package jp.co.cos_mos.mdm.v1.dao.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class Code implements Serializable {
	private Long id;
	private Long ownerId;
	private Long categoryId;
	private String code;
	private String name;
	private String startDate;
	private String endDate;
	private Timestamp lastUpdateTs;
	private String lastUpdateUser;
	private Long lastUpdateTxId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public Timestamp getLastUpdateTs() {
		return lastUpdateTs;
	}
	public void setLastUpdateTs(Timestamp lastUpdateTs) {
		this.lastUpdateTs = lastUpdateTs;
	}
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
	public Long getLastUpdateTxId() {
		return lastUpdateTxId;
	}
	public void setLastUpdateTxId(Long lastUpdateTxId) {
		this.lastUpdateTxId = lastUpdateTxId;
	}
	
}
