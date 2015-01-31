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

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * コードカテゴリーを無効にする。<br/>
 * <br/>
 *
 * @author mokku
 */
@Service
public class CodeCategoryInactiveActionImpl implements
		CodeCategoryInactiveAction {

	@Autowired
	private CodeCategoryMapper codeCategoryMapper;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public CodeCategoryServiceResponse perform(Control control,
			CodeCategoryObj input) {
		CodeCategoryServiceResponse response = new CodeCategoryServiceResponse();
		response.setControl(control);

		// 入力チェックを行う。
		Result result = validate(input);
		if (result.getStatus() != Status.SUCCESS) {
			response.setResult(result);
			return response;
		}

		// 無効対象のキー項目をセットする。
		CodeCategory codeCategory = new CodeCategory();
		codeCategory.setId(Long.valueOf(input.getId()));
		codeCategory
				.setLastUpdateTs(Timestamp.valueOf(input.getLastUpdateTs()));
		codeCategory.setLastUpdateUser(control.getRequesterName());
		codeCategory
				.setLastUpdateTxId(Long.valueOf(control.getTransactionId()));

		// 無効にする。
		if (codeCategoryMapper.inactive(codeCategory) == 0) {
			// 無効にする処理に失敗した場合
			result.setStatus(Status.EXCEPTION_CONFLICT);
			throw new ConflictRequestException(result);
		}

		// 再検索を行う。
		CodeCategory category = codeCategoryMapper.select(Long.valueOf(input.getId()));
		CodeCategoryObj obj = new CodeCategoryObj();
		obj.setId(category.getId().toString());
		obj.setOwnerId(category.getOwnerId().toString());
		obj.setName(category.getName().toString());
		obj.setInactiveTs(category.getInactiveTs().toString());
		obj.setLastUpdateTs(category.getLastUpdateTs().toString());
		obj.setLastUpdateTxId(category.getLastUpdateTxId().toString());
		obj.setLastUpdateUser(category.getLastUpdateUser().toString());

		List<CodeCategoryObj> output = new ArrayList<CodeCategoryObj>();
		output.add(obj);

		response.setOutput(output);
		response.setResult(result);
		return response;
	}

	/**
	 * 入力チェックを行う。<br/>
	 * <br/>
	 * @param input
	 * @return
	 */
	private Result validate(CodeCategoryObj input) {
		Result result = new Result();

		if (input == null) {
			result.setStatus(Status.BAD_REQUEST_VALUE);
			return result;
		}

		if (StringUtils.isEmpty(input.getId())) {
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
