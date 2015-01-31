package jp.co.cos_mos.mdm.v1.service.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import jp.co.cos_mos.mdm.core.service.domain.entity.Control;
import jp.co.cos_mos.mdm.core.service.domain.entity.Result;
import jp.co.cos_mos.mdm.core.service.domain.entity.Status;
import jp.co.cos_mos.mdm.core.service.exception.ConflictRequestException;
import jp.co.cos_mos.mdm.v1.dao.entity.CodeCategory;
import jp.co.cos_mos.mdm.v1.dao.mapper.CodeCategoryMapper;
import jp.co.cos_mos.mdm.v1.service.domain.CodeCategoryServiceResponse;
import jp.co.cos_mos.mdm.v1.service.domain.entity.CodeCategoryObj;

import org.springframework.beans.factory.annotation.Autowired;

public class CodeCategoryUpdateActionImpl implements CodeCategoryUpdateAction {
	@Autowired
	private CodeCategoryMapper codeCategoryMappar;

	@Override
	public CodeCategoryServiceResponse perform(Control control,
			CodeCategoryObj input) {
		
		CodeCategoryServiceResponse response = new CodeCategoryServiceResponse();
		List<CodeCategoryObj> codeCategoryObjList = new  ArrayList<CodeCategoryObj>();
		response.setControl(control);
		Result result = validate(input);
		
		// 入力チェック
		if (!result.getStatus().equals(Status.SUCCESS)) {
			response.setResult(result);
			return response;
		} else {
			
			// 更新する値をセット
			CodeCategory codeCategory = new CodeCategory();
			codeCategory.setId(Long.valueOf(input.getId()));
			codeCategory.setName(input.getName());
			codeCategory.setOwnerId(Long.valueOf(input.getOwnerId()));
			codeCategory.setLastUpdateTs(Timestamp.valueOf(input.getLastUpdateTs()));
			codeCategory.setLastUpdateUser(control.getRequesterName());
			codeCategory.setLastUpdateTxId(control.getTransactionId());
			codeCategory.setInactiveTs(Timestamp.valueOf(input.getInactiveTs()));
			
			// 更新実施
			if (codeCategoryMappar.update(codeCategory) == 0) {
				result.setStatus(Status.EXCEPTION_CONFLICT);
				throw new ConflictRequestException(result);
			}
			
			// 更新結果再検索
			codeCategory = codeCategoryMappar.select(Long.valueOf(input.getId()));
			CodeCategoryObj codeCategoryObj = new CodeCategoryObj();
			
			codeCategoryObj.setId(String.valueOf(codeCategory.getId()));
			codeCategoryObj.setName(codeCategory.getName());
			codeCategoryObj.setOwnerId(String.valueOf(codeCategory.getOwnerId()));
			codeCategoryObj.setLastUpdateTs(String.valueOf(codeCategory.getLastUpdateTs()));
			codeCategoryObj.setLastUpdateUser(codeCategory.getLastUpdateUser());
			codeCategoryObj.setLastUpdateTxId(String.valueOf(codeCategory.getLastUpdateTxId()));
			codeCategoryObj.setInactiveTs(String.valueOf(codeCategory.getInactiveTs()));
			codeCategoryObjList.add(codeCategoryObj);
		}
		
		response.setOutput(codeCategoryObjList);
		response.setResult(result);
		return response;
	}
	
	
	private Result validate(CodeCategoryObj input) {
		Result result = new Result();
		
		// nameのチェック
		if ("".equals(input.getName()) ||  input.getName() == null) {
			result.setStatus(Status.BAD_REQUEST_VALUE);
		} else {
			result.setStatus(Status.SUCCESS);
		}
		return result;
		
	}
}
