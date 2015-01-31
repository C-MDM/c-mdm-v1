package jp.co.cos_mos.mdm.v1.service.action;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jp.co.cos_mos.mdm.core.service.domain.entity.Control;
import jp.co.cos_mos.mdm.core.service.domain.entity.Status;
import jp.co.cos_mos.mdm.v1.dao.entity.Code;
import jp.co.cos_mos.mdm.v1.dao.entity.CodeCriteria;
import jp.co.cos_mos.mdm.v1.dao.mapper.CodeMapper;
import jp.co.cos_mos.mdm.v1.service.domain.CodeServiceResponse;
import jp.co.cos_mos.mdm.v1.service.domain.entity.CodeCriteriaObj;
import jp.co.cos_mos.mdm.v1.service.domain.entity.CodeObj;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CodeGetCodesActionTest {

	private Control control;
	private CodeCriteriaObj criteria;
	
	@InjectMocks
	private CodeGetCodesAction target = new CodeGetCodesActionImpl();
	
	@Mock
	private CodeMapper codeMapper;
	
	@Before
	public void setup() {
		control = new Control();
		control.setRequesterName("ut");
		control.setTransactionId(Math.abs(UUID.randomUUID().getLeastSignificantBits()));
		
		MockitoAnnotations.initMocks(this);
	}
	
	/**
	 * コードリスト取得(正常系)_001<br />
	 * 入力項目が全て正しく入力されたとき正常終了することを確認する<br />
	 * <br />
	 * <br />
	 * 入力値：
	 * 戻り値：
	 */
	@Test
	public void testParform_SUCCESS001() {
		String ownerId = "2";
		String categoryId = "3";
		String sort = "1";
		
		// テスト対象クラスに渡す情報
		criteria = new CodeCriteriaObj();
		criteria.setOwnerId(ownerId);
		criteria.setCategoryId(categoryId);
		criteria.setSort(sort);
		
		// Mock(テストクラスから呼ばれるクラスとか今回はMapper)に渡す情報
		CodeCriteria codeCriteria = new CodeCriteria();
		codeCriteria.setOwnerId(Long.parseLong(ownerId));
		codeCriteria.setCategoryId(Long.parseLong(categoryId));
		codeCriteria.setSort(Integer.parseInt(sort));
		
		// Mockの返却値
		Code code1 = new Code();
		code1.setId(Long.parseLong("1"));
		code1.setOwnerId(Long.parseLong(ownerId));
		code1.setCategoryId(Long.parseLong(categoryId));
		code1.setCode("10000101");
		code1.setName("ut_name");
		code1.setStartDate("2015/01/31");
		code1.setEndDate("2015/02/31");
		code1.setLastUpdateTs(Timestamp.valueOf("2014-11-30 18:33:12.123456"));
		code1.setLastUpdateUser("ut_updateUser");
		code1.setLastUpdateTxId(Long.parseLong("2"));
		
		List <Code> codeList = new ArrayList<Code>();
		codeList.add(code1);
		
//		when(codeMapper.search(codeCriteria)).thenReturn(codeList);
		when(codeMapper.search(anyObject())).thenReturn(codeList);

		CodeServiceResponse response = target.perform(control, criteria);
		
		// response自体の検証
		assertTrue(this.control.equals(response.getControl()));
		assertTrue(response.getResult() != null);
		assertTrue(Status.SUCCESS.equals(response.getResult().getStatus()));
		assertTrue(response.getOutput() != null);
		assertTrue(response.getOutput().size() == 1);
		
		// response内のデータの検証
		CodeObj output1 = response.getOutput().get(0);
		assertTrue(output1.getId().equals("1"));
		assertTrue(output1.getOwnerId().equals(ownerId));
		assertTrue(output1.getCategoryId().equals(categoryId));
		assertTrue(output1.getCode().equals("10000101"));
		assertTrue(output1.getName().equals("ut_name"));
		assertTrue(output1.getStartDate().equals("2015/01/31"));
		assertTrue(output1.getEndDate().equals("2015/02/31"));
		assertTrue(output1.getLastUpdateTs().equals("2014-11-30 18:33:12.123456"));
		assertTrue(output1.getLastUpdateTxId().equals("2"));
		assertTrue(output1.getLastUpdateUser().equals("ut_updateUser"));
	}
	
	/**
	 * コードリスト取得処理_002(異常系)<br />
	 * ownerIdがnullの場合、BAD_REQUEST_VALUEが返ること確認する<br />
	 * <br />
	 * <br />
	 * 入力値：
	 * 戻り値：
	 */
	public void testParform_BAT_REQUEST_VALUE001() {
		String ownerId = null;
		String categoryId = "3";
		String sort = "1";
		
		// テスト対象クラスに渡す情報
		criteria = new CodeCriteriaObj();
		criteria.setOwnerId(ownerId);
		criteria.setCategoryId(categoryId);
		criteria.setSort(sort);
		
		// Mockの返却値
		Code code1 = new Code();
		code1.setId(Long.parseLong("1"));
		code1.setOwnerId(Long.parseLong(ownerId));
		code1.setCategoryId(Long.parseLong(categoryId));
		code1.setCode("10000101");
		code1.setName("ut_name");
		code1.setStartDate("2015/01/31");
		code1.setEndDate("2015/02/31");
		code1.setLastUpdateTs(Timestamp.valueOf("2014-11-30 18:33:12.123456"));
		code1.setLastUpdateUser("ut_updateUser");
		code1.setLastUpdateTxId(Long.parseLong("2"));
	}
}
