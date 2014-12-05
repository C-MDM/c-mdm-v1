package jp.co.cos_mos.mdm.v1.service.domain;

import java.io.Serializable;

@Deprecated
public class CodeCategoryServiceCriteria implements Serializable {

	private Long ownerId;

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}
	
}
