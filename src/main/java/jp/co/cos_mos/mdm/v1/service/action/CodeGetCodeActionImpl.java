package jp.co.cos_mos.mdm.v1.service.action;

import java.util.ArrayList;
import java.util.List;

import jp.co.cos_mos.mdm.core.service.domain.entity.Control;
import jp.co.cos_mos.mdm.core.service.domain.entity.Result;
import jp.co.cos_mos.mdm.core.service.domain.entity.Status;
import jp.co.cos_mos.mdm.v1.dao.entity.Code;
import jp.co.cos_mos.mdm.v1.dao.mapper.CodeMapper;
import jp.co.cos_mos.mdm.v1.service.domain.CodeServiceResponse;
import jp.co.cos_mos.mdm.v1.service.domain.entity.CodeCriteriaObj;
import jp.co.cos_mos.mdm.v1.service.domain.entity.CodeObj;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class CodeGetCodeActionImpl implements CodeGetCodeAction {

	@Autowired
	private CodeMapper codeMapper;
	
	@Transactional(readOnly=true)
	public CodeServiceResponse perform(Control control, CodeCriteriaObj criteria) {
		// 返却データ生成
		CodeServiceResponse response = new CodeServiceResponse();
		response.setControl(control);
		
		// 入力チェック
		Result result = validate(criteria);
		if (result.getStatus() != Status.SUCCESS) {
			response.setResult(result);
			return response;
		}
		
		// 検索
		Code selectCode = codeMapper.select(Long.parseLong(criteria.getId()));
		
		// 検索結果がない場合ステータスを設定
		if (selectCode == null) {
			result.setStatus(Status.DATA_NOT_FOUND);
			response.setResult(result);
			return response;
		}
		
		// 検索結果をセット
		CodeObj respCode = new CodeObj();
		respCode.setCategoryId(selectCode.getCategoryId().toString());
		respCode.setCode(selectCode.getCode().toString());
		respCode.setEndDate(selectCode.getEndDate());
		respCode.setId(selectCode.getId().toString());
		respCode.setLastUpdateTs(selectCode.getLastUpdateTs().toString());
		respCode.setLastUpdateTxId(selectCode.getLastUpdateTxId().toString());
		respCode.setLastUpdateUser(selectCode.getLastUpdateUser());
		respCode.setName(selectCode.getName());
		respCode.setOwnerId(selectCode.getOwnerId().toString());
		respCode.setStartDate(selectCode.getStartDate());
		
		List<CodeObj> output = new ArrayList<CodeObj>();
		output.add(respCode);
		result.setStatus(Status.SUCCESS);
		response.setOutput(output);
		response.setResult(result);
		return response;
	}
	
	/**
	 * 入力チェック
	 * 
	 * @param criteria 入力データ
	 * @return 入力チェック結果
	 */
	private Result validate(CodeCriteriaObj criteria) {
		Result result = new Result();
		
		// 入力値がない場合エラー
		if (criteria == null) {
			result.setStatus(Status.BAD_REQUEST_VALUE);
			return result;
		}
		
		// IDが入力されていない、または数字ではない場合エラー
		if (StringUtils.isEmpty(criteria.getId()) || !StringUtils.isNumeric(criteria.getId())) {
			result.setStatus(Status.BAD_REQUEST_VALUE);
		}
		return result;
	}

}