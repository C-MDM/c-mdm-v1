package jp.co.cos_mos.mdm.v1.service.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import jp.co.cos_mos.mdm.core.service.domain.entity.Control;
import jp.co.cos_mos.mdm.core.service.domain.entity.Result;
import jp.co.cos_mos.mdm.core.service.domain.entity.Status;
import jp.co.cos_mos.mdm.core.service.exception.ConflictRequestException;
import jp.co.cos_mos.mdm.v1.dao.entity.Owner;
import jp.co.cos_mos.mdm.v1.dao.mapper.OwnerMapper;
import jp.co.cos_mos.mdm.v1.service.domain.OwnerServiceResponse;
import jp.co.cos_mos.mdm.v1.service.domain.entity.OwnerObj;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OwnerUpdateActionImpl implements OwnerUpdateAction {
	
	@Autowired
	private OwnerMapper ownerMapper;
	
	/* (非 Javadoc)
	 * @see jp.co.cos_mos.mdm.v1.service.action.OwnerUpdateAction#perform(jp.co.cos_mos.mdm.core.service.domain.entity.Control, jp.co.cos_mos.mdm.v1.service.domain.entity.OwnerObj)
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

		Long ownerId = Long.valueOf(input.getId());

		Owner updateOwner = new Owner();
		updateOwner.setId(ownerId);
		updateOwner.setName(input.getName());
		updateOwner.setLastUpdateTs(Timestamp.valueOf(input.getLastUpdateTs()));
		updateOwner.setLastUpdateUser(control.getRequesterName());
		updateOwner.setLastUpdateTxId(control.getTransactionId());
		
		int count = ownerMapper.update(updateOwner);
		
		if (count == 0) {
			result.setStatus(Status.EXCEPTION_CONFLICT);
			throw new ConflictRequestException(result);
		}

		updateOwner = ownerMapper.select(ownerId);
		
		OwnerObj respOwner = new OwnerObj();
		respOwner.setId(String.valueOf(updateOwner.getId()));
		respOwner.setName(updateOwner.getName());
		respOwner.setLastUpdateTs(updateOwner.getLastUpdateTs().toString());
		respOwner.setLastUpdateTxId(updateOwner.getLastUpdateTxId().toString());
		respOwner.setLastUpdateUser(updateOwner.getLastUpdateUser());

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
		
		if (StringUtils.isEmpty(input.getId()) || !StringUtils.isNumeric(input.getId())) {
			result.setStatus(Status.BAD_REQUEST_VALUE);
			return result;
		}

		if (StringUtils.isEmpty(input.getName())) {
			result.setStatus(Status.BAD_REQUEST_VALUE);
			return result;
		}

		if (input.getLastUpdateTs() == null) {
			result.setStatus(Status.BAD_REQUEST_VALUE);
			return result;
		}
		
		return result;
	}
}
