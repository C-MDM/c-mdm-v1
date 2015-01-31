package jp.co.cos_mos.mdm.v1.service.action;

import java.sql.Timestamp;

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

/**
 * コードを物理削除する。 <br/>
 * <br/>
 * @author mokku
 */
@Service
public class CodeDeleteActionImpl implements CodeDeleteAction {

	@Autowired
	private CodeMapper codeMapper;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public CodeServiceResponse perform(Control control, CodeObj input) {
		CodeServiceResponse response = new CodeServiceResponse();
		response.setControl(control);

		// 入力チェックを行う。
		Result result = validate(input);
		if (result.getStatus() != Status.SUCCESS) {
			response.setResult(result);
			return response;
		}

		// 削除対象のキー項目をセット。
		Code code = new Code();
		code.setId(Long.valueOf(input.getId()));
		code.setLastUpdateTs(Timestamp.valueOf(input.getLastUpdateTs()));

		// 削除する。
		if (codeMapper.delete(code) == 0) {
			// 削除処理に失敗した場合
			result.setStatus(Status.EXCEPTION_CONFLICT);
			throw new ConflictRequestException(result);
		}

		response.setResult(result);
		return response;
	}

	/**
	 * 入力チェックを行う。<br/>
	 * <br/>
	 * @param input
	 * @return
	 */
	private Result validate(CodeObj input) {
		Result result = new Result();

		if (input == null) {
			result.setStatus(Status.BAD_REQUEST_VALUE);
			return result;
		}

		if (StringUtils.isEmpty(input.getId()) || !StringUtils.isNumeric(input.getId())) {
			result.setStatus(Status.BAD_REQUEST_VALUE);
			return result;
		}

		if (input.getLastUpdateTs() == null) {
			result.setStatus(Status.BAD_REQUEST_VALUE);
			return result;
		}

		return result;
	}
}
