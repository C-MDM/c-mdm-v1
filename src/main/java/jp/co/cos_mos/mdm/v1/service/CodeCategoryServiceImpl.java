package jp.co.cos_mos.mdm.v1.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import jp.co.cos_mos.mdm.core.dao.entity.SequenceIdentifier;
import jp.co.cos_mos.mdm.core.dao.mapper.SequenceIdentifierMapper;
import jp.co.cos_mos.mdm.core.service.SequenceNumberServiceNumbering;
import jp.co.cos_mos.mdm.core.service.domain.entity.Control;
import jp.co.cos_mos.mdm.core.service.domain.entity.Message;
import jp.co.cos_mos.mdm.core.service.domain.entity.Result;
import jp.co.cos_mos.mdm.v1.dao.entity.CodeCategory;
import jp.co.cos_mos.mdm.v1.dao.entity.CodeCategoryCriteria;
import jp.co.cos_mos.mdm.v1.dao.mapper.CodeCategoryMapper;
import jp.co.cos_mos.mdm.v1.service.domain.CodeCategoryServiceRequest;
import jp.co.cos_mos.mdm.v1.service.domain.CodeCategoryServiceResponse;
import jp.co.cos_mos.mdm.v1.service.domain.entity.CodeCategoryObj;

import org.springframework.beans.factory.annotation.Autowired;

class CodeCategoryServiceImpl implements CodeCategoryService {

	@Autowired
	private SequenceIdentifierMapper sequenceIdentifierMapper;

	@Autowired
	private SequenceNumberServiceNumbering numberingService;
	
	@Autowired
	private CodeCategoryMapper codeCategoryMappar;

	private final String EMPTY = "";

	
	public CodeCategoryServiceResponse create(CodeCategoryServiceRequest request) {
		CodeCategoryServiceResponse response = new CodeCategoryServiceResponse();

		Result result = validateForCreate(request);

		//List<CodeCategoryObj> codeCategoryObjList = request.getInput();
		CodeCategoryObj codeCategoryObj = request.getInput();

		CodeCategory codeCategory = new CodeCategory();
		Control control = request.getControl();
		SequenceIdentifier seqIdent =
				sequenceIdentifierMapper.select(codeCategory.getClass().getName());

//		// 件数分登録を行う
//		for (CodeCategoryObj codeCategoryObj : codeCategoryObjList) {
			// idを取得
			Long id = numberingService.perform(seqIdent.getSequenceId());

			codeCategory.setId(id);
			codeCategory.setOwnerId(Long.parseLong(codeCategoryObj.getOwnerId()));
			codeCategory.setName(codeCategoryObj.getName());
			codeCategory.setLastUpdateUser(control.getRequesterName());
			codeCategory.setLastUpdateTxId(control.getTransactionId());

			// 登録
			int insertCount = codeCategoryMappar.insert(codeCategory);

			// 登録に失敗
			if (insertCount == 0) {
				result = new Result();
			}
//		}

		response.setResult(result);
		response.setControl(request.getControl());

		return response;
	}

	public CodeCategoryServiceResponse update(CodeCategoryServiceRequest request) {
		CodeCategoryServiceResponse response = new CodeCategoryServiceResponse();
		Control control  = request.getControl();
		response.setControl(control);

		Result result = validate(request);
		int count = 0;
		
		// TODO check
		if (!result.getStatus().equals("エラー")) {
			
			CodeCategoryObj codeCategoryObj  = request.getInput();
			CodeCategory codeCategory = new CodeCategory();
			
			codeCategory.setId(Long.valueOf(codeCategoryObj.getId()));
			codeCategory.setName(codeCategoryObj.getName());
			codeCategory.setLastUpdateTs(Timestamp.valueOf(codeCategoryObj.getLastUpdateTs()));
			codeCategory.setLastUpdateUser(control.getRequesterName());
			codeCategory.setLastUpdateTxId(control.getTransactionId());
			
			if (codeCategoryMappar.update(codeCategory) > 0) {
				count++;
//					result.setMessage;
			} else {
//					result.setMessage;
				
			}

			response = getCodeCategory(request);
		}
		response.setCount(count);
		response.setResult(result);
		return response;
	}

	public CodeCategoryServiceResponse inactive(
			CodeCategoryServiceRequest request) {
		CodeCategoryServiceResponse response = new CodeCategoryServiceResponse();
		Control control = request.getControl();
		response.setControl(control);

		Result result = validateForInactive(request);
		int count = 0;

		if (!result.getStatus().equals("error")) {
			//Status status = new Status();
			Message messagge = new Message();

			CodeCategoryObj codeCategoryObj = request.getInput();

			CodeCategory codeCategory = new CodeCategory();
			codeCategory.setId(Long.valueOf(codeCategoryObj.getId()));
			codeCategory.setInactiveTs(Timestamp.valueOf(codeCategoryObj.getInactiveTs()));
			codeCategory.setLastUpdateTs(Timestamp.valueOf(codeCategoryObj.getLastUpdateTs()));
			codeCategory.setLastUpdateUser(control.getRequesterName());
			codeCategory.setLastUpdateTxId(Long.valueOf(control.getTransactionId()));

			if (codeCategoryMappar.delete(codeCategory) > 0) {
				count++;
			} else {
				// 失敗
				//result.setStatus();
				//result.setMessage();
			}
			if (codeCategoryMappar.inactive(codeCategory) > 0) {
				count++;
			} else {
				// 失敗
				//result.setStatus();
				//result.setMessage();
			}

			//成功
			//result.setStatus();
			//result.setMessage();
		}

		response = getCodeCategories(request);
		response.setCount(count);
		response.setResult(result);

		return response;

	}

	public CodeCategoryServiceResponse getCodeCategory(
			CodeCategoryServiceRequest request) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public CodeCategoryServiceResponse getCodeCategories(
			CodeCategoryServiceRequest request) {

		// TODO: エラーチェック
		Result result = validate(request);

		// 一覧データ取得
		CodeCategoryCriteria criteria = new CodeCategoryCriteria();
		criteria.setOwnerId(request.getCriteria().getOwnerId());
		List<CodeCategory> views = codeCategoryMappar.search(criteria);

		// データ格納
		List<CodeCategoryObj> output  = new ArrayList<CodeCategoryObj>();
		for (CodeCategory category : views) {

			CodeCategoryObj obj = new CodeCategoryObj();
			obj.setId(category.getId().toString());
			obj.setOwnerId(category.getOwnerId().toString());
			obj.setName(category.getName().toString());
			obj.setInactiveTs(category.getInactiveTs().toString());
			obj.setLastUpdateTs(category.getLastUpdateTs().toString());
			obj.setLastUpdateTxId(category.getLastUpdateTxId().toString());
			obj.setLastUpdateUser(category.getLastUpdateUser().toString());
			output.add(obj);
		}
		CodeCategoryServiceResponse res = new CodeCategoryServiceResponse();
		res.setOutput(output);
		res.setResult(result);
		res.setCount(output.size());

		return res;
	}

	private Result validate(CodeCategoryServiceRequest request) {

		Result result = new Result();

		if (request.getInput()== null){
			// TODO: 2014/11/29 hashi かきたい。
		}

		return result;
	}

	private Result validateForInactive(CodeCategoryServiceRequest request) {
		Result result = new Result();
		//Status status = new Status();
		Message message = new Message();

		CodeCategoryObj codeCategoryObj = request.getInput();
		if (isNullOrEmpty(codeCategoryObj.getId())) {
			// status
			// message
		}
		if (isNullOrEmpty(codeCategoryObj.getInactiveTs())) {
			// status
			// message
		}
		if (isNullOrEmpty(codeCategoryObj.getLastUpdateTs())) {
			// status
			// message
		}

//		result.setStatus(status);
//		result.setMessagge(message);
		return result;
	}

	private boolean isNullOrEmpty(String _string) {
		boolean _boolean = false;
		if (_string == null || EMPTY.equals(_string)) {
			_boolean = true;
		}
		return _boolean;
	}
	
	/**
	 * バリデートメソッド for Create
	 * @param request
	 * @return Result
	 */
	private Result validateForCreate(CodeCategoryServiceRequest request) {
		// TODO 中身
		return new Result();
	}
	
}
