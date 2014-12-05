package jp.co.cos_mos.mdm.v1.service.action;

import java.util.ArrayList;
import java.util.List;

import jp.co.cos_mos.mdm.core.service.domain.entity.Control;
import jp.co.cos_mos.mdm.core.service.domain.entity.Result;
import jp.co.cos_mos.mdm.core.service.domain.entity.Status;
import jp.co.cos_mos.mdm.v1.dao.entity.Owner;
import jp.co.cos_mos.mdm.v1.dao.mapper.OwnerMapper;
import jp.co.cos_mos.mdm.v1.service.domain.OwnerServiceResponse;
import jp.co.cos_mos.mdm.v1.service.domain.entity.OwnerCriteriaObj;
import jp.co.cos_mos.mdm.v1.service.domain.entity.OwnerObj;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class OwnerGetOwnersActionImpl implements OwnerGetOwnersAction {

	@Autowired
	private OwnerMapper ownerMapper;
	
	/* (非 Javadoc)
	 * @see jp.co.cos_mos.mdm.v1.service.action.OwnerGetOwnersAction#perform(jp.co.cos_mos.mdm.core.service.domain.entity.Control, jp.co.cos_mos.mdm.v1.service.domain.entity.OwnerCriteriaObj)
	 */
	@Transactional(readOnly=true)
	public OwnerServiceResponse perform(Control control, OwnerCriteriaObj criteria) {
		OwnerServiceResponse response = new OwnerServiceResponse();
		response.setControl(control);
		
		Result result = new Result();
		
		List<Owner> owners = ownerMapper.selectAll();
		if (owners.size() == 0) {
			result.setStatus(Status.DATA_NOT_FOUND);
			response.setResult(result);
			return response;
		}

		List<OwnerObj> output = new ArrayList<OwnerObj>();
		for (Owner owner: owners) {
			OwnerObj respOwner = new OwnerObj();
			respOwner.setId(owner.getId().toString());
			respOwner.setName(owner.getName());
			respOwner.setInactiveTs(owner.getInactiveTs().toString());
			respOwner.setLastUpdateTs(owner.getLastUpdateTs().toString());
			respOwner.setLastUpdateTxId(owner.getLastUpdateTxId().toString());
			respOwner.setLastUpdateUser(owner.getLastUpdateUser());
			output.add(respOwner);
		}

		response.setResult(result);
		response.setOutput(output);

		return response;
	}

}
