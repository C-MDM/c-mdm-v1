package jp.co.cos_mos.mdm.v1.service;

import jp.co.cos_mos.mdm.v1.service.action.OwnerCreateAction;
import jp.co.cos_mos.mdm.v1.service.action.OwnerGetOwnerAction;
import jp.co.cos_mos.mdm.v1.service.action.OwnerGetOwnersAction;
import jp.co.cos_mos.mdm.v1.service.action.OwnerInactiveAction;
import jp.co.cos_mos.mdm.v1.service.action.OwnerUpdateAction;
import jp.co.cos_mos.mdm.v1.service.domain.OwnerServiceRequest;
import jp.co.cos_mos.mdm.v1.service.domain.OwnerServiceResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OwnerServiceImpl implements OwnerService {
	
	@Autowired
	private OwnerCreateAction createAction;
	@Autowired
	private OwnerUpdateAction updateAction;
	@Autowired
	private OwnerInactiveAction inactiveAction;
	@Autowired
	private OwnerGetOwnerAction getOwnerAction;
	@Autowired
	private OwnerGetOwnersAction getOwnersAction;

	@Transactional
	public OwnerServiceResponse create(OwnerServiceRequest request) {
		return createAction.perform(request.getControl(), request.getInput());
	}

	@Transactional
	public OwnerServiceResponse update(OwnerServiceRequest request) {
		return updateAction.perform(request.getControl(), request.getInput());
	}

	@Transactional
	public OwnerServiceResponse inactive(OwnerServiceRequest request) {
		return inactiveAction.perform(request.getControl(), request.getInput());
	}

	public OwnerServiceResponse getOwner(OwnerServiceRequest request) {
		return getOwnerAction.perform(request.getControl(), request.getCriteria());
	}

	public OwnerServiceResponse getOwners(OwnerServiceRequest request) {
		return getOwnersAction.perform(request.getControl(), request.getCriteria());
	}

}
