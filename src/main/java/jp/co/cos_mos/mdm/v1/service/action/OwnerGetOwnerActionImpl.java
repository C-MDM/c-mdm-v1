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

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OwnerGetOwnerActionImpl implements OwnerGetOwnerAction {

	@Autowired
	private OwnerMapper ownerMapper;
	
	/* (非 Javadoc)
	 * @see jp.co.cos_mos.mdm.v1.service.action.OwnerGetOwnerAction#perform(jp.co.cos_mos.mdm.core.service.domain.entity.Control, jp.co.cos_mos.mdm.v1.service.domain.entity.OwnerCriteriaObj)
	 */
	@Transactional(readOnly=true)
	public OwnerServiceResponse perform(Control control, OwnerCriteriaObj criteria) {
		OwnerServiceResponse response = new OwnerServiceResponse();
		response.setControl(control);
		
		Result result = validate(criteria);
		if (result.getStatus() != Status.SUCCESS) {
			response.setResult(result);
			return response;
		}

		Long ownerId = Long.valueOf(criteria.getId());
		Owner selectOwner = ownerMapper.select(ownerId);

		if (selectOwner == null) {
			result.setStatus(Status.DATA_NOT_FOUND);
			response.setResult(result);
			return response;
		}
		
		OwnerObj respOwner = new OwnerObj();
		respOwner.setId(selectOwner.getId().toString());
		respOwner.setName(selectOwner.getName());
		respOwner.setInactiveTs(selectOwner.getInactiveTs().toString());
		respOwner.setLastUpdateTs(selectOwner.getLastUpdateTs().toString());
		respOwner.setLastUpdateTxId(selectOwner.getLastUpdateTxId().toString());
		respOwner.setLastUpdateUser(selectOwner.getLastUpdateUser());

		List<OwnerObj> output = new ArrayList<OwnerObj>();
		output.add(respOwner);
		
		response.setOutput(output);
		response.setResult(result);

		return response;
	}
	
	private Result validate(OwnerCriteriaObj criteria) {
		Result result = new Result();
		
		if (criteria == null) {
			result.setStatus(Status.BAD_REQUEST_VALUE);
			return result;
		}
		if (StringUtils.isEmpty(criteria.getId()) || !StringUtils.isNumeric(criteria.getId())) {
			result.setStatus(Status.BAD_REQUEST_VALUE);
			return result;
		}
		
		return result;
	}
	
}
