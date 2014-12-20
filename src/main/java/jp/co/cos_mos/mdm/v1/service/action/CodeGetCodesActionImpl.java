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

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class CodeGetCodesActionImpl implements CodeGetCodesAction {

	@Autowired
	private CodeMapper codeMappaer;
	
	@Transactional(readOnly=true)
	public CodeServiceResponse perform(Control control, CodeCriteriaObj criteria) {
		CodeServiceResponse response = new CodeServiceResponse();
		response.setControl(control);
		
		Result result = validate (criteria);
		if (result.getStatus() != Status.SUCCESS) {
			response.setResult(result);
			return response;
		}
		
		CodeCriteria tmpCriteria = new CodeCriteria();
		tmpCriteria.setBaseDate(criteria.getBaseDate());
		tmpCriteria.setCategoryId(Long.parseLong(criteria.getCategoryId()));
		tmpCriteria.setOwnerId(Long.parseLong(criteria.getOwnerId()));
		tmpCriteria.setSort(Integer.parseInt(criteria.getSort()));
		
		List<Code> selectCodes = new ArrayList<Code>();
		selectCodes = codeMappaer.search(tmpCriteria);
		if (selectCodes == null) {
			result.setStatus(Status.DATA_NOT_FOUND);
			return response;
		}
		
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
		
		response.setOutput(output);
		response.setResult(result);
		
		return null;
	}

	private Result validate (CodeCriteriaObj criteria) {
		
		Result result = new Result();
		
		if (criteria == null) {
			result.setStatus(Status.BAD_REQUEST_VALUE);
			return result;
		}
		
		if (StringUtils.isEmpty(criteria.getBaseDate())) {
			result.setStatus(Status.BAD_REQUEST_VALUE);
			return result;
		}
		
		if (StringUtils.isEmpty(criteria.getCategoryId()) || StringUtils.isNumeric(criteria.getCategoryId())) {
			result.setStatus(Status.BAD_REQUEST_VALUE);
			return result;
		}
		
		if (StringUtils.isEmpty(criteria.getOwnerId()) || StringUtils.isNumeric(criteria.getOwnerId())) {
			result.setStatus(Status.BAD_REQUEST_VALUE);
			return result;
		}
		
		return result;
	}
}
