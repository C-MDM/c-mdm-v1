package jp.co.cos_mos.mdm.v1.service.action;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.sql.Timestamp;
import java.util.UUID;

import jp.co.cos_mos.mdm.core.service.domain.entity.Control;
import jp.co.cos_mos.mdm.core.service.domain.entity.Status;
import jp.co.cos_mos.mdm.core.service.exception.ConflictRequestException;
import jp.co.cos_mos.mdm.v1.dao.entity.Code;
import jp.co.cos_mos.mdm.v1.dao.mapper.CodeMapper;
import jp.co.cos_mos.mdm.v1.service.domain.CodeServiceResponse;
import jp.co.cos_mos.mdm.v1.service.domain.entity.CodeObj;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CodeUpdateActionTset {
	private Control control;
	private CodeObj input;

	@InjectMocks
	private CodeUpdateActionImpl target = new CodeUpdateActionImpl();
	
	@Mock
	private CodeMapper codeMapper;
	
	
	@Before
	public void setUp() throws Exception {
		control = new Control();
		control.setRequesterName("ut");
		control.setTransactionId(Math.abs(UUID.randomUUID().getLeastSignificantBits()));
		
		MockitoAnnotations.initMocks(this);
	}

	/**
	 * <p><b>更新処理（正常系）</b></p>
	 * すべての項目に正しい値が入力されているときに<br>
	 * 各項目が入力値で更新されていることを確認する。<br>
	 * 
	 * 入力値：各項目<br>
	 * 戻り値：ステータスがSUCCESS
	 */
	@Test
	public void testParform_SUCCESS001() {
		Long id = 1L;
		String startDate = "2000-12-01 00:11:13.654321";
		String endDate = "2099-12-31 23:59:59.999999";
		String lastUpdateTs = "2014-11-29 18:33:12.123456";
		
		input = new CodeObj();
		input.setId(String.valueOf(id));
		input.setOwnerId("6");
		input.setCategoryId("3");
		input.setCode("12");
		input.setName("updateCodeName");
		input.setStartDate(startDate);
		input.setEndDate(endDate);
		input.setLastUpdateTs(lastUpdateTs);
		
		when(codeMapper.update(anyObject())).thenReturn(1);

		Code updateCode = new Code();
		updateCode.setId(Long.valueOf(input.getId()));
		updateCode.setCategoryId(Long.valueOf(input.getCategoryId()));
		updateCode.setOwnerId(Long.valueOf(input.getOwnerId()));
		updateCode.setCode(input.getCode());
		updateCode.setName(input.getName());
		updateCode.setStartDate(input.getStartDate());
		updateCode.setEndDate(input.getEndDate());
		updateCode.setLastUpdateTs(new Timestamp(System.currentTimeMillis()));
		updateCode.setLastUpdateUser(control.getRequesterName());
		updateCode.setLastUpdateTxId(control.getTransactionId());
		
		when(codeMapper.select(id)).thenReturn(updateCode);
		
		CodeServiceResponse response = target.perform(control, input);
		
		assertTrue(this.control.equals(response.getControl()));
		assertTrue(response.getResult() != null);
		assertTrue(response.getResult().getStatus() == Status.SUCCESS);
		assertTrue(response.getOutput() != null);
		assertTrue(response.getOutput().size() == 1);
		assertTrue(response.getOutput().get(0).getId().equals(String.valueOf(input.getId())));
		assertTrue(response.getOutput().get(0).getCategoryId().equals(String.valueOf(input.getCategoryId())));
		assertTrue(response.getOutput().get(0).getOwnerId().equals(String.valueOf(input.getOwnerId())));
		assertTrue(response.getOutput().get(0).getCode().equals(String.valueOf(input.getCode())));
		assertTrue(response.getOutput().get(0).getName().equals(input.getName()));
		assertTrue(response.getOutput().get(0).getStartDate().equals(String.valueOf(input.getStartDate())));
		assertTrue(response.getOutput().get(0).getEndDate().equals(String.valueOf(input.getEndDate())));
		assertTrue(response.getOutput().get(0).getLastUpdateUser().equals(control.getRequesterName()));
		assertTrue(response.getOutput().get(0).getLastUpdateTxId().equals(control.getTransactionId().toString()));
		assertFalse(response.getOutput().get(0).getLastUpdateTs().equals(input.getLastUpdateTs()));
		
	}

	/**
	 * <p><b>更新処理（異常系）</b></p>
	 * 更新失敗した時にEXCEPTIONが発生することを確認する<br>
	 * 
	 * 入力値：各項目<br>
	 * 戻り値：ステータスがEXCEPTION_CONFLICT<br>
	 */
	@Test
	public void testParform_EXCEPTION_CONFLICT001() {
		Long id = 1L;
		String startDate = "2000-12-01 00:11:13.654321";
		String endDate = "2099-12-31 23:59:59.999999";
		String lastUpdateTs = "2014-11-29 18:33:12.123456";
		
		input = new CodeObj();
		input.setId(String.valueOf(id));
		input.setOwnerId("6");
		input.setCategoryId("3");
		input.setCode("12");
		input.setName("updateCodeName");
		input.setStartDate(startDate);
		input.setEndDate(endDate);
		input.setLastUpdateTs(lastUpdateTs);
		
		when(codeMapper.update(anyObject())).thenReturn(0);

		try {
			target.perform(control, input);
			fail();
		} catch (ConflictRequestException e) {
			assertTrue(e.getResult().getStatus() == Status.EXCEPTION_CONFLICT);
		}
	}
	
	/**
	 * <p><b>入力チェック（正常系）</b></p>
	 * 入力チェックでinputがnullの場合エラーになることを確認する<br>
	 * 
	 * 入力値：なし<br>
	 * 戻り値：ステータスがBAD_REQUEST_VALUE<br>
	 */
	@Test
	public void testParform_BAT_REQUEST_VALUE001() {
		CodeServiceResponse response = target.perform(control, null);
		
		assertTrue(this.control.equals(response.getControl()));
		assertTrue(response.getResult() != null);
		assertTrue(response.getResult().getStatus() == Status.BAD_REQUEST_VALUE);
		assertTrue(response.getOutput() == null);
	}

	/**
	 * <p><b>入力チェック（正常系）</b></p>
	 * 入力チェックでnameがnullの場合エラーになることを確認する<br>
	 * 
	 * 入力値：nameのみnull<br>
	 * 戻り値：ステータスがBAD_REQUEST_VALUE<br>
	 */
	@Test
	public void testParform_BAT_REQUEST_VALUE002() {
		
		String startDate = "2000-12-01 00:11:13.654321";
		String endDate = "2099-12-31 23:59:59.999999";
		String lastUpdateTs = "2014-11-29 18:33:12.123456";
		
		input = new CodeObj();
		input.setId("1");
		input.setOwnerId("6");
		input.setCategoryId("3");
		input.setCode("12");
		input.setName(null);
		input.setStartDate(startDate);
		input.setEndDate(endDate);
		input.setLastUpdateTs(lastUpdateTs);

		CodeServiceResponse response = target.perform(control, input);
		
		assertTrue(this.control.equals(response.getControl()));
		assertTrue(response.getResult() != null);
		assertTrue(response.getResult().getStatus() == Status.BAD_REQUEST_VALUE);
		assertTrue(response.getOutput() == null);
		
	}
	
	/**
	 * <p><b>入力チェック（正常系）</b></p>
	 * 入力チェックでnameが空の場合エラーになることを確認する<br>
	 * 
	 * 入力値：nameのみ空<br>
	 * 戻り値：ステータスがBAD_REQUEST_VALUE<br>
	 */
	@Test
	public void testParform_BAT_REQUEST_VALUE003() {
		
		String startDate = "2000-12-01 00:11:13.654321";
		String endDate = "2099-12-31 23:59:59.999999";
		String lastUpdateTs = "2014-11-29 18:33:12.123456";
		
		input = new CodeObj();
		input.setId("1");
		input.setOwnerId("6");
		input.setCategoryId("3");
		input.setCode("12");
		input.setName("");
		input.setStartDate(startDate);
		input.setEndDate(endDate);
		input.setLastUpdateTs(lastUpdateTs);

		CodeServiceResponse response = target.perform(control, input);
		
		assertTrue(this.control.equals(response.getControl()));
		assertTrue(response.getResult() != null);
		assertTrue(response.getResult().getStatus() == Status.BAD_REQUEST_VALUE);
		assertTrue(response.getOutput() == null);
		
	}

}
