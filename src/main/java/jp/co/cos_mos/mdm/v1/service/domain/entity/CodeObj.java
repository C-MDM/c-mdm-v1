package jp.co.cos_mos.mdm.v1.service.domain.entity;

import java.io.Serializable;

public class CodeObj implements Serializable {
	
	private String id;
	private String ownerId;
	private String categoryId;
	private String code;
	private String name;
	private String startDate;
	private String endDate;
	private String lastUpdateTs;
	private String lastUpdateUser;
	private String lastUpdateTxId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
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
	public String getLastUpdateTs() {
		return lastUpdateTs;
	}
	public void setLastUpdateTs(String lastUpdateTs) {
		this.lastUpdateTs = lastUpdateTs;
	}
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
	public String getLastUpdateTxId() {
		return lastUpdateTxId;
	}
	public void setLastUpdateTxId(String lastUpdateTxId) {
		this.lastUpdateTxId = lastUpdateTxId;
	}
	
}
