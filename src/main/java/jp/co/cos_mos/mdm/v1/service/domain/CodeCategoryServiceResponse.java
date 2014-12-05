package jp.co.cos_mos.mdm.v1.service.domain;

import java.io.Serializable;
import java.util.List;

import jp.co.cos_mos.mdm.core.service.domain.entity.Control;
import jp.co.cos_mos.mdm.core.service.domain.entity.Result;
import jp.co.cos_mos.mdm.v1.service.domain.entity.CodeCategoryObj;

public class CodeCategoryServiceResponse implements Serializable {

	private Control control;
	private Result result;
	private List<CodeCategoryObj> output;
	private int count;

	public Control getControl() {
		return control;
	}
	public void setControl(Control control) {
		this.control = control;
	}
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
	public List<CodeCategoryObj> getOutput() {
		return output;
	}
	public void setOutput(List<CodeCategoryObj> output) {
		this.output = output;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
}
