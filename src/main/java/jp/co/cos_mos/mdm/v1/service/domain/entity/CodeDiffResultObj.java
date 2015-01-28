package jp.co.cos_mos.mdm.v1.service.domain.entity;

public class CodeDiffResultObj {
	
	private String diffStatus;
	private CodeObj baseCode;
	private CodeObj targetCode;
	
	
	public String getDiffStatus() {
		return diffStatus;
	}
	public void setDiffStatus(String diffStatus) {
		this.diffStatus = diffStatus;
	}
	public CodeObj getBaseCode() {
		return baseCode;
	}
	public void setBaseCode(CodeObj baseCode) {
		this.baseCode = baseCode;
	}
	public CodeObj getTargetCode() {
		return targetCode;
	}
	public void setTargetCode(CodeObj targetCode) {
		this.targetCode = targetCode;
	}
	
}
