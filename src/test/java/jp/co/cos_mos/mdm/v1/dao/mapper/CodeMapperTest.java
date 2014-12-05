package jp.co.cos_mos.mdm.v1.dao.mapper;

import static org.junit.Assert.*;

import java.util.UUID;

import jp.co.cos_mos.mdm.v1.dao.entity.Code;
import jp.co.cos_mos.mdm.v1.dao.entity.CodeCriteria;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:serviceContext.xml")
public class CodeMapperTest {

	@Autowired
	CodeMapper codeMapper;
	
	Code testCode1;
	Code testCode2;
	Code testCode3;
	
	Code testCode4;
	Code testCode5;
	Code testCode6;
	
	@Before
	public void setup() {
		// 準備
		Long ownerId = Math.abs(UUID.randomUUID().getLeastSignificantBits());
		Long codeCategoryId1 = Math.abs(UUID.randomUUID().getLeastSignificantBits());

		Long codeCategoryId2 = Math.abs(UUID.randomUUID().getLeastSignificantBits());

		testCode6 = new Code();
		testCode6.setId(Math.abs(UUID.randomUUID().getLeastSignificantBits()));
		testCode6.setOwnerId(ownerId);
		testCode6.setCategoryId(codeCategoryId2);
		testCode6.setCode("code6");
		testCode6.setName("codeName6");
		testCode6.setStartDate("00000000");
		testCode6.setEndDate("21020222");
		testCode6.setLastUpdateTxId(Math.abs(UUID.randomUUID().getLeastSignificantBits()));
		testCode6.setLastUpdateUser("ut");
		codeMapper.insert(testCode6);

		testCode5 = new Code();
		testCode5.setId(Math.abs(UUID.randomUUID().getLeastSignificantBits()));
		testCode5.setOwnerId(ownerId);
		testCode5.setCategoryId(codeCategoryId2);
		testCode5.setCode("code5");
		testCode5.setName("codeName5");
		testCode5.setStartDate("00000000");
		testCode5.setEndDate("21010222");
		testCode5.setLastUpdateTxId(Math.abs(UUID.randomUUID().getLeastSignificantBits()));
		testCode5.setLastUpdateUser("ut");
		codeMapper.insert(testCode5);

		testCode4 = new Code();
		testCode4.setId(Math.abs(UUID.randomUUID().getLeastSignificantBits()));
		testCode4.setOwnerId(ownerId);
		testCode4.setCategoryId(codeCategoryId2);
		testCode4.setCode("code4");
		testCode4.setName("codeName4");
		testCode4.setStartDate("00000000");
		testCode4.setEndDate("21010221");
		testCode4.setLastUpdateTxId(Math.abs(UUID.randomUUID().getLeastSignificantBits()));
		testCode4.setLastUpdateUser("ut");
		codeMapper.insert(testCode4);

		
		testCode3 = new Code();
		testCode3.setId(Math.abs(UUID.randomUUID().getLeastSignificantBits()));
		testCode3.setOwnerId(ownerId);
		testCode3.setCategoryId(codeCategoryId1);
		testCode3.setCode("code3");
		testCode3.setName("codeName3");
		testCode3.setStartDate("20050222");
		testCode3.setEndDate("99999999");
		testCode3.setLastUpdateTxId(Math.abs(UUID.randomUUID().getLeastSignificantBits()));
		testCode3.setLastUpdateUser("ut");
		codeMapper.insert(testCode3);

		testCode2 = new Code();
		testCode2.setId(Math.abs(UUID.randomUUID().getLeastSignificantBits()));
		testCode2.setOwnerId(ownerId);
		testCode2.setCategoryId(codeCategoryId1);
		testCode2.setCode("code2");
		testCode2.setName("codeName2");
		testCode2.setStartDate("19890222");
		testCode2.setEndDate("99999999");
		testCode2.setLastUpdateTxId(Math.abs(UUID.randomUUID().getLeastSignificantBits()));
		testCode2.setLastUpdateUser("ut");
		codeMapper.insert(testCode2);

		testCode1 = new Code();
		testCode1.setId(Math.abs(UUID.randomUUID().getLeastSignificantBits()));
		testCode1.setOwnerId(ownerId);
		testCode1.setCategoryId(codeCategoryId1);
		testCode1.setCode("code1");
		testCode1.setName("codeName1");
		testCode1.setStartDate("20000222");
		testCode1.setEndDate("99999999");
		testCode1.setLastUpdateTxId(Math.abs(UUID.randomUUID().getLeastSignificantBits()));
		testCode1.setLastUpdateUser("ut");
		codeMapper.insert(testCode1);


	}
	
	@After
	public void doAfter() {
		// 後始末
		codeMapper.delete(testCode1);
		codeMapper.delete(testCode2);
		codeMapper.delete(testCode3);
		codeMapper.delete(testCode4);
		codeMapper.delete(testCode5);
		codeMapper.delete(testCode6);

	}
	
	/**
	 * Mpperメソッドの正常動作検証ケース
	 * <ul>
	 * <li>search
	 * </ul>
	 */
	@Test
	public void testCase001() {
		CodeCriteria criteria = new CodeCriteria();
		criteria.setOwnerId(testCode1.getOwnerId());
		criteria.setCategoryId(testCode1.getCategoryId());
		assertTrue(codeMapper.search(criteria).size() == 3);
		
		// BaseDate startDate検証
		criteria.setBaseDate("19000101");
		assertTrue(codeMapper.search(criteria).size() == 0);
		
		criteria.setBaseDate("19890222");
		assertTrue(codeMapper.search(criteria).size() == 1);

		criteria.setBaseDate("20000222");
		assertTrue(codeMapper.search(criteria).size() == 2);

		criteria.setBaseDate("20050222");
		assertTrue(codeMapper.search(criteria).size() == 3);

		// BaseDate endDate検証
		criteria.setCategoryId(testCode4.getCategoryId());
		criteria.setBaseDate("21020223");
		assertTrue(codeMapper.search(criteria).size() == 0);

		criteria.setBaseDate("21020222");
		assertTrue(codeMapper.search(criteria).size() == 1);

		criteria.setBaseDate("21010222");
		assertTrue(codeMapper.search(criteria).size() == 2);

		criteria.setBaseDate("21000222");
		assertTrue(codeMapper.search(criteria).size() == 3);

		criteria.setBaseDate("21000221");
		assertTrue(codeMapper.search(criteria).size() == 3);

		
	}
	
	/**
	 * Mpperメソッドの正常動作検証ケース
	 * <ul>
	 * <li>select
	 * <li>update
	 * <li>insert
	 * <li>selectActiveCode
	 * <li>delete
	 * </ul>
	 */
	@Test
	public void testCase002() {
		CodeCriteria criteria = new CodeCriteria();
		criteria.setOwnerId(testCode1.getOwnerId());
		criteria.setCategoryId(testCode1.getCategoryId());
		criteria.setBaseDate("20141130");
		
		assertTrue(codeMapper.search(criteria).size() == 3);
		
		// testCode1のidで取得できること。異なるidで取得できないこと。
		assertTrue(codeMapper.select(testCode1.getId() + 1) == null);

		Code selectCode1 = codeMapper.select(testCode1.getId());
		assertTrue(selectCode1 != null);
		assertTrue(selectCode1.getId().equals(testCode1.getId()));
		assertTrue(selectCode1.getOwnerId().equals(testCode1.getOwnerId()));
		assertTrue(selectCode1.getCategoryId().equals(testCode1.getCategoryId()));
		assertTrue(selectCode1.getName().equals(testCode1.getName()));
		assertTrue(selectCode1.getStartDate().equals(testCode1.getStartDate()));
		assertTrue(selectCode1.getEndDate().equals(testCode1.getEndDate()));
		assertTrue(selectCode1.getLastUpdateTxId().equals(testCode1.getLastUpdateTxId()));
		assertTrue(selectCode1.getLastUpdateUser().equals(testCode1.getLastUpdateUser()));
		assertTrue(selectCode1.getLastUpdateTs() != null);
		
		// testCode1はlastUpdateTsが入力されていないためUpdateは失敗すること。
		assertTrue(testCode1.getLastUpdateTs() == null);
		assertTrue(codeMapper.update(testCode1) == 0);
		
		// selectCode1はlastUpdateTsが入力されているためUpdateは成功すること。
		// EndDateが入力された期限が更新されたcode1が更新されること
		selectCode1.setCategoryId(testCode4.getCategoryId());
		selectCode1.setCode("newCode1");
		selectCode1.setName("inactiveCodeName1");
		selectCode1.setStartDate("20140222");
		selectCode1.setEndDate("20200222");
		selectCode1.setLastUpdateTxId(Math.abs(UUID.randomUUID().getLeastSignificantBits()));
		selectCode1.setLastUpdateUser("ut1");
		
		assertTrue(codeMapper.update(selectCode1) == 1);
		
		// 設定した値が更新されていること。
		Code updatedCode1 = codeMapper.select(testCode1.getId());
		assertTrue(updatedCode1 != null);
		assertTrue(updatedCode1.getId().equals(selectCode1.getId()));
		assertTrue(updatedCode1.getOwnerId().equals(selectCode1.getOwnerId()));
		assertTrue(updatedCode1.getCategoryId().equals(selectCode1.getCategoryId()));
		assertTrue(updatedCode1.getCode().equals(selectCode1.getCode()));
		assertTrue(updatedCode1.getName().equals(selectCode1.getName()));
		assertTrue(updatedCode1.getStartDate().equals(selectCode1.getStartDate()));
		assertTrue(updatedCode1.getEndDate().equals(selectCode1.getEndDate()));
		assertTrue(updatedCode1.getLastUpdateTxId().equals(selectCode1.getLastUpdateTxId()));
		assertTrue(updatedCode1.getLastUpdateUser().equals(selectCode1.getLastUpdateUser()));

		assertFalse(updatedCode1.getLastUpdateTs().equals(selectCode1.getLastUpdateTs()));
		
		// 有効なCode1をインサート
		Code insertCode1 = new Code();
		Long insertCodeId = Math.abs(UUID.randomUUID().getLeastSignificantBits());
		insertCode1.setId(insertCodeId);
		insertCode1.setOwnerId(updatedCode1.getOwnerId());
		insertCode1.setCategoryId(updatedCode1.getCategoryId());
		insertCode1.setCode(updatedCode1.getCode());
		insertCode1.setName("newCodeName1");
		insertCode1.setStartDate("20200223");
		insertCode1.setEndDate("99999999");
		insertCode1.setLastUpdateTxId(Math.abs(UUID.randomUUID().getLeastSignificantBits()));
		insertCode1.setLastUpdateUser("ut");
		
		assertTrue(codeMapper.insert(insertCode1) == 1);
		
		// インサートされた有効な newCode1 が取得されること
		CodeCriteria activeSearchCriteria = new CodeCriteria();
		activeSearchCriteria.setOwnerId(insertCode1.getOwnerId());
		activeSearchCriteria.setCategoryId(insertCode1.getCategoryId());
		activeSearchCriteria.setCode(insertCode1.getCode());
		
		Code activeCode = codeMapper.selectActiveCode(activeSearchCriteria);
		
		assertTrue(activeCode != null);
		assertTrue(activeCode.getId().equals(insertCodeId));
		assertTrue(activeCode.getOwnerId().equals(insertCode1.getOwnerId()));
		assertTrue(activeCode.getCategoryId().equals(insertCode1.getCategoryId()));
		assertTrue(activeCode.getCode().equals(insertCode1.getCode()));
		assertTrue(activeCode.getName().equals(insertCode1.getName()));
		assertTrue(activeCode.getStartDate().equals(insertCode1.getStartDate()));
		assertTrue(activeCode.getEndDate().equals(insertCode1.getEndDate()));
		assertTrue(activeCode.getLastUpdateTxId().equals(insertCode1.getLastUpdateTxId()));
		assertTrue(activeCode.getLastUpdateUser().equals(insertCode1.getLastUpdateUser()));
		
		// 削除されること
		assertTrue(codeMapper.delete(activeCode) == 1);
		
	}
}
