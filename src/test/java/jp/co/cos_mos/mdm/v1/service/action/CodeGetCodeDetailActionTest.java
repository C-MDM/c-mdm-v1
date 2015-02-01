package jp.co.cos_mos.mdm.v1.service.action;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jp.co.cos_mos.mdm.core.service.domain.entity.Control;
import jp.co.cos_mos.mdm.core.service.domain.entity.Result;
import jp.co.cos_mos.mdm.core.service.domain.entity.Status;
import jp.co.cos_mos.mdm.v1.dao.entity.Code;
import jp.co.cos_mos.mdm.v1.dao.entity.CodeCriteria;
import jp.co.cos_mos.mdm.v1.dao.entity.Owner;
import jp.co.cos_mos.mdm.v1.dao.mapper.CodeMapper;
import jp.co.cos_mos.mdm.v1.service.domain.CodeServiceResponse;
import jp.co.cos_mos.mdm.v1.service.domain.OwnerServiceResponse;
import jp.co.cos_mos.mdm.v1.service.domain.entity.CodeCriteriaObj;
import jp.co.cos_mos.mdm.v1.service.domain.entity.CodeObj;
import jp.co.cos_mos.mdm.v1.service.domain.entity.OwnerCriteriaObj;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
/**
 * 作成日:2015/1/31
 * @author 福島圭佑
 *
 */
public class CodeGetCodeDetailActionTest {

	private Control control;
	private CodeCriteriaObj criteria;
	private Result result;

	@InjectMocks
	private CodeGetCodeDetailActionImpl target = new CodeGetCodeDetailActionImpl();

	@Mock
	private CodeMapper codeMapper;

	@Before
	public void setup() {
		control = new Control();
		control.setRequesterName("ut");
		control.setTransactionId(Math.abs(UUID.randomUUID()
				.getLeastSignificantBits()));

		MockitoAnnotations.initMocks(this);
	}

	/**
	 * <p><b>validate処理_001(異常系)</b></p><br/>
	 * 入力チェック:クライテリアがnullの時、異常であることを確認する<br/>
	 * <br/>
	 * 入力値:criteria=mull<br>
	 * 戻り値:result.status =BAD_REQUEST_VALUE<br>
	 */
	@Test
	public void testParform_BAD_REQUEST_VALUE001() {
		criteria = new CodeCriteriaObj();
		CodeServiceResponse response = target.perform(control, null);

		assertTrue(response.getResult().getStatus()
				.equals(Status.BAD_REQUEST_VALUE));
	}

	/**
	 * <p><b>validate処理_002(異常系)</b></p><br/>
	 * 入力チェック:クライテリアが正常、オーナーidがnullの時、resultのステータスがBAD_REQUEST_VALUEであることを確認する<br/>
	 * <br/>
	 * 入力値:ownerId = null<br>
	 * 戻り値:result.status =BAD_REQUEST_VALUE<br>
	 */
	@Test
	public void testParform_BAD_REQUEST_VALUE002() {
		criteria = new CodeCriteriaObj();
		criteria.setOwnerId(null);
		CodeServiceResponse response = target.perform(control, criteria);
		assertTrue(response.getResult().getStatus()
				.equals(Status.BAD_REQUEST_VALUE));
	}

	/**
	 * <p><b>validate処理_003(異常系)</b></p><br/>
	 * 入力チェック:クライテリアが正常、オーナーidが数値でない場合、resultのステータスがBAD_REQUEST_VALUEであることを確認する<br/>
	 * <br/>
	 * 入力値:owerId="a"<br>
	 * 戻り値:result.status =BAD_REQUEST_VALUE<br>
	 */
	@Test
	public void validate_BAT_REQUEST_VALUE003() {
		criteria = new CodeCriteriaObj();
		criteria.setOwnerId("a");
		CodeServiceResponse response = target.perform(control, criteria);
		assertTrue(response.getResult().getStatus()
				.equals(Status.BAD_REQUEST_VALUE));
	}

	/**
	 * <p><b>validate処理_004(異常系)</b></p></br>
	 * 入力チェック:クライテリアが正常、オーナーidが正常、カテゴリIDがnullの場合、
	 * resultのステータスがBAD_REQUEST_VALUEであることを確認する<br/>
	 *入力値:ownerId="1",CategoryId=null<br>
	 *戻り値:result.status =BAD_REQUEST_VALUE<br>
	 */
	@Test
	public void validate_BAT_REQUEST_VALUE004() {
		criteria = new CodeCriteriaObj();
		criteria.setOwnerId("1");
		criteria.setCategoryId(null);
		CodeServiceResponse response = target.perform(control, criteria);
		assertTrue(response.getResult().getStatus()
				.equals(Status.BAD_REQUEST_VALUE));
	}

	/**
	 * <p><b>validate処理_005(異常系)</b></p><br/>
	 * 入力チェック:クライテリアが正常、オーナーidが正常、カテゴリIDが数値でない場合、
	 * resultのステータスがBAD_REQUEST_VALUEであることを確認する<br/>
	 * 入力値:ownerId="1",categoryId="a"<br>
	 * 戻り値:result.status =BAD_REQUEST_VALUE<br>
	 */
	@Test
	public void validate_BAD_REQUEST_VALUE005() {
		criteria = new CodeCriteriaObj();
		criteria.setOwnerId("1");
		criteria.setCategoryId("a");
		CodeServiceResponse response = target.perform(control, criteria);
		assertTrue(response.getResult().getStatus()
				.equals(Status.BAD_REQUEST_VALUE));
	}

	/**
	 * <p><b>validate処理_006(異常系)</b></p><br/>
	 * 入力チェック:クライテリアが正常、オーナーidが正常、カテゴリIDが正常、コードがnullの場合、
	 * resultのステータスがBAD_REQUEST_VALUEであることを確認する<br/>
	 * 入力値:ownerId="1",categoryId="2",code=null<br>
	 * 戻り値:result.status =BAD_REQUEST_VALUE<br>
	 */
	@Test
	public void validate_BAD_REQUEST_VALUE006() {
		criteria = new CodeCriteriaObj();
		criteria.setOwnerId("1");
		criteria.setCategoryId("2");
		criteria.setCode(null);
		CodeServiceResponse response = target.perform(control, criteria);
		assertTrue(response.getResult().getStatus()
				.equals(Status.BAD_REQUEST_VALUE));
	}

	/**
	 * <p><b>(正常系)</b></p><br/>
	 * すべての項目に正しい値が入力されているときに各項目が入力値でselectされていることを確認する。<br/>
	 * 入力値:各項目<br/>
	 * 戻り値:各項目<br>
	 *
	 */
	@Test
	public void validate__REQUEST_VALUE007() {
		// 入力値
		final String OWNER_ID1 = "1";
		final String CATEGORY_ID1 = "2";
		final String CODE1 = "a";
		final String OWNER_ID2 = "3";
		final String CATEGORY_ID2 = "4";
		final String CODE2 = "b";

		// 入力値をDaoクラスに渡す形式に変換
		CodeCriteria codeCriteria = new CodeCriteria();
		codeCriteria.setOwnerId(Long.parseLong(OWNER_ID1));
		codeCriteria.setCategoryId(Long.parseLong(CATEGORY_ID1));
		codeCriteria.setCode(CODE1);

		// 検索
		List<Code> selectCodes = new ArrayList<Code>();
		when(codeMapper.search(codeCriteria)).thenReturn(selectCodes);

		// 検索結果セット
		List<CodeObj> output = new ArrayList<CodeObj>();
		CodeObj incodeObj1 = new CodeObj();
		incodeObj1.setCategoryId(CATEGORY_ID1);
		incodeObj1.setCode(CODE1);
		incodeObj1.setEndDate("2015/02/31");
		incodeObj1.setId("3");
		incodeObj1.setLastUpdateTs("2014-11-30 18:33:12.123456");
		incodeObj1.setLastUpdateTxId("2");
		incodeObj1.setLastUpdateUser("ut_updateUser");
		incodeObj1.setName("ja");
		incodeObj1.setOwnerId(OWNER_ID1);
		incodeObj1.setStartDate("2015/01/31");
		output.add(incodeObj1);

		CodeObj incodeObj2 = new CodeObj();
		incodeObj2.setCategoryId(CATEGORY_ID2);
		incodeObj2.setCode(CODE2);
		incodeObj2.setEndDate("2015/02/31");
		incodeObj2.setId("3");
		incodeObj2.setLastUpdateTs("2014-11-30 18:33:12.123456");
		incodeObj2.setLastUpdateTxId("2");
		incodeObj2.setLastUpdateUser("ut_updateUser");
		incodeObj2.setName("ja");
		incodeObj2.setOwnerId(OWNER_ID2);
		incodeObj2.setStartDate("2015/01/31");
		output.add(incodeObj2);

		CodeServiceResponse response = target.perform(control, criteria);

		// response自体の検証
		assertTrue(this.control.equals(response.getControl()));
		assertTrue(response.getResult() != null);
		assertTrue(response.getResult().getStatus() == Status.SUCCESS);
		assertTrue(response.getOutput() != null);
		assertTrue(response.getOutput().size() == 1);

		CodeObj outCodeObj1 = response.getOutput().get(0);

		assertTrue(outCodeObj1.getCategoryId().equals(CATEGORY_ID1));
		assertTrue(outCodeObj1.getCode().equals(CODE1));
		assertTrue(outCodeObj1.getEndDate().equals("2015/02/31"));
		assertTrue(outCodeObj1.getId().equals("3"));
		assertTrue(outCodeObj1.getLastUpdateTs().equals(
				"2014-11-30 18:33:12.123456"));
		assertTrue(outCodeObj1.getLastUpdateTxId().equals("2"));
		assertTrue(outCodeObj1.getLastUpdateUser().equals("ut_updateUser"));
		assertTrue(outCodeObj1.getName().equals("ja"));
		assertTrue(outCodeObj1.getOwnerId().equals(OWNER_ID1));
		assertTrue(outCodeObj1.getStartDate().equals("2015/01/31"));

		CodeObj outCodeObj2 = response.getOutput().get(1);

		assertTrue(outCodeObj2.getCategoryId().equals(CATEGORY_ID2));
		assertTrue(outCodeObj2.getCode().equals(CODE2));
		assertTrue(outCodeObj2.getEndDate().equals("2015/02/31"));
		assertTrue(outCodeObj2.getId().equals("3"));
		assertTrue(outCodeObj2.getLastUpdateTs().equals(
				"2014-11-30 18:33:12.123456"));
		assertTrue(outCodeObj2.getLastUpdateTxId().equals("2"));
		assertTrue(outCodeObj2.getLastUpdateUser().equals("ut_updateUser"));
		assertTrue(outCodeObj2.getName().equals("ja"));
		assertTrue(outCodeObj2.getOwnerId().equals(OWNER_ID2));
		assertTrue(outCodeObj2.getStartDate().equals("2015/01/31"));
	}

}
