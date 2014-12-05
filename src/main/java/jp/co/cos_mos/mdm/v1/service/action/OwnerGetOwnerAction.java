package jp.co.cos_mos.mdm.v1.service.action;

import jp.co.cos_mos.mdm.core.service.domain.entity.Control;
import jp.co.cos_mos.mdm.v1.service.domain.OwnerServiceResponse;
import jp.co.cos_mos.mdm.v1.service.domain.entity.OwnerCriteriaObj;

public interface OwnerGetOwnerAction {

	public abstract OwnerServiceResponse perform(Control control,
			OwnerCriteriaObj criteria);

}