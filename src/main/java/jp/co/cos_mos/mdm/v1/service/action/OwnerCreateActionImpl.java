package jp.co.cos_mos.mdm.v1.service.action;

import java.util.ArrayList;
import java.util.List;

import jp.co.cos_mos.mdm.core.service.action.SequenceNumberNumberingAction;
import jp.co.cos_mos.mdm.core.service.domain.entity.Control;
import jp.co.cos_mos.mdm.core.service.domain.entity.Result;
import jp.co.cos_mos.mdm.core.service.domain.entity.Status;
import jp.co.cos_mos.mdm.v1.dao.entity.Owner;
import jp.co.cos_mos.mdm.v1.dao.mapper.OwnerMapper;
import jp.co.cos_mos.mdm.v1.service.domain.OwnerServiceResponse;
import jp.co.cos_mos.mdm.v1.service.domain.entity.OwnerObj;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class OwnerCreateActionImpl implements OwnerCreateAction {

	@Autowired
	private OwnerMapper ownerMapper;
	@Autowired
	private SequenceNumberNumberingAction numberingAction;
	
	/* (非 Javadoc)
	 * @see jp.co.cos_mos.mdm.v1.service.action.OwnerCreateAction#perform(jp.co.cos_mos.mdm.core.service.domain.entity.Control, jp.co.cos_mos.mdm.v1.service.domain.entity.OwnerObj)
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public OwnerServiceResponse perform(Control control, OwnerObj input) {
		OwnerServiceResponse response = new OwnerServiceResponse();
		response.setControl(control);
		
		Result result = validate(input);
		if (result.getStatus() != Status.SUCCESS) {
			response.setResult(result);
			return response;
		}
		
		Long ownerId = numberingAction.getEntityNumberingId(Owner.class.getName());
		
		Owner newOwner = new Owner();
		newOwner.setId(ownerId);
		newOwner.setLastUpdateUser(control.getRequesterName());
		newOwner.setLastUpdateTxId(control.getTransactionId());
		newOwner.setName(input.getName());
		newOwner.setInactiveTs(null);
		
		ownerMapper.insert(newOwner);
		
		newOwner = ownerMapper.select(ownerId);
		
		OwnerObj respOwner = new OwnerObj();
		respOwner.setId(String.valueOf(newOwner.getId()));
		respOwner.setName(newOwner.getName());
		respOwner.setLastUpdateTs(newOwner.getLastUpdateTs().toString());
		respOwner.setLastUpdateTxId(newOwner.getLastUpdateTxId().toString());
		respOwner.setLastUpdateUser(newOwner.getLastUpdateUser());

		List<OwnerObj> output = new ArrayList<OwnerObj>();
		output.add(respOwner);
		
		response.setOutput(output);
		response.setResult(result);

		return response;
	}
	
	private Result validate(OwnerObj input) {
		Result result = new Result();
		
		if (input == null) {
			result.setStatus(Status.BAD_REQUEST_VALUE);
			return result;
		}

		if (StringUtils.isNotEmpty(input.getId())) {
			result.setStatus(Status.BAD_REQUEST_VALUE);
			return result;
		}

		if (StringUtils.isEmpty(input.getName())) {
			result.setStatus(Status.BAD_REQUEST_VALUE);
			return result;
		}
		
		return result;
	}
}
