package jp.co.cos_mos.mdm.v1.service.action;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jp.co.cos_mos.mdm.core.service.domain.entity.Control;
import jp.co.cos_mos.mdm.core.service.domain.entity.Status;
import jp.co.cos_mos.mdm.v1.dao.entity.Owner;
import jp.co.cos_mos.mdm.v1.dao.mapper.OwnerMapper;
import jp.co.cos_mos.mdm.v1.service.domain.OwnerServiceResponse;
import jp.co.cos_mos.mdm.v1.service.domain.entity.OwnerCriteriaObj;
import jp.co.cos_mos.mdm.v1.service.domain.entity.OwnerObj;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class OwnerGetOwnersActionTest {

	private Control control;
	private OwnerCriteriaObj criteria;
	
	@InjectMocks
	private OwnerGetOwnersAction target = new OwnerGetOwnersActionImpl();
	
	@Mock
	private OwnerMapper ownerMapper;	
    
	@Before
    public void setup() {
		control = new Control();
		control.setRequesterName("ut");
		control.setTransactionId(Math.abs(UUID.randomUUID().getLeastSignificantBits()));
		
		MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void testParform_SUCCESS001() {
		String ownerId1 = "1";
		String ownerId2 = "2";

		String inactiveTs = "2014-11-30 18:33:12.123456";
		String lastUpdateTs = "2014-11-29 18:33:12.123456";
		
		criteria = new OwnerCriteriaObj();
		
		Owner owner1 = new Owner();
		owner1.setId(Long.valueOf(ownerId1));
		owner1.setName("owner1");
		owner1.setInactiveTs(Timestamp.valueOf(inactiveTs));
		owner1.setLastUpdateTs(Timestamp.valueOf(lastUpdateTs));
		owner1.setLastUpdateTxId(Math.abs(UUID.randomUUID().getLeastSignificantBits()));
		owner1.setLastUpdateUser("ut");

		Owner owner2 = new Owner();
		owner2.setId(Long.valueOf(ownerId2));
		owner2.setName("owner2");
		owner2.setInactiveTs(Timestamp.valueOf(inactiveTs));
		owner2.setLastUpdateTs(Timestamp.valueOf(lastUpdateTs));
		owner2.setLastUpdateTxId(Math.abs(UUID.randomUUID().getLeastSignificantBits()));
		owner2.setLastUpdateUser("ut");
		
		List<Owner> owners = new ArrayList<Owner>();
		owners.add(owner1);
		owners.add(owner2);
		
		when(ownerMapper.selectAll()).thenReturn(owners);
		
		OwnerServiceResponse response = target.perform(control, criteria);
		
		assertTrue(this.control.equals(response.getControl()));
		assertTrue(response.getResult() != null);
		assertTrue(response.getResult().getStatus() == Status.SUCCESS);
		assertTrue(response.getOutput() != null);
		assertTrue(response.getOutput().size() == 2);
		
		OwnerObj output1 = response.getOutput().get(0);
		assertTrue(output1.getId().equals(ownerId1));
		assertTrue(output1.getName().equals(owner1.getName()));
		
		OwnerObj output2 = response.getOutput().get(1);
		assertTrue(output2.getId().equals(ownerId2));
		assertTrue(output2.getName().equals(owner2.getName()));
		
	}
	
	@Test
	public void testParform_DATA_NOT_FOUND001() {
		criteria = new OwnerCriteriaObj();
		
		List<Owner> owners = new ArrayList<Owner>();
		when(ownerMapper.selectAll()).thenReturn(owners);
		
		OwnerServiceResponse response = target.perform(control, criteria);
		
		assertTrue(this.control.equals(response.getControl()));
		assertTrue(response.getResult() != null);
		assertTrue(response.getResult().getStatus() == Status.DATA_NOT_FOUND);
		assertTrue(response.getOutput() == null);
	}

	
}
