package jp.co.cos_mos.mdm.v1.service.domain;

import java.util.List;

import jp.co.cos_mos.mdm.core.service.domain.entity.Control;
import jp.co.cos_mos.mdm.core.service.domain.entity.Result;
import jp.co.cos_mos.mdm.v1.service.domain.entity.CodeObj;

public class CodeServiceResponse {

	private Control control;
	private Result result;
	private List<CodeObj> output;
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
	public List<CodeObj> getOutput() {
		return output;
	}
	public void setOutput(List<CodeObj> output) {
		this.output = output;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}

	
}
