package jp.co.cos_mos.mdm.v1.service.domain.entity;

import java.io.Serializable;

public class OwnerObj implements Serializable {

	private String id;
	private String name;
	private String inactiveTs;;
	private String lastUpdateTs;
	private String lastUpdateUser;
	private String lastUpdateTxId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInactiveTs() {
		return inactiveTs;
	}
	public void setInactiveTs(String inactiveTs) {
		this.inactiveTs = inactiveTs;
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
