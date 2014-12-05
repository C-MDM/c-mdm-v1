package jp.co.cos_mos.mdm.v1.service;

import jp.co.cos_mos.mdm.v1.service.domain.OwnerServiceRequest;
import jp.co.cos_mos.mdm.v1.service.domain.OwnerServiceResponse;

public interface OwnerService {
	
	public OwnerServiceResponse create(OwnerServiceRequest request);
	public OwnerServiceResponse update(OwnerServiceRequest request);
	public OwnerServiceResponse inactive(OwnerServiceRequest request);
	public OwnerServiceResponse getOwner(OwnerServiceRequest request);
	public OwnerServiceResponse getOwners(OwnerServiceRequest request);
	
}
