package jp.co.cos_mos.mdm.v1.service.action;

import jp.co.cos_mos.mdm.core.service.domain.entity.Control;
import jp.co.cos_mos.mdm.v1.service.domain.CodeCategoryServiceResponse;
import jp.co.cos_mos.mdm.v1.service.domain.entity.CodeCategoryCriteriaObj;

public interface CodeCategoryGetCodeCategoryAction {

	/**
	 * 
	 * @param control
	 * @param criteria
	 * @return
	 */
	public abstract CodeCategoryServiceResponse perform(Control control, CodeCategoryCriteriaObj criteria);

}
