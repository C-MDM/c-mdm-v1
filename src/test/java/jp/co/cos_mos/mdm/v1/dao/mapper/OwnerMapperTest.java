package jp.co.cos_mos.mdm.v1.dao.mapper;

import static org.junit.Assert.*;

import java.util.UUID;

import jp.co.cos_mos.mdm.v1.dao.entity.Owner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:serviceContext.xml")
public class OwnerMapperTest {
	
	@Autowired
	OwnerMapper ownerMapper;
	
	Owner testOwner1;
	Owner testOwner2;
	
	Owner selectOwner;
	
	@Before
	public void setup() {
		// 準備
		testOwner1 = new Owner();
		testOwner1.setId(Math.abs(UUID.randomUUID().getLeastSignificantBits()));
		testOwner1.setName("ownerName");
		testOwner1.setInactiveTs(null);
		testOwner1.setLastUpdateTxId(Math.abs(UUID.randomUUID().getLeastSignificantBits()));
		testOwner1.setLastUpdateUser("ut");
		ownerMapper.insert(testOwner1);

		testOwner2 = new Owner();
		testOwner2.setId(Math.abs(UUID.randomUUID().getLeastSignificantBits()));
		testOwner2.setName("ownerName");
		testOwner2.setInactiveTs(null);
		testOwner2.setLastUpdateTxId(Math.abs(UUID.randomUUID().getLeastSignificantBits()));
		testOwner2.setLastUpdateUser("ut");
		ownerMapper.insert(testOwner2);
	}
	
	@After
	public void doAfter() {
		// 後始末
		ownerMapper.delete(testOwner1);
		ownerMapper.delete(testOwner2);
	}
	
	/**
	 * Mpperメソッドの正常動作検証ケース
	 * <ul>
	 * <li>select
	 * <li>selectall
	 * <li>update
	 * <li>inactive
	 * </ul>
	 */
	@Test
	public void testCase001() {
		// ownerはTableに２件登録されている。
		assertTrue(ownerMapper.selectAll().size() == 2);
		
		// testOwner1のidで取得できること。異なるidで取得できないこと。
		assertTrue(ownerMapper.select(testOwner1.getId() + 1) == null);

		selectOwner = ownerMapper.select(testOwner1.getId());
		assertTrue(selectOwner != null);
		assertTrue(selectOwner.getId().equals(testOwner1.getId()));
		assertTrue(selectOwner.getName().equals(testOwner1.getName()));
		assertTrue(selectOwner.getLastUpdateTxId().equals(testOwner1.getLastUpdateTxId()));
		assertTrue(selectOwner.getLastUpdateUser().equals(testOwner1.getLastUpdateUser()));
		assertTrue(selectOwner.getInactiveTs() == null);
		assertTrue(selectOwner.getLastUpdateTs() != null);
		
		// testOwner1はlastUpdateTsが入力されていないためUpdateは失敗すること。
		assertTrue(testOwner1.getLastUpdateTs() == null);
		assertTrue(ownerMapper.update(testOwner1) == 0);
		
		// selectOwnerはlastUpdateTsが入力されているためUpdateは成功すること。
		selectOwner.setName("UpdatedOwnerName");
		selectOwner.setLastUpdateTxId(Math.abs(UUID.randomUUID().getLeastSignificantBits()));
		selectOwner.setLastUpdateUser("ut1");
		
		assertTrue(ownerMapper.update(selectOwner) == 1);
		
		// 設定した値が更新されていること。
		Owner updatedOwner = ownerMapper.select(testOwner1.getId());
		assertTrue(updatedOwner != null);
		assertTrue(updatedOwner.getId().equals(selectOwner.getId()));
		assertTrue(updatedOwner.getName().equals(selectOwner.getName()));
		assertTrue(updatedOwner.getLastUpdateTxId().equals(selectOwner.getLastUpdateTxId()));
		assertTrue(updatedOwner.getLastUpdateUser().equals(selectOwner.getLastUpdateUser()));
		assertTrue(updatedOwner.getInactiveTs() == null);
		assertFalse(updatedOwner.getLastUpdateTs().equals(selectOwner.getLastUpdateTs()));
		
		// 論理削除
		assertTrue(ownerMapper.inactive(testOwner1) == 0);
		assertTrue(ownerMapper.inactive(selectOwner) == 0);
		assertTrue(ownerMapper.inactive(updatedOwner) == 1);
		
		// 論理削除されていても２件。
		assertTrue(ownerMapper.selectAll().size() == 2);
		
		// 論理削除レコードを抽出
		Owner inactivedOwner = ownerMapper.select(testOwner1.getId());
		assertFalse(inactivedOwner.getInactiveTs() == null);
		
	}

}
