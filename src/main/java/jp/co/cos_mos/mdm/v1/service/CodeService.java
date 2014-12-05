package jp.co.cos_mos.mdm.v1.service;

import jp.co.cos_mos.mdm.v1.service.domain.CodeServiceRequest;
import jp.co.cos_mos.mdm.v1.service.domain.CodeServiceResponse;

public interface CodeService {

	public CodeServiceResponse create(CodeServiceRequest request);
	public CodeServiceResponse update(CodeServiceRequest request);
	public CodeServiceResponse delete(CodeServiceRequest request);
	public CodeServiceResponse importAll(CodeServiceRequest request);
	public CodeServiceResponse getCode(CodeServiceRequest request);
	public CodeServiceResponse getCodes(CodeServiceRequest request);
	public CodeServiceResponse getCodeDetail(CodeServiceRequest request);
	public CodeServiceResponse getCheckResult(CodeServiceRequest request);
	public CodeServiceResponse getDifference(CodeServiceRequest request);

}
