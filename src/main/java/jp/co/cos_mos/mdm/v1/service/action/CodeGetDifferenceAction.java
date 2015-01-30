package jp.co.cos_mos.mdm.v1.service.action;

import jp.co.cos_mos.mdm.core.service.domain.entity.Control;
import jp.co.cos_mos.mdm.v1.service.domain.CodeServiceResponse;
import jp.co.cos_mos.mdm.v1.service.domain.entity.CodeCriteriaObj;

public interface CodeGetDifferenceAction {

	/**
	 * 
	 * 
	 * @param control
	 * @param criteria
	 * @return
	 */
	public abstract CodeServiceResponse perform(Control control, CodeCriteriaObj criteria);
	
}
