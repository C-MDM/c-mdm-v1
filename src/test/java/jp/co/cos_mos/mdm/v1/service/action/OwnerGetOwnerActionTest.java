package jp.co.cos_mos.mdm.v1.service.action;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.Timestamp;
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

public class OwnerGetOwnerActionTest {

	private Control control;
	private OwnerCriteriaObj criteria;
	
	@InjectMocks
	private OwnerGetOwnerAction target = new OwnerGetOwnerActionImpl();
	
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
		String ownerId = "1";
		String inactiveTs = "2014-11-30 18:33:12.123456";
		String lastUpdateTs = "2014-11-29 18:33:12.123456";
		
		criteria = new OwnerCriteriaObj();
		criteria.setId(ownerId);
		
		Owner selectedOwner = new Owner();
		selectedOwner.setId(Long.valueOf(ownerId));
		selectedOwner.setName("ownerName");
		selectedOwner.setInactiveTs(Timestamp.valueOf(inactiveTs));
		selectedOwner.setLastUpdateTs(Timestamp.valueOf(lastUpdateTs));
		selectedOwner.setLastUpdateTxId(Math.abs(UUID.randomUUID().getLeastSignificantBits()));
		selectedOwner.setLastUpdateUser("ut");
		
		when(ownerMapper.select(Long.valueOf(ownerId))).thenReturn(selectedOwner);
		
		OwnerServiceResponse response = target.perform(control, criteria);
		
		assertTrue(this.control.equals(response.getControl()));
		assertTrue(response.getResult() != null);
		assertTrue(response.getResult().getStatus() == Status.SUCCESS);
		assertTrue(response.getOutput() != null);
		assertTrue(response.getOutput().size() == 1);
		
		OwnerObj output = response.getOutput().get(0);
		assertTrue(output.getId().equals(ownerId));
		assertTrue(output.getName().equals(selectedOwner.getName()));
		assertTrue(output.getInactiveTs().equals(inactiveTs));
		assertTrue(output.getLastUpdateTs().equals(lastUpdateTs));
		assertTrue(output.getLastUpdateTxId().equals(String.valueOf(selectedOwner.getLastUpdateTxId())));
		assertTrue(output.getLastUpdateUser().equals(selectedOwner.getLastUpdateUser()));
		
	}

	@Test
	public void testParform_BAT_REQUEST_VALUE001() {
		OwnerServiceResponse response = target.perform(control, null);
		assertTrue(response.getResult().getStatus().equals(Status.BAD_REQUEST_VALUE));
	}

	@Test
	public void testParform_BAT_REQUEST_VALUE002() {
		criteria = new OwnerCriteriaObj();
		criteria.setId(null);
		
		OwnerServiceResponse response = target.perform(control, criteria);
		
		assertTrue(response.getResult().getStatus().equals(Status.BAD_REQUEST_VALUE));
	}

	@Test
	public void testParform_BAT_REQUEST_VALUE003() {
		criteria = new OwnerCriteriaObj();
		criteria.setId("abc001");
		
		OwnerServiceResponse response = target.perform(control, criteria);
		
		assertTrue(response.getResult().getStatus().equals(Status.BAD_REQUEST_VALUE));
	}
	
	@Test
	public void testParform_DATA_NOT_FOUND001() {
		criteria = new OwnerCriteriaObj();
		String ownerId = "1";
		criteria.setId(ownerId);
		
		Owner selectedOwner = new Owner();
		selectedOwner.setId(Long.valueOf(ownerId));
		
		when(ownerMapper.select(Long.valueOf(ownerId))).thenReturn(null);
		
		OwnerServiceResponse response = target.perform(control, criteria);
		
		assertTrue(response.getResult().getStatus().equals(Status.DATA_NOT_FOUND));
	}

}
