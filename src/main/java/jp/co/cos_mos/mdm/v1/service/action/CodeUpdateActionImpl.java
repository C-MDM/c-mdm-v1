package jp.co.cos_mos.mdm.v1.service.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import jp.co.cos_mos.mdm.core.service.domain.entity.Control;
import jp.co.cos_mos.mdm.core.service.domain.entity.Result;
import jp.co.cos_mos.mdm.core.service.domain.entity.Status;
import jp.co.cos_mos.mdm.core.service.exception.ConflictRequestException;
import jp.co.cos_mos.mdm.v1.dao.entity.Code;
import jp.co.cos_mos.mdm.v1.dao.mapper.CodeMapper;
import jp.co.cos_mos.mdm.v1.service.domain.CodeServiceResponse;
import jp.co.cos_mos.mdm.v1.service.domain.entity.CodeObj;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CodeUpdateActionImpl implements CodeUpdateAction {
	@Autowired
	private CodeMapper codeMapper;

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public CodeServiceResponse perform(Control control, CodeObj input) {
		
		CodeServiceResponse response = new CodeServiceResponse();
		List<CodeObj> codeObjList = new  ArrayList<CodeObj>();
		response.setControl(control);
		Result result = validate(input);
		
		// 入力チェック
		if (!result.getStatus().equals(Status.SUCCESS)) {
			response.setResult(result);
			return response;
		} else {
			
			// 更新する値をセット
			Code code = new Code();
			code.setId(Long.valueOf(input.getId()));
			code.setOwnerId(Long.valueOf(input.getOwnerId()));
			code.setCategoryId(Long.valueOf(input.getCategoryId()));
			code.setCode(input.getCode());
			code.setName(input.getName());
			code.setStartDate(input.getStartDate());
			code.setEndDate(input.getEndDate());
			code.setLastUpdateTs(Timestamp.valueOf(input.getLastUpdateTs()));
			code.setLastUpdateUser(control.getRequesterName());
			code.setLastUpdateTxId(control.getTransactionId());
			
			// 更新実施
			if (codeMapper.update(code) == 0) {
				result.setStatus(Status.EXCEPTION_CONFLICT);
				throw new ConflictRequestException(result);
			}
			
			// 更新結果再検索
			code = codeMapper.select(Long.valueOf(input.getId()));
			CodeObj codeObj = new CodeObj();
			
			codeObj.setId(String.valueOf(code.getId()));
			codeObj.setOwnerId(String.valueOf(code.getOwnerId()));
			codeObj.setCategoryId(String.valueOf(code.getCategoryId()));
			codeObj.setCode(code.getCode());
			codeObj.setName(code.getName());
			codeObj.setStartDate(code.getStartDate());
			codeObj.setEndDate(code.getEndDate());
			codeObj.setLastUpdateTs(String.valueOf(code.getLastUpdateTs()));
			codeObj.setLastUpdateUser(code.getLastUpdateUser());
			codeObj.setLastUpdateTxId(String.valueOf(code.getLastUpdateTxId()));
			codeObjList.add(codeObj);
		}
		
		response.setOutput(codeObjList);
		response.setResult(result);
		return response;
	}

	private Result validate(CodeObj input) {
		Result result = new Result();
		
		if (input == null) {
			result.setStatus(Status.BAD_REQUEST_VALUE);
			return result;
		}
		
		// nameのチェック
		if (StringUtils.isEmpty(input.getName())) {
			result.setStatus(Status.BAD_REQUEST_VALUE);
		}
		
		return result;
		
	}

}
