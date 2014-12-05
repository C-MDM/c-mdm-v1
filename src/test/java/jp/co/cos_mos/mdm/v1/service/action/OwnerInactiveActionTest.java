package jp.co.cos_mos.mdm.v1.service.action;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.sql.Timestamp;
import java.util.UUID;

import jp.co.cos_mos.mdm.core.service.domain.entity.Control;
import jp.co.cos_mos.mdm.core.service.domain.entity.Status;
import jp.co.cos_mos.mdm.core.service.exception.ConflictRequestException;
import jp.co.cos_mos.mdm.v1.dao.entity.Owner;
import jp.co.cos_mos.mdm.v1.dao.mapper.OwnerMapper;
import jp.co.cos_mos.mdm.v1.service.domain.OwnerServiceResponse;
import jp.co.cos_mos.mdm.v1.service.domain.entity.OwnerObj;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class OwnerInactiveActionTest {
	private Control control;
	private OwnerObj input;
	
	@InjectMocks
	private OwnerInactiveAction target = new OwnerInactiveActionImpl();
	
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
		Long ownerId = 1L;
		String lastUpdateTs = "2014-11-29 18:33:12.123456";
		
		input = new OwnerObj();
		input.setId(String.valueOf(ownerId));
		input.setName("OwnerName");
		input.setLastUpdateTs(lastUpdateTs);
		
		when(ownerMapper.inactive(anyObject())).thenReturn(1);

		Owner inactiveOwner = new Owner();
		inactiveOwner.setId(ownerId);
		inactiveOwner.setLastUpdateUser(control.getRequesterName());
		inactiveOwner.setLastUpdateTxId(control.getTransactionId());
		inactiveOwner.setInactiveTs(new Timestamp(System.currentTimeMillis()));
		inactiveOwner.setLastUpdateTs(new Timestamp(System.currentTimeMillis()));
		when(ownerMapper.select(ownerId)).thenReturn(inactiveOwner);
		
		OwnerServiceResponse response = target.perform(control, input);
		
		assertTrue(this.control.equals(response.getControl()));
		assertTrue(response.getResult() != null);
		assertTrue(response.getResult().getStatus() == Status.SUCCESS);
		assertTrue(response.getOutput() != null);
		assertTrue(response.getOutput().size() == 1);
		assertTrue(response.getOutput().get(0).getId().equals(String.valueOf(ownerId)));
		assertTrue(response.getOutput().get(0).getLastUpdateUser().equals(control.getRequesterName()));
		assertTrue(response.getOutput().get(0).getLastUpdateTxId().equals(control.getTransactionId().toString()));
		assertTrue(response.getOutput().get(0).getInactiveTs() != null);
		assertTrue(response.getOutput().get(0).getLastUpdateTs() != null);
		
	}

	@Test
	public void testParform_EXCEPTION_CONFLICT001() {
		Long ownerId = 1L;
		String lastUpdateTs = "2014-11-29 18:33:12.123456";
		
		input = new OwnerObj();
		input.setId(String.valueOf(ownerId));
		input.setName("OwnerName");
		input.setLastUpdateTs(lastUpdateTs);
		
		when(ownerMapper.inactive(anyObject())).thenReturn(0);

		try {
			target.perform(control, input);
			fail();
		} catch (ConflictRequestException e) {
			assertTrue(e.getResult().getStatus() == Status.EXCEPTION_CONFLICT);
		}

	}
	
	
	@Test
	public void testParform_BAT_REQUEST_VALUE001() {
		OwnerServiceResponse response = target.perform(control, null);
		
		assertTrue(this.control.equals(response.getControl()));
		assertTrue(response.getResult() != null);
		assertTrue(response.getResult().getStatus() == Status.BAD_REQUEST_VALUE);
		assertTrue(response.getOutput() == null);
	}
	
	@Test
	public void testParform_BAT_REQUEST_VALUE002() {
		input = new OwnerObj();
		input.setId(null);

		OwnerServiceResponse response = target.perform(control, input);
		
		assertTrue(this.control.equals(response.getControl()));
		assertTrue(response.getResult() != null);
		assertTrue(response.getResult().getStatus() == Status.BAD_REQUEST_VALUE);
		assertTrue(response.getOutput() == null);
	}
	
	@Test
	public void testParform_BAT_REQUEST_VALUE003() {
		input = new OwnerObj();
		input.setId("hoge011");

		OwnerServiceResponse response = target.perform(control, input);
		
		assertTrue(this.control.equals(response.getControl()));
		assertTrue(response.getResult() != null);
		assertTrue(response.getResult().getStatus() == Status.BAD_REQUEST_VALUE);
		assertTrue(response.getOutput() == null);
	}
	
	@Test
	public void testParform_BAT_REQUEST_VALUE004() {
		Long ownerId = 1L;
		
		input = new OwnerObj();
		input.setId(String.valueOf(ownerId));
		input.setName("OwnerName");
		input.setLastUpdateTs(null);

		OwnerServiceResponse response = target.perform(control, input);
		
		assertTrue(this.control.equals(response.getControl()));
		assertTrue(response.getResult() != null);
		assertTrue(response.getResult().getStatus() == Status.BAD_REQUEST_VALUE);
		assertTrue(response.getOutput() == null);
	}
}
