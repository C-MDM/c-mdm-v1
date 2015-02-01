package jp.co.cos_mos.mdm.v1.service.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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

import java.util.ArrayList;
import java.util.List;

/**
 *  2015/1/30
 *
 * @author 福島圭佑
 *
 */
public class CodeGetCodeDetailActionImpl implements CodeGetCodeDetailAction {


	@Autowired
	private CodeMapper codeMapper;


	@Transactional(readOnly = true)
	public CodeServiceResponse perform(Control control, CodeCriteriaObj criteria) {

		CodeServiceResponse response = new CodeServiceResponse();
		response.setControl(control);

		Result result = validate(criteria);
		if (result.getStatus() != Status.SUCCESS) {
			response.setResult(result);
			return response;
		} else {
			response.setResult(result);
		}


		CodeCriteria codeCriteria = new CodeCriteria();
		codeCriteria.setCategoryId(Long.parseLong(criteria.getCategoryId()));
		codeCriteria.setOwnerId(Long.parseLong(criteria.getOwnerId()));
		codeCriteria.setCode(criteria.getCode());

		List<Code> inList = codeMapper.search(codeCriteria);

		List<CodeObj> outList = new ArrayList();

		for (Code searchResult : inList) {
			CodeObj codeObj = new CodeObj();
			codeObj.setCode(searchResult.getCode());
			codeObj.setId(String.valueOf(searchResult.getId()));
			codeObj.setOwnerId(String.valueOf(searchResult.getOwnerId()));
			codeObj.setCategoryId(String.valueOf(searchResult.getCategoryId()));
			codeObj.setName(searchResult.getName());
			codeObj.setStartDate(searchResult.getStartDate());
			codeObj.setEndDate(searchResult.getEndDate());
			codeObj.setLastUpdateUser(searchResult.getLastUpdateUser());
			codeObj.setLastUpdateTxId(String.valueOf(searchResult
					.getLastUpdateTxId()));
			codeObj.setLastUpdateTs(String.valueOf(searchResult
					.getLastUpdateTs()));
			outList.add(codeObj);
		}

		response.setResult(result);
		response.setOutput(outList);

		return response;
	}


	private Result validate(CodeCriteriaObj criteria) {
		Result result = new Result();


		if (criteria == null) {
			result.setStatus(Status.BAD_REQUEST_VALUE);
			return result;
		}

		if (StringUtils.isEmpty(criteria.getOwnerId())
				|| !StringUtils.isNumeric(criteria.getOwnerId())) {
			result.setStatus(Status.BAD_REQUEST_VALUE);
		}

		if (StringUtils.isEmpty(criteria.getCategoryId())
				|| !StringUtils.isNumeric(criteria.getCategoryId())) {
			result.setStatus(Status.BAD_REQUEST_VALUE);
		}

		if (StringUtils.isEmpty(criteria.getCode())) {
			result.setStatus(Status.BAD_REQUEST_VALUE);
		}
		return result;
	}

}
