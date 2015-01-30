package jp.co.cos_mos.mdm.v1.service;

import jp.co.cos_mos.mdm.v1.service.action.CodeCreateAction;
import jp.co.cos_mos.mdm.v1.service.action.CodeDeleteAction;
import jp.co.cos_mos.mdm.v1.service.action.CodeGetCodeAction;
import jp.co.cos_mos.mdm.v1.service.action.CodeGetCodeDetailAction;
import jp.co.cos_mos.mdm.v1.service.action.CodeGetCodesAction;
import jp.co.cos_mos.mdm.v1.service.action.CodeGetDifferenceAction;
import jp.co.cos_mos.mdm.v1.service.action.CodeImportAllAction;
import jp.co.cos_mos.mdm.v1.service.action.CodeUpdateAction;
import jp.co.cos_mos.mdm.v1.service.domain.CodeServiceRequest;
import jp.co.cos_mos.mdm.v1.service.domain.CodeServiceResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CodeServiceImpl implements CodeService {

	@Autowired
	private CodeCreateAction createAction;
	@Autowired
	private CodeDeleteAction deleteAction;
	@Autowired
	private CodeGetCodeAction getCodeAction;
	@Autowired
	private CodeGetCodeDetailAction getCodeDetailAction;
	@Autowired
	private CodeGetCodesAction getCodesAction;
	@Autowired
	private CodeGetDifferenceAction getDifferenceAction;
	@Autowired
	private CodeImportAllAction importAllAction;
	@Autowired
	private CodeUpdateAction updateAction;
	
	@Transactional
	public CodeServiceResponse create(CodeServiceRequest request) {
		return createAction.perform(request.getControl(), request.getInput().get(0));
	}

	@Transactional
	public CodeServiceResponse update(CodeServiceRequest request) {
		return updateAction.perform(request.getControl(), request.getInput().get(0));
	}

	@Transactional
	public CodeServiceResponse delete(CodeServiceRequest request) {
		return deleteAction.perform(request.getControl(), request.getInput().get(0));
	}

	@Transactional
	public CodeServiceResponse importAll(CodeServiceRequest request) {
		return importAllAction.perform(request.getControl(), request.getInput());
	}

	public CodeServiceResponse getCode(CodeServiceRequest request) {
		return getCodeAction.perform(request.getControl(), request.getCriteria());
	}

	public CodeServiceResponse getCodes(CodeServiceRequest request) {
		return getCodesAction.perform(request.getControl(), request.getCriteria());
	}

	public CodeServiceResponse getCodeDetail(CodeServiceRequest request) {
		return getCodeDetailAction.perform(request.getControl(), request.getCriteria());
	}

	public CodeServiceResponse getCheckResult(CodeServiceRequest request) {
		return null;
	}

	public CodeServiceResponse getDifference(CodeServiceRequest request) {
		return getDifferenceAction.perform(request.getControl(), request.getCriteria());
	}

}
