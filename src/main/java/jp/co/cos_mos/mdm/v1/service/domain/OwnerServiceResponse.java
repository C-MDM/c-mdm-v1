package jp.co.cos_mos.mdm.v1.service.domain;

import java.util.List;

import jp.co.cos_mos.mdm.core.service.domain.entity.Control;
import jp.co.cos_mos.mdm.core.service.domain.entity.Result;
import jp.co.cos_mos.mdm.v1.service.domain.entity.OwnerObj;

public class OwnerServiceResponse {

	private Control control;
	private Result result;
	private List<OwnerObj> output;
	
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
	public List<OwnerObj> getOutput() {
		return output;
	}
	public void setOutput(List<OwnerObj> output) {
		this.output = output;
	}
	
}
