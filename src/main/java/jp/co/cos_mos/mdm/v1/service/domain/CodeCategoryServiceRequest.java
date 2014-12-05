package jp.co.cos_mos.mdm.v1.service.domain;

import java.io.Serializable;

import jp.co.cos_mos.mdm.core.service.domain.entity.Control;
import jp.co.cos_mos.mdm.v1.service.domain.entity.CodeCategoryObj;

public class CodeCategoryServiceRequest implements Serializable {

	private Control control;
	private CodeCategoryServiceCriteria criteria;
	private CodeCategoryObj input;

	public Control getControl() {
		return control;
	}
	public void setControl(Control control) {
		this.control = control;
	}
	public CodeCategoryServiceCriteria getCriteria() {
		return criteria;
	}
	public void setCriteria(CodeCategoryServiceCriteria criteria) {
		this.criteria = criteria;
	}
	public CodeCategoryObj getInput() {
		return input;
	}
	public void setInput(CodeCategoryObj input) {
		this.input = input;
	}
	
}
