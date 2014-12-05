package jp.co.cos_mos.mdm.v1.service.action;

import jp.co.cos_mos.mdm.core.service.domain.entity.Control;
import jp.co.cos_mos.mdm.v1.service.domain.CodeCategoryServiceResponse;
import jp.co.cos_mos.mdm.v1.service.domain.entity.CodeCategoryObj;

public interface CodeCategoryUpdateAction {

	/**
	 * 
	 * @param control
	 * @param input
	 * @return
	 */
	public abstract CodeCategoryServiceResponse perform(Control control, CodeCategoryObj input);
}
