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
import jp.co.cos_mos.mdm.v1.service.domain.entity.CodeDiffResultObj;
import jp.co.cos_mos.mdm.v1.service.domain.entity.CodeObj;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class CodeGetDifferenceActionImpl implements CodeGetDifferenceAction {

	@Autowired
	private CodeMapper codeMapper;
	
	// 差分ステータス
	private final String DIFF_STATS_DELETE = "DELETE";
	private final String DIFF_STATS_INSERT = "INSERT";
	private final String DIFF_STATS_UPDATE = "UPDATE";
	
	@Transactional(readOnly=true)
	public CodeServiceResponse perform(Control control, CodeCriteriaObj criteria) {
		
		// 返却オブジェクト生成
		CodeServiceResponse response = new CodeServiceResponse();
		response.setControl(control);
		
		// 入力チェック
		Result result = validate(criteria);
		if (result.getStatus() == Status.SUCCESS) {
			result.setStatus(Status.BAD_REQUEST_VALUE);
			return response;
		}
		
		// 入力情報
		CodeCriteria codeCriteria = new CodeCriteria();
		codeCriteria.setOwnerId(Long.parseLong(criteria.getOwnerId()));
		codeCriteria.setCategoryId(Long.parseLong(criteria.getCategoryId()));
		codeCriteria.setBaseDate(criteria.getBaseDate());
		codeCriteria.setTargetDate(criteria.getTargetDate());
		
		// baseDateを元に検索
		List <Code> baseCodeList = new ArrayList<Code>(); 
		baseCodeList = codeMapper.search(codeCriteria);
		
		// 検索結果0件
		if (baseCodeList.size() == 0) {
			result.setStatus(Status.DATA_NOT_FOUND);
			return response;
		}
		
		// targetDateを元に検索
		List <Code> targetCodeList = new ArrayList<Code>(); 
		targetCodeList = codeMapper.search(codeCriteria);

		int baseIndex = 0;
		int targetIndex = 0;
		int baseEndIndex = baseCodeList.size();
		int targetEndIndex = targetCodeList.size();
		
		List<CodeDiffResultObj> diffResultList = new ArrayList<CodeDiffResultObj>();
		
		while (true) {
			CodeDiffResultObj codeDiffResultObj = new CodeDiffResultObj();
			
			// base,targeListの中身を全て検証し終える
			if (baseIndex >= baseEndIndex && targetIndex >= targetEndIndex) {
				break;
			}
			
			Code baseCode = baseCodeList.get(baseIndex);
			String bCode = baseCode.getCode();
			
			Code targetCode = targetCodeList.get(targetIndex);
			String tCode = targetCode.getCode();
			
			
			
			if (bCode.compareTo(tCode) == 0) {
				
				// 更新があった場合
				if (StringUtils.isNotEmpty(baseCode.getName()) && !baseCode.getName().equals(targetCode.getName())) {
					codeDiffResultObj.setBaseCode(codeToCodeObj(baseCode));
					codeDiffResultObj.setTargetCode(codeToCodeObj(targetCode));
					codeDiffResultObj.setDiffStatus(DIFF_STATS_UPDATE);
					
				}
				baseIndex++;
				targetIndex++;
				
			
			} else if (bCode.compareTo(tCode) > 0) {
				codeDiffResultObj.setBaseCode(codeToCodeObj(baseCode));
				codeDiffResultObj.setTargetCode(codeToCodeObj(targetCode));
				codeDiffResultObj.setDiffStatus(checkStatus(criteria.getBaseDate(), criteria.getTargetDate()));
				targetIndex++;
				
			} else if (bCode.compareTo(tCode) < 0) {
				codeDiffResultObj.setBaseCode(codeToCodeObj(baseCode));
				codeDiffResultObj.setTargetCode(codeToCodeObj(targetCode));
				codeDiffResultObj.setDiffStatus(checkStatus(criteria.getBaseDate(), criteria.getTargetDate()));
				baseIndex++;
			}
			
			diffResultList.add(codeDiffResultObj);
		}
		
		response.setDiffResultList(diffResultList);
		return response;
	}
	
	/**
	 * 入力チェック
	 * @param criteria
	 * @return
	 */
	private Result validate (CodeCriteriaObj criteria) {
		
		Result result = new Result();
		
		if (criteria == null) {
			result.setStatus(Status.BAD_REQUEST_VALUE);
			return result;
		}
		
		if (criteria.getBaseDate() == null) {
			result.setStatus(Status.BAD_REQUEST_VALUE);
			return result;
		}
		
		if (criteria.getTargetDate() == null) {
			result.setStatus(Status.BAD_REQUEST_VALUE);
			return result;
		}
		
		if (criteria.getOwnerId() == null && StringUtils.isNumeric(criteria.getOwnerId())) {
			result.setStatus(Status.BAD_REQUEST_VALUE);
			return result;
		}
		
		if (criteria.getCategoryId() == null && StringUtils.isNumeric(criteria.getCategoryId())) {
			result.setStatus(Status.BAD_REQUEST_VALUE);
			return result;
		}
		
		return result;
	}
	
	/**
	 * CodeオブジェクトからCodeObjオブジェクトに値を入れ替える
	 * 
	 * @param code
	 * @return
	 */
	private CodeObj codeToCodeObj (Code code) {
		CodeObj codeObj = new CodeObj();
		codeObj.setId(String.valueOf(code.getId()));
		codeObj.setCategoryId(String.valueOf(code.getCategoryId()));
		codeObj.setOwnerId(String.valueOf(code.getOwnerId()));
		codeObj.setName(code.getName());
		codeObj.setCode(code.getCode());
		codeObj.setLastUpdateTs(String.valueOf(code.getLastUpdateTs()));
		codeObj.setLastUpdateUser(code.getLastUpdateUser());
		codeObj.setLastUpdateTxId(String.valueOf(code.getLastUpdateTxId()));
		codeObj.setStartDate(code.getStartDate());
		codeObj.setEndDate(code.getEndDate());
		return codeObj;
	}
	
	/**
	 * 日付を比較し、差分ステータスを判断する
	 * @param baseDate
	 * @param targetDate
	 * @return
	 */
	private String checkStatus (String baseDate, String targetDate) {
		String status = "";
		
		// baseDateの方が小さい
		if (baseDate.compareTo(targetDate) < 0) {
			status = DIFF_STATS_INSERT;
		} else {
			status = DIFF_STATS_DELETE;
		}
		return status;
	}

}
