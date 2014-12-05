package jp.co.cos_mos.mdm.v1.service;

import jp.co.cos_mos.mdm.v1.service.domain.CodeCategoryServiceRequest;
import jp.co.cos_mos.mdm.v1.service.domain.CodeCategoryServiceResponse;

public interface CodeCategoryService {

	public CodeCategoryServiceResponse create(CodeCategoryServiceRequest request);
	public CodeCategoryServiceResponse update(CodeCategoryServiceRequest request);
	public CodeCategoryServiceResponse inactive(CodeCategoryServiceRequest request);
	public CodeCategoryServiceResponse getCodeCategory(CodeCategoryServiceRequest request);
	public CodeCategoryServiceResponse getCodeCategories(CodeCategoryServiceRequest request);

}
