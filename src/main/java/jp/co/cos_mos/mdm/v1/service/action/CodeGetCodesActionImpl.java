package jp.co.cos_mos.mdm.v1.service.action;

import java.util.ArrayList;
import java.util.List;

import jp.co.cos_mos.mdm.core.service.domain.entity.Control;
import jp.co.cos_mos.mdm.core.service.domain.entity.Result;
import jp.co.cos_mos.mdm.core.service.domain.entity.Status;
import jp.co.cos_mos.mdm.v1.dao.entity.Code;
import jp.co.cos_mos.mdm.v1.dao.entity.CodeCriteria;
import jp.co.cos_mos.mdm.v1.dao.mapper.CodeMapper;
import jp.co.cos_mos.mdm.v1.service.domain.CodeServiceResponse;
import jp.co.cos_mos.mdm.v1.service.domain.entity.CodeCriteriaObj;
import jp.co.cos_mos.mdm.v1.service.domain.entity.CodeObj;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CodeGetCodesActionImpl implements CodeGetCodesAction {

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
		
		// 入力値をDaoクラスに渡す形式に変換
		CodeCriteria codeCriteria = new CodeCriteria();
		codeCriteria.setCategoryId(Long.parseLong(criteria.getCategoryId()));
		codeCriteria.setOwnerId(Long.parseLong(criteria.getOwnerId()));
		codeCriteria.setSort(Integer.parseInt(criteria.getSort()));
		
		// 検索
		List<Code> selectCodes = new ArrayList<Code>();
		selectCodes = codeMapper.search(codeCriteria);
		
		// 検索結果がない場合ステータスを設定
		if (selectCodes.size() == 0) {
			result.setStatus(Status.DATA_NOT_FOUND);
			return response;
		}
		
		// 検索結果セット
		List<CodeObj> output = new ArrayList<CodeObj>();
		for (Code code : selectCodes) {
			CodeObj respCode = new CodeObj();
			respCode.setCategoryId(code.getCategoryId().toString());
			respCode.setCode(code.getCode());
			respCode.setEndDate(code.getEndDate());
			respCode.setId(code.getId().toString());
			respCode.setLastUpdateTs(code.getLastUpdateTs().toString());
			respCode.setLastUpdateTxId(code.getLastUpdateTxId().toString());
			respCode.setLastUpdateUser(code.getLastUpdateUser());
			respCode.setName(code.getName());
			respCode.setOwnerId(code.getOwnerId().toString());
			respCode.setStartDate(code.getStartDate());
			output.add(respCode);
		}
		
		response.setResult(result);
		response.setOutput(output);
		
		return null;
	}
	
	/**
	 * 入力チェック
	 * 
	 * @param criteria
	 * @return
	 */
	private Result validate (CodeCriteriaObj criteria) {
		
		Result result = new Result();
		
		// 入力値がない場合エラー
		if (criteria == null) {
			result.setStatus(Status.BAD_REQUEST_VALUE);
			return result;
		}
		
		
		
		return result;
	}

}
