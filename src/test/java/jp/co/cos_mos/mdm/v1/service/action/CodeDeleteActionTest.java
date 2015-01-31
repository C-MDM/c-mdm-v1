package jp.co.cos_mos.mdm.v1.service.action;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import jp.co.cos_mos.mdm.core.service.domain.entity.Control;
import jp.co.cos_mos.mdm.core.service.domain.entity.Status;
import jp.co.cos_mos.mdm.core.service.exception.ConflictRequestException;
import jp.co.cos_mos.mdm.v1.dao.mapper.CodeMapper;
import jp.co.cos_mos.mdm.v1.service.domain.CodeServiceResponse;
import jp.co.cos_mos.mdm.v1.service.domain.entity.CodeObj;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CodeDeleteActionTest {
	private Control control;
	private CodeObj input;

	@InjectMocks
	private CodeDeleteAction target = new CodeDeleteActionImpl();

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
	 * CodeCategoryInactive001_success <br/>
	 * コードカテゴリーを無効にし、再検索を行うことを確認する。 <br/>
	 * 入力値：<br/>
	 * ownerId = 1L <br/>
	 * lastUpdateTs = "2015-01-01 00:00:00.000000" <br/>
	 * 戻り値：<br/>
	 * result.status = success <br/>
	 * output = null <br/>
	 * count = 0 <br/>
	 * diffResultList = 0 <br/>
	 */
	@Test
	public void testPerfofrm_SUCCESS001() {
		Long ownerId = 1L;
		String lastUpdateTs = "2015-01-01 00:00:00.000000";

		input = new CodeObj();
		input.setId(String.valueOf(ownerId));
		input.setName("OwnerName");
		input.setLastUpdateTs(lastUpdateTs);

		when(codeMapper.delete(anyObject())).thenReturn(1);

		CodeServiceResponse response = target.perform(control, input);

		// 共通
		assertTrue(this.control.equals(response.getControl()));
		assertTrue(response.getResult() != null);
		assertTrue(response.getResult().getStatus() == Status.SUCCESS);
		assertTrue(response.getOutput() == null);
		assertTrue(response.getCount() == 0);
		assertTrue(response.getDiffResultList() == null);
	}

	/**
	 * CodeCategoryInactive001_failure <br/>
	 * inputがnullの場合、BAD_REQUEST_VALUEが返ってくることを確認する。 <br/>
	 * 入力値：<br/>
	 * input = null <br/>
	 * 戻り値：<br/>
	 * result.status = BAD_REQUEST_VALUE <br/>
	 * output = null <br/>
	 * count = 0 <br/>
	 * diffResultList = 0 <br/>
	 */
	@Test
	public void testPerfofrm_FAILURE001() {
		Long ownerId = 1L;
		String lastUpdateTs = "2015-01-01 00:00:00.000000";

		input = new CodeObj();
		input.setId(String.valueOf(ownerId));
		input.setName("OwnerName");
		input.setLastUpdateTs(lastUpdateTs);

		when(codeMapper.delete(anyObject())).thenReturn(1);

		CodeServiceResponse response = target.perform(control, null);

		// 共通
		assertTrue(this.control.equals(response.getControl()));
		assertTrue(response.getResult() != null);
		assertTrue(response.getResult().getStatus() == Status.BAD_REQUEST_VALUE);
		assertTrue(response.getOutput() == null);
		assertTrue(response.getCount() == 0);
		assertTrue(response.getDiffResultList() == null);
	}

	/**
	 * CodeCategoryInactive002_failure <br/>
	 * inputがnullの場合、BAD_REQUEST_VALUEが返ってくることを確認する。 <br/>
	 * 入力値：<br/>
	 * input.id = null <br/>
	 * input.lastUpdateTs = "2015-01-01 00:00:00.000000";
	 * 戻り値：<br/>
	 * result.status = BAD_REQUEST_VALUE <br/>
	 * output = null <br/>
	 * count = 0 <br/>
	 * diffResultList = 0 <br/>
	 */
	@Test
	public void testPerfofrm_FAILURE002() {
		Long ownerId = 1L;
		String lastUpdateTs = "2015-01-01 00:00:00.000000";

		input = new CodeObj();
		input.setId(null);
		input.setName("OwnerName");
		input.setLastUpdateTs(lastUpdateTs);

		when(codeMapper.delete(anyObject())).thenReturn(1);

		CodeServiceResponse response = target.perform(control, input);

		// 共通
		assertTrue(this.control.equals(response.getControl()));
		assertTrue(response.getResult() != null);
		assertTrue(response.getResult().getStatus() == Status.BAD_REQUEST_VALUE);
		assertTrue(response.getOutput() == null);
		assertTrue(response.getCount() == 0);
		assertTrue(response.getDiffResultList() == null);
	}

	/**
	 * CodeCategoryInactive003_failure <br/>
	 * inputが文字列の場合、BAD_REQUEST_VALUEが返ってくることを確認する。 <br/>
	 * 入力値：<br/>
	 * input.id = aaaaaaaaaaaaa <br/>
	 * input.lastUpdateTs = "2015-01-01 00:00:00.000000";
	 * 戻り値：<br/>
	 * result.status = BAD_REQUEST_VALUE <br/>
	 * output = null <br/>
	 * count = 0 <br/>
	 * diffResultList = 0 <br/>
	 */
	@Test
	public void testPerfofrm_FAILURE003() {
		Long ownerId = 1L;
		String lastUpdateTs = "2015-01-01 00:00:00.000000";

		input = new CodeObj();
		input.setId("aaaaaaaaaaaaa");
		input.setName("OwnerName");
		input.setLastUpdateTs(lastUpdateTs);

		when(codeMapper.delete(anyObject())).thenReturn(1);

		CodeServiceResponse response = target.perform(control, input);

		// 共通
		assertTrue(this.control.equals(response.getControl()));
		assertTrue(response.getResult() != null);
		assertTrue(response.getResult().getStatus() == Status.BAD_REQUEST_VALUE);
		assertTrue(response.getOutput() == null);
		assertTrue(response.getCount() == 0);
		assertTrue(response.getDiffResultList() == null);
	}

	/**
	 * CodeCategoryInactive004_failure <br/>
	 * lastUpdateTsがnullの場合、BAD_REQUEST_VALUEが返ってくることを確認する。 <br/>
	 * 入力値：<br/>
	 * input.id = 1L <br/>
	 * input.lastUpdateTs = null;
	 * 戻り値：<br/>
	 * result.status = BAD_REQUEST_VALUE <br/>
	 * output = null <br/>
	 * count = 0 <br/>
	 * diffResultList = 0 <br/>
	 */
	@Test
	public void testPerfofrm_FAILURE004() {
		Long ownerId = 1L;
		String lastUpdateTs = "2015-01-01 00:00:00.000000";

		input = new CodeObj();
		input.setId(String.valueOf(ownerId));
		input.setName("OwnerName");
		input.setLastUpdateTs(null);

		when(codeMapper.delete(anyObject())).thenReturn(1);

		CodeServiceResponse response = target.perform(control, input);

		// 共通
		assertTrue(this.control.equals(response.getControl()));
		assertTrue(response.getResult() != null);
		assertTrue(response.getResult().getStatus() == Status.BAD_REQUEST_VALUE);
		assertTrue(response.getOutput() == null);
		assertTrue(response.getCount() == 0);
		assertTrue(response.getDiffResultList() == null);
	}

	/**
	 * CodeCategoryInactive005_failure <br/>
	 * 削除処理に失敗した場合、EXCEPTION_CONFLICTが返ってくることを確認する。 <br/>
	 * 入力値：<br/>
	 * input.id = 1L <br/>
	 * input.lastUpdateTs = "2015-01-01 00:00:00.000000";
	 * 戻り値：<br/>
	 * result.status = EXCEPTION_CONFLICT <br/>
	 * output = null <br/>
	 * count = 0 <br/>
	 * diffResultList = 0 <br/>
	 */
	@Test
	public void testPerfofrm_FAILURE005() {
		Long ownerId = 1L;
		String lastUpdateTs = "2015-01-01 00:00:00.000000";

		input = new CodeObj();
		input.setId(String.valueOf(ownerId));
		input.setName("OwnerName");
		input.setLastUpdateTs(lastUpdateTs);

		when(codeMapper.delete(anyObject())).thenReturn(0);

		try {
			target.perform(control, input);
			fail();
		} catch (ConflictRequestException e) {
			assertTrue(e.getResult().getStatus() == Status.EXCEPTION_CONFLICT);
		}
	}
}
