package jp.co.cos_mos.mdm.v1.service.domain.entity;

import java.io.Serializable;

public class CodeCriteriaObj implements Serializable{
	
	private String id;
	private String ownerId;
	private String categoryId;
	private String code;
	private String sort;
	private String baseDate;
	private String targetDate;
	
	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.ownerId = id;
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
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getBaseDate() {
		return baseDate;
	}
	public void setBaseDate(String baseDate) {
		this.baseDate = baseDate;
	}
	public String getTargetDate() {
		return targetDate;
	}
	public void setTargetDate(String targetDate) {
		this.targetDate = targetDate;
	}
}
