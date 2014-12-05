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
import jp.co.cos_mos.mdm.v1.dao.entity.Code;
import jp.co.cos_mos.mdm.v1.dao.entity.CodeCriteria;
import jp.co.cos_mos.mdm.v1.dao.mapper.CodeMapper;
import jp.co.cos_mos.mdm.v1.service.domain.CodeServiceCriteria;
import jp.co.cos_mos.mdm.v1.service.domain.CodeServiceRequest;
import jp.co.cos_mos.mdm.v1.service.domain.CodeServiceResponse;
import jp.co.cos_mos.mdm.v1.service.domain.entity.CodeObj;

import org.springframework.beans.factory.annotation.Autowired;

public class CodeServiceImpl implements CodeService {

	@Autowired
	private SequenceIdentifierMapper sequenceIdentifierMapper;

	@Autowired
	private SequenceNumberServiceNumbering numberingService;

	@Autowired
	private CodeMapper codeMapper;

	private final String EMPTY = "";
	
	public CodeServiceResponse create(CodeServiceRequest request) {

		CodeServiceResponse response = new CodeServiceResponse();

		Result result = validateForCreate(request);

		List<CodeObj> codeObjList = request.getInput();
		Code code = new Code();
		Control control = request.getControl();
		SequenceIdentifier seqIdent =
				sequenceIdentifierMapper.select(code.getClass().getName());

		// 件数分登録を行う
		for (CodeObj codeObj : codeObjList) {
			// idを取得
			Long id = numberingService.perform(seqIdent.getSequenceId());

			code.setId(id);
			code.setOwnerId(Long.parseLong(codeObj.getOwnerId()));
			code.setCategoryId(Long.parseLong(codeObj.getCategoryId()));
			code.setCode(codeObj.getCode());
			code.setName(codeObj.getName());
			code.setStartDate(codeObj.getStartDate());
			code.setEndDate(codeObj.getEndDate());
			code.setLastUpdateUser(control.getRequesterName());
			code.setLastUpdateTxId(control.getTransactionId());

			// 登録
			int insertCount = codeMapper.insert(code);

			// 登録に失敗
			if (insertCount == 0) {
				result = new Result();
			}
		}

		// 定義チェック
		getCheckResult(request);

		response.setResult(result);
		response.setControl(request.getControl());

		return response;
	}

	public CodeServiceResponse update(CodeServiceRequest request) {
		CodeServiceResponse response = new CodeServiceResponse();
		Control control  = request.getControl();
		response.setControl(control);

		Result result = validate(request);
		int count = 0;
		
		// TODO check
		if (!result.getStatus().equals("エラー")) {
			
			for (CodeObj codeObj : request.getInput()) {
				Code code = new Code();
				code.setCategoryId(Long.valueOf(codeObj.getCategoryId()));
				code.setId(Long.valueOf(codeObj.getId()));
				code.setName(codeObj.getName());
				code.setStartDate(codeObj.getStartDate());
				code.setEndDate(codeObj.getEndDate());
				code.setLastUpdateTs(Timestamp.valueOf(codeObj.getLastUpdateTs()));
				code.setLastUpdateUser(control.getRequesterName());
				code.setLastUpdateTxId(control.getTransactionId());
				
				if (codeMapper.update(code) > 0) {
					count++;
//					result.setMessage;
				} else {
//					result.setMessage;
					break;
				}
			}
			getCheckResult(request);
			response = getCode(request);
		}
		response.setCount(count);
		response.setResult(result);
		return response;
	}

	public CodeServiceResponse delete(CodeServiceRequest request) {
		CodeServiceResponse response = new CodeServiceResponse();

		Result result = validateForDelete(request);
		int count = 0;

		if (!result.getStatus().equals("error")) {
			//Status status = new Status();
			Message messagge = new Message();

			for (CodeObj codeObj : request.getInput()) {
				Code code = new Code();
				code.setCategoryId(Long.valueOf(codeObj.getCategoryId()));
				code.setCode(codeObj.getCode());
				code.setId(Long.valueOf(codeObj.getId()));
				code.setLastUpdateTs(Timestamp.valueOf(codeObj.getLastUpdateTs()));

				if (codeMapper.delete(code) > 0) {
					count++;
				} else {
					//失敗
					//result.setStatus();
					//result.setMessage();
					break;
				}
			}
			//成功
			//result.setStatus();
			//result.setMessage();
		}

		getCheckResult(request);
		response = getCodes(request);
		response.setCount(count);
		response.setResult(result);

		return response;
	}

	public CodeServiceResponse importAll(CodeServiceRequest request) {
		CodeServiceResponse response = new CodeServiceResponse();

		Result result = validateForImportAll(request);

		List<CodeObj> codeObjList = request.getInput();
		Code code = new Code();
		Control control = request.getControl();
		SequenceIdentifier seqIdent =
				sequenceIdentifierMapper.select(code.getClass().getName());

		// 件数分登録を行う
		for (CodeObj codeObj : codeObjList) {
			Long id = numberingService.perform(seqIdent.getSequenceId());

			code.setId(id);
			code.setOwnerId(Long.parseLong(codeObj.getOwnerId()));
			code.setCategoryId(Long.parseLong(codeObj.getCategoryId()));
			code.setCode(codeObj.getCode());
			code.setName(codeObj.getName());
			code.setStartDate(codeObj.getStartDate());
			code.setEndDate(codeObj.getEndDate());
			code.setLastUpdateUser(control.getRequesterName());
			code.setLastUpdateTxId(control.getTransactionId());

			// 登録
			int insertCount = codeMapper.insert(code);

			// 登録に失敗
			if (insertCount == 0) {
				result = new Result();
			}
		}

		response.setResult(result);
		response.setControl(request.getControl());

		return response;
	}

	public CodeServiceResponse getCode(CodeServiceRequest request) {
		// 返却するデータ
		CodeServiceResponse response = new CodeServiceResponse();
		response.setControl(request.getControl());
		
		// エラーチェック(未実装)
		Result result = validateForSearch(request);
		response.setResult(result);
		
		// ID取得
		List<CodeObj> codeObjList = new ArrayList<CodeObj>();
		codeObjList = request.getInput();
		CodeObj inCodeObj = codeObjList.get(0);
		
		// 詳細画面の内容を取得
		Code newCode = new Code();
		newCode = codeMapper.select(Long.parseLong(inCodeObj.getId()));
		
		// 返却データ作成
		CodeObj outCodeObj = new CodeObj();
		outCodeObj.setId(String.valueOf(newCode.getId()));
		outCodeObj.setOwnerId(String.valueOf(newCode.getOwnerId()));
		outCodeObj.setCategoryId(String.valueOf(newCode.getCategoryId()));
		outCodeObj.setCode(newCode.getCode());
		outCodeObj.setName(newCode.getName());
		outCodeObj.setStartDate(newCode.getStartDate());
		outCodeObj.setEndDate(newCode.getEndDate());
		outCodeObj.setLastUpdateTs(String.valueOf(newCode.getLastUpdateTs()));
		outCodeObj.setLastUpdateUser(newCode.getLastUpdateUser());
		outCodeObj.setLastUpdateTxId(String.valueOf(newCode.getLastUpdateTxId()));
		List<CodeObj> output = new ArrayList<CodeObj>();		
		
		response.setOutput(output);
		return response;
	}

	public CodeServiceResponse getCodes(CodeServiceRequest request) {
		
		// 返却するデータ
		CodeServiceResponse response = new CodeServiceResponse();
		response.setControl(request.getControl());
		
		// エラーチェック
		Result result = validateForSearch(request);
		response.setResult(result);
		
		// 抽出条件取得
		CodeServiceCriteria codeServiceCritera = request.getCriteria();
		
		// 有効なコードの取得
		List<Code> codeList = new ArrayList<Code>();
		// TODO codeList = codeMapper.search(codeServiceCritera);
		
		// 返却用オブジェクト作成
		List<CodeObj> codeObjList = new ArrayList<CodeObj>();
		for (int i = 0; i < codeList.size(); i++) {
			Code code = new Code();
			code = codeList.get(i);
			CodeObj respCode = new CodeObj();
			respCode.setId(String.valueOf(code.getId()));
			respCode.setOwnerId(String.valueOf(code.getOwnerId()));
			respCode.setCategoryId(String.valueOf(code.getCategoryId()));
			respCode.setCode(code.getCode());
			respCode.setName(code.getName());
			respCode.setStartDate(code.getStartDate());
			respCode.setEndDate(code.getEndDate());
			respCode.setLastUpdateTs(String.valueOf(code.getLastUpdateTs()));
			respCode.setLastUpdateUser(code.getLastUpdateUser());
			respCode.setLastUpdateTxId(String.valueOf(code.getLastUpdateTxId()));
			codeObjList.add(respCode);
		}
		
		response.setOutput(codeObjList);
		return response;
	}

	public CodeServiceResponse getCodeDetail(CodeServiceRequest request) {

		CodeServiceResponse response = new CodeServiceResponse();

		Result result = validate(request);

		CodeCriteria codeCriteria = new CodeCriteria();

		codeCriteria.setCode(request.getCriteria().getCode());
		List<Code> inList = codeMapper.search(codeCriteria);

		List<CodeObj> outList = new ArrayList();

		for(int i = 0; i < inList.size() ;i++){
			CodeObj codeObj = new CodeObj();
			codeObj.setCode(inList.get(i).getCode());
			codeObj.setId(String.valueOf(inList.get(i).getId()));
			codeObj.setOwnerId(String.valueOf(inList.get(i).getOwnerId()));
			codeObj.setCategoryId(String.valueOf(inList.get(i).getCategoryId()));
			codeObj.setName(inList.get(i).getName());
			codeObj.setStartDate(inList.get(i).getStartDate());
			codeObj.setEndDate(inList.get(i).getEndDate());
			codeObj.setLastUpdateUser(inList.get(i).getLastUpdateUser());
			codeObj.setLastUpdateTxId(String.valueOf(inList.get(i).getLastUpdateTxId()));
			codeObj.setLastUpdateTs(String.valueOf(inList.get(i).getLastUpdateTs()));
			outList.add(codeObj);
		}

		response.setOutput(outList);
		response.setResult(result);

		return response;
	}

	public CodeServiceResponse getCheckResult(CodeServiceRequest request) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return null;
	}

	public CodeServiceResponse getDifference(CodeServiceRequest request) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return null;
	}

	private Result validate(CodeServiceRequest request) {

		return new Result();
	}
	
	public Result validateForSearch(CodeServiceRequest request){
		
		CodeServiceCriteria codeCriteria = new CodeServiceCriteria();
		Long categoryId = codeCriteria.getCategoryId();
		int sort = codeCriteria.getSort();
		String baseDate = codeCriteria.getBaseDate();
		String xDate = codeCriteria.getxDate();
		String yDate = codeCriteria.getyDate();
		
		Result result = new Result();
		
		// カテゴリーID必須チェック
		if (categoryId == null) {
		
		// ソートキーが想定以外
		} else if (!(sort == 1 || sort == 2 || sort == 3)) {
		
		// 
		} else if (baseDate != null && xDate != null) {
		
		// 
		} else if (baseDate != null && yDate != null) {
			
		// 
		} else if (xDate != null && yDate == null) {
		
		// 
		} else if (xDate == null && yDate != null) {
			
		}

		return result;
	}
	private Result validateForDelete(CodeServiceRequest request) {
		Result result = new Result();
		//Status status = new Status();
		Message message = new Message();
		for (CodeObj codeObj : request.getInput()) {
			if (isNullOrEmpty(codeObj.getCategoryId())) {
				// status
				// message
				break;
			}
			if (isNullOrEmpty(codeObj.getCode())) {
				// status
				// message
				break;
			}
			if (isNullOrEmpty(codeObj.getId())) {
				// status
				// message
				break;
			}
			if (isNullOrEmpty(codeObj.getLastUpdateTs())) {
				// status
				// message
				break;
			}
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
	private Result validateForCreate(CodeServiceRequest request) {
		// TODO 中身
		return new Result();
	}

	/**
	 * バリデートメソッド for importAll
	 * @param request
	 * @return Result
	 */
	private Result validateForImportAll(CodeServiceRequest request) {
		// TODO 中身
		return new Result();
	}
}
