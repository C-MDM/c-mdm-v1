package jp.co.cos_mos.mdm.v1.dao.mapper;

import static org.junit.Assert.*;

import java.util.UUID;

import jp.co.cos_mos.mdm.v1.dao.entity.CodeCategory;
import jp.co.cos_mos.mdm.v1.dao.entity.CodeCategoryCriteria;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:rest-servlet.xml")
public class CodeCategoryMapperTest {

	@Autowired
	CodeCategoryMapper codeCategoryMapper;
	
	Long ownerId1;
	Long ownerId2;
	CodeCategory testCodeCategory1;
	CodeCategory testCodeCategory2;
	CodeCategory testCodeCategory3;
	
	@Before
	public void setup() {
		// 準備
		ownerId1 = Math.abs(UUID.randomUUID().getLeastSignificantBits());
		testCodeCategory1 = new CodeCategory();
		testCodeCategory1.setId(Math.abs(UUID.randomUUID().getLeastSignificantBits()));
		testCodeCategory1.setOwnerId(ownerId1);
		testCodeCategory1.setName("CodeCategory1");
		testCodeCategory1.setInactiveTs(null);
		testCodeCategory1.setLastUpdateTxId(Math.abs(UUID.randomUUID().getLeastSignificantBits()));
		testCodeCategory1.setLastUpdateUser("ut");
		codeCategoryMapper.insert(testCodeCategory1);
		
		testCodeCategory2 = new CodeCategory();
		testCodeCategory2.setId(Math.abs(UUID.randomUUID().getLeastSignificantBits()));
		testCodeCategory2.setOwnerId(ownerId1);
		testCodeCategory2.setName("CodeCategory1");
		testCodeCategory2.setInactiveTs(null);
		testCodeCategory2.setLastUpdateTxId(Math.abs(UUID.randomUUID().getLeastSignificantBits()));
		testCodeCategory2.setLastUpdateUser("ut");
		codeCategoryMapper.insert(testCodeCategory2);

		ownerId2 = Math.abs(UUID.randomUUID().getLeastSignificantBits());
		testCodeCategory3 = new CodeCategory();
		testCodeCategory3.setId(Math.abs(UUID.randomUUID().getLeastSignificantBits()));
		testCodeCategory3.setOwnerId(ownerId2);
		testCodeCategory3.setName("CodeCategory1");
		testCodeCategory3.setInactiveTs(null);
		testCodeCategory3.setLastUpdateTxId(Math.abs(UUID.randomUUID().getLeastSignificantBits()));
		testCodeCategory3.setLastUpdateUser("ut");
		codeCategoryMapper.insert(testCodeCategory3);

	}
	
	@After
	public void doAfter() {
		// 後始末
		codeCategoryMapper.delete(testCodeCategory1);
		codeCategoryMapper.delete(testCodeCategory2);
		codeCategoryMapper.delete(testCodeCategory3);
	}
	
	
	/**
	 * Mpperメソッドの正常動作検証ケース
	 * <ul>
	 * <li>select
	 * <li>search
	 * <li>update
	 * <li>inactive
	 * </ul>
	 */
	@Test
	public void testCase001() {
		CodeCategoryCriteria criteria = new CodeCategoryCriteria();
		
		// 条件なし
		assertTrue(codeCategoryMapper.search(criteria).size() == 3);
		
		// ownerId 1
		criteria.setOwnerId(testCodeCategory1.getOwnerId());
		assertTrue(codeCategoryMapper.search(criteria).size() == 2);

		// ownerId 2
		criteria.setOwnerId(testCodeCategory3.getOwnerId());
		assertTrue(codeCategoryMapper.search(criteria).size() == 1);

		// TODO
		
	}
	
}
