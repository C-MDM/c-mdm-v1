package jp.co.cos_mos.mdm.v1.service.domain;

import java.io.Serializable;

@Deprecated
public class CodeServiceCriteria implements Serializable {

	private Long ownerId;
	private Long categoryId;
	private String code;
	private int sort;
	private String baseDate;
	private String yDate;
	private String xDate;
	
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
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public String getBaseDate() {
		return baseDate;
	}
	public void setBaseDate(String baseDate) {
		this.baseDate = baseDate;
	}
	public String getyDate() {
		return yDate;
	}
	public void setyDate(String yDate) {
		this.yDate = yDate;
	}
	public String getxDate() {
		return xDate;
	}
	public void setxDate(String xDate) {
		this.xDate = xDate;
	}
	
}
