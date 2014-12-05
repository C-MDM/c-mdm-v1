package jp.co.cos_mos.mdm.v1.service.domain;

import jp.co.cos_mos.mdm.core.service.domain.entity.Control;
import jp.co.cos_mos.mdm.v1.service.domain.entity.OwnerCriteriaObj;
import jp.co.cos_mos.mdm.v1.service.domain.entity.OwnerObj;

public class OwnerServiceRequest {

	private Control control;
	private OwnerCriteriaObj criteria;
	private OwnerObj input;
	
	public Control getControl() {
		return control;
	}
	public void setControl(Control control) {
		this.control = control;
	}
	public OwnerObj getInput() {
		return input;
	}
	public void setInput(OwnerObj input) {
		this.input = input;
	}
	public OwnerCriteriaObj getCriteria() {
		return criteria;
	}
	public void setCriteria(OwnerCriteriaObj criteria) {
		this.criteria = criteria;
	}
	
}
