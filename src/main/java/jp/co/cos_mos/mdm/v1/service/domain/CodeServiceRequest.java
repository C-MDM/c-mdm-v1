package jp.co.cos_mos.mdm.v1.service.domain;

import java.io.Serializable;
import java.util.List;

import jp.co.cos_mos.mdm.core.service.domain.entity.Control;
import jp.co.cos_mos.mdm.v1.service.domain.entity.CodeCriteriaObj;
import jp.co.cos_mos.mdm.v1.service.domain.entity.CodeObj;

public class CodeServiceRequest implements Serializable {

	private Control control;
	private CodeCriteriaObj criteria;
	private List<CodeObj> input;
	
	public Control getControl() {
		return control;
	}
	public void setControl(Control control) {
		this.control = control;
	}
	public CodeCriteriaObj getCriteria() {
		return criteria;
	}
	public void setCriteria(CodeCriteriaObj criteria) {
		this.criteria = criteria;
	}
	public List<CodeObj> getInput() {
		return input;
	}
	public void setInput(List<CodeObj> input) {
		this.input = input;
	}
	
}
