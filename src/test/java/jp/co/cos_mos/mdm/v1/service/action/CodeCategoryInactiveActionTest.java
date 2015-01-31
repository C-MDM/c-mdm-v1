package jp.co.cos_mos.mdm.v1.service.action;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.sql.Timestamp;
import java.util.UUID;

import jp.co.cos_mos.mdm.core.service.domain.entity.Control;
import jp.co.cos_mos.mdm.core.service.domain.entity.Status;
import jp.co.cos_mos.mdm.core.service.exception.ConflictRequestException;
import jp.co.cos_mos.mdm.v1.dao.entity.CodeCategory;
import jp.co.cos_mos.mdm.v1.dao.mapper.CodeCategoryMapper;
import jp.co.cos_mos.mdm.v1.service.domain.CodeCategoryServiceResponse;
import jp.co.cos_mos.mdm.v1.service.domain.entity.CodeCategoryObj;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CodeCategoryInactiveActionTest {
	private Control control;
	private CodeCategoryObj input;

	@InjectMocks
	private CodeCategoryInactiveAction target = new CodeCategoryInactiveActionImpl();

	@Mock
	private CodeCategoryMapper codeCategoryMapper;

	@Before
	public void setup() {
		control = new Control();
		control.setRequesterName("ut");
		control.setTransactionId(Math.abs(UUID.randomUUID().getLeastSignificantBits()));

		MockitoAnnotations.initMocks(this);
	}

	/**
	 * <p><b>CodeCategoryInactive001_success</b></p>
	 * コードカテゴリーを無効にし、再検索を行うことを確認する。 <br>
	 * 入力値：<br>
	 * ownerId = 1L <br>
	 * lastUpdateTs = "2015-01-01 00:00:00.000000" <br>
	 * 戻り値：<br>
	 * result.status = success <br>
	 * output.size == 1 <br>
	 * output.id == ownerId <br>
	 * output.lastUpdateUser == control.requesterName <br>
	 * output.lastUpdateTxId == control.TransactionId <br>
	 * output.inactiveTs != null <br>
	 * output.lastUpdateTs != nulll <br>
	 */
	@Test
	public void testPerform_SUCCESS001() {
		Long ownerId = 1L;
		String lastUpdateTs = "2015-01-01 00:00:00.000000";

		input = new CodeCategoryObj();
		input.setId(String.valueOf(ownerId));
		input.setName("OwnerName");
		input.setLastUpdateTs(lastUpdateTs);

		when(codeCategoryMapper.inactive(anyObject())).thenReturn(1);

		CodeCategory codeCategory = new CodeCategory();
		codeCategory.setId(Long.valueOf(input.getId()));
		codeCategory.setOwnerId(ownerId);
		codeCategory.setName(input.getName());
		codeCategory.setInactiveTs(new Timestamp(System.currentTimeMillis()));
		codeCategory.setLastUpdateTs(new Timestamp(System.currentTimeMillis()));
		codeCategory.setLastUpdateUser(control.getRequesterName());
		codeCategory.setLastUpdateTxId(Long.valueOf(control.getTransactionId()));
		when(codeCategoryMapper.select(ownerId)).thenReturn(codeCategory);

		CodeCategoryServiceResponse response = target.perform(control, input);

		// 共通
		assertTrue(this.control.equals(response.getControl()));
		assertTrue(response.getResult() != null);
		assertTrue(response.getResult().getStatus() == Status.SUCCESS);
		// 再検索
		assertTrue(response.getOutput() != null);
		assertTrue(response.getOutput().size() == 1);
		assertTrue(response.getOutput().get(0).getId().equals(String.valueOf(ownerId)));
		assertTrue(response.getOutput().get(0).getLastUpdateUser().equals(control.getRequesterName()));
		assertTrue(response.getOutput().get(0).getLastUpdateTxId().equals(control.getTransactionId().toString()));
		assertTrue(response.getOutput().get(0).getInactiveTs() != null);
		assertTrue(response.getOutput().get(0).getLastUpdateTs() != null);
	}

	/**
	 * <p><b>CodeCategoryInactive001_failure</b></p>
	 * inputがnullの場合、BAD_REQUEST_VALUEが返ってくることを確認する。 <br>
	 * 入力値：<br>
	 * null <br>
	 * 戻り値：<br>
	 * BAD_REQUEST_VALUE <br>
	 */
	@Test
	public void testPerform_FAILURE001() {
		Long ownerId = 1L;
		String lastUpdateTs = "2015-01-01 00:00:00.000000";

		input = new CodeCategoryObj();
		input.setId(String.valueOf(ownerId));
		input.setName("OwnerName");
		input.setLastUpdateTs(lastUpdateTs);

		when(codeCategoryMapper.inactive(anyObject())).thenReturn(1);

		CodeCategory codeCategory = new CodeCategory();
		codeCategory.setId(Long.valueOf(input.getId()));
		codeCategory.setOwnerId(ownerId);
		codeCategory.setName(input.getName());
		codeCategory.setInactiveTs(new Timestamp(System.currentTimeMillis()));
		codeCategory.setLastUpdateTs(new Timestamp(System.currentTimeMillis()));
		codeCategory.setLastUpdateUser(control.getRequesterName());
		codeCategory.setLastUpdateTxId(Long.valueOf(control.getTransactionId()));
		when(codeCategoryMapper.select(ownerId)).thenReturn(codeCategory);

		CodeCategoryServiceResponse response = target.perform(control, null);

		// 共通
		assertTrue(this.control.equals(response.getControl()));
		assertTrue(response.getResult() != null);
		assertTrue(response.getResult().getStatus() == Status.BAD_REQUEST_VALUE);
		// 再検索なし
	}

	/**
	 * <p><b>CodeCategoryInactive002_failure</b></p>
	 * input.idがnullの場合、BAD_REQUEST_VALUEが返ってくることを確認する。 <br>
	 * 入力値：<br>
	 * id = null <br>
	 * lastUpdateTs = "2015-01--01 00:00:00.000000" <br>
	 * 戻り値：<br>
	 * BAD_REQUEST_VALUE <br>
	 */
	@Test
	public void testPerform_FAILURE002() {
		Long ownerId = 1L;
		String lastUpdateTs = "2015-01-01 00:00:00.000000";

		input = new CodeCategoryObj();
		input.setId(null);
		input.setName("OwnerName");
		input.setLastUpdateTs(lastUpdateTs);

		when(codeCategoryMapper.inactive(anyObject())).thenReturn(1);

		CodeCategory codeCategory = new CodeCategory();
		codeCategory.setId(null);
		codeCategory.setOwnerId(ownerId);
		codeCategory.setName(input.getName());
		codeCategory.setInactiveTs(new Timestamp(System.currentTimeMillis()));
		codeCategory.setLastUpdateTs(new Timestamp(System.currentTimeMillis()));
		codeCategory.setLastUpdateUser(control.getRequesterName());
		codeCategory.setLastUpdateTxId(Long.valueOf(control.getTransactionId()));
		when(codeCategoryMapper.select(ownerId)).thenReturn(codeCategory);

		CodeCategoryServiceResponse response = target.perform(control, null);

		// 共通
		assertTrue(this.control.equals(response.getControl()));
		assertTrue(response.getResult() != null);
		assertTrue(response.getResult().getStatus() == Status.BAD_REQUEST_VALUE);
		// 再検索なし
	}

	/**
	 * <p><b>CodeCategoryInactive003_failure</b></p>
	 * input.lastUpdateTsがnullの場合、BAD_REQUEST_VALUEが返ってくることを確認する。 <br>
	 * 入力値：<br>
	 * id = 1L <br>
	 * lastUpdateTs = null <br>
	 * 戻り値：<br>
	 * BAD_REQUEST_VALUE <br>
	 */
	@Test
	public void testPerform_FAILURE003() {
		Long ownerId = 1L;

		input = new CodeCategoryObj();
		input.setId(String.valueOf(ownerId));
		input.setName("OwnerName");
		input.setLastUpdateTs(null);

		when(codeCategoryMapper.inactive(anyObject())).thenReturn(1);

		CodeCategory codeCategory = new CodeCategory();
		codeCategory.setId(Long.valueOf(input.getId()));
		codeCategory.setOwnerId(ownerId);
		codeCategory.setName(input.getName());
		codeCategory.setInactiveTs(new Timestamp(System.currentTimeMillis()));
		codeCategory.setLastUpdateTs(new Timestamp(System.currentTimeMillis()));
		codeCategory.setLastUpdateUser(control.getRequesterName());
		codeCategory.setLastUpdateTxId(Long.valueOf(control.getTransactionId()));
		when(codeCategoryMapper.select(ownerId)).thenReturn(codeCategory);

		CodeCategoryServiceResponse response = target.perform(control, null);

		// 共通
		assertTrue(this.control.equals(response.getControl()));
		assertTrue(response.getResult() != null);
		assertTrue(response.getResult().getStatus() == Status.BAD_REQUEST_VALUE);
		// 再検索なし
	}

	/**
	 * <p><b>CodeCategoryInactive004_failure</b></p>
	 * 無効にする処理を失敗した場合、EXCEPTION_CONFLICTが返ってくることを確認する。 <br>
	 * 入力値：<br>
	 * ownerId = 1L <br>
	 * lastUpdateTs = "2015-01-01 00:00:00.000000" <br>
	 * 戻り値：<br>
	 * result.status = success <br>
	 */
	@Test
	public void testPerform_FAILURE004() {
		Long ownerId = 1L;
		String lastUpdateTs = "2015-01-01 00:00:00.000000";

		input = new CodeCategoryObj();
		input.setId(String.valueOf(ownerId));
		input.setName("OwnerName");
		input.setLastUpdateTs(lastUpdateTs);

		when(codeCategoryMapper.inactive(anyObject())).thenReturn(0);

		CodeCategory codeCategory = new CodeCategory();
		codeCategory.setId(Long.valueOf(input.getId()));
		codeCategory.setOwnerId(ownerId);
		codeCategory.setName(input.getName());
		codeCategory.setInactiveTs(new Timestamp(System.currentTimeMillis()));
		codeCategory.setLastUpdateTs(new Timestamp(System.currentTimeMillis()));
		codeCategory.setLastUpdateUser(control.getRequesterName());
		codeCategory.setLastUpdateTxId(Long.valueOf(control.getTransactionId()));
		when(codeCategoryMapper.select(ownerId)).thenReturn(codeCategory);

		try {
			target.perform(control, input);
			fail();
		} catch (ConflictRequestException e) {
			assertTrue(e.getResult().getStatus() == Status.EXCEPTION_CONFLICT);
		}
	}
}
