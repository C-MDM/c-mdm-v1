package jp.co.cos_mos.mdm.v1.service;


import jp.co.cos_mos.mdm.v1.service.action.CodeCategoryCreateAction;
import jp.co.cos_mos.mdm.v1.service.action.CodeCategoryGetCodeCategoriesAction;
import jp.co.cos_mos.mdm.v1.service.action.CodeCategoryGetCodeCategoryAction;
import jp.co.cos_mos.mdm.v1.service.action.CodeCategoryInactiveAction;
import jp.co.cos_mos.mdm.v1.service.action.CodeCategoryUpdateAction;
import jp.co.cos_mos.mdm.v1.service.domain.CodeCategoryServiceRequest;
import jp.co.cos_mos.mdm.v1.service.domain.CodeCategoryServiceResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
class CodeCategoryServiceImpl implements CodeCategoryService {

	@Autowired
	private CodeCategoryCreateAction createAction;
	@Autowired
	private CodeCategoryGetCodeCategoryAction getCodeCategoryAction;
	@Autowired
	private CodeCategoryGetCodeCategoriesAction getCodeCategoriesAction;
	@Autowired
	private CodeCategoryInactiveAction inactiveAction;
	@Autowired
	private CodeCategoryUpdateAction updateAction;
	
	@Transactional
	public CodeCategoryServiceResponse create(CodeCategoryServiceRequest request) {
		return createAction.perform(request.getControl(), request.getInput());
	}

	@Transactional
	public CodeCategoryServiceResponse update(CodeCategoryServiceRequest request) {
		return updateAction.perform(request.getControl(), request.getInput());
	}

	@Transactional
	public CodeCategoryServiceResponse inactive(
			CodeCategoryServiceRequest request) {
		return inactiveAction.perform(request.getControl(), request.getInput());
	}

	public CodeCategoryServiceResponse getCodeCategory(
			CodeCategoryServiceRequest request) {
		return getCodeCategoryAction.perform(request.getControl(), request.getCriteria());
	}

	public CodeCategoryServiceResponse getCodeCategories(
			CodeCategoryServiceRequest request) {
		return getCodeCategoriesAction.perform(request.getControl(), request.getCriteria());
	}
	
}
