package jp.co.cos_mos.mdm.v1.dao.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class Owner implements Serializable {

	private Long id;
	private String name;
	private Timestamp inactiveTs;;
	private Timestamp lastUpdateTs;
	private String lastUpdateUser;
	private Long lastUpdateTxId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Timestamp getInactiveTs() {
		return inactiveTs;
	}
	public void setInactiveTs(Timestamp inactiveTs) {
		this.inactiveTs = inactiveTs;
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
