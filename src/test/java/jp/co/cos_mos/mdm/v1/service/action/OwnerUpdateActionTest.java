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

public class OwnerUpdateActionTest {
	private Control control;
	private OwnerObj input;
	
	@InjectMocks
	private OwnerUpdateAction target = new OwnerUpdateActionImpl();
	
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
		input.setName("updateOwnerName");
		input.setLastUpdateTs(lastUpdateTs);
		
		when(ownerMapper.update(anyObject())).thenReturn(1);

		Owner updateeOwner = new Owner();
		updateeOwner.setId(ownerId);
		updateeOwner.setName(input.getName());
		updateeOwner.setLastUpdateUser(control.getRequesterName());
		updateeOwner.setLastUpdateTxId(control.getTransactionId());
		updateeOwner.setInactiveTs(new Timestamp(System.currentTimeMillis()));
		updateeOwner.setLastUpdateTs(new Timestamp(System.currentTimeMillis()));
		when(ownerMapper.select(ownerId)).thenReturn(updateeOwner);
		
		OwnerServiceResponse response = target.perform(control, input);
		
		assertTrue(this.control.equals(response.getControl()));
		assertTrue(response.getResult() != null);
		assertTrue(response.getResult().getStatus() == Status.SUCCESS);
		assertTrue(response.getOutput() != null);
		assertTrue(response.getOutput().size() == 1);
		assertTrue(response.getOutput().get(0).getId().equals(String.valueOf(ownerId)));
		assertTrue(response.getOutput().get(0).getName().equals(input.getName()));
		assertTrue(response.getOutput().get(0).getLastUpdateUser().equals(control.getRequesterName()));
		assertTrue(response.getOutput().get(0).getLastUpdateTxId().equals(control.getTransactionId().toString()));
		assertFalse(response.getOutput().get(0).getLastUpdateTs().equals(input.getLastUpdateTs()));
		
	}
	
	@Test
	public void testParform_EXCEPTION_CONFLICT001() {
		Long ownerId = 1L;
		String lastUpdateTs = "2014-11-29 18:33:12.123456";
		
		input = new OwnerObj();
		input.setId(String.valueOf(ownerId));
		input.setName("updateOwnerName");
		input.setLastUpdateTs(lastUpdateTs);
		
		when(ownerMapper.update(anyObject())).thenReturn(0);

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
		input.setId("foo001");

		OwnerServiceResponse response = target.perform(control, input);
		
		assertTrue(this.control.equals(response.getControl()));
		assertTrue(response.getResult() != null);
		assertTrue(response.getResult().getStatus() == Status.BAD_REQUEST_VALUE);
		assertTrue(response.getOutput() == null);
		
	}

	@Test
	public void testParform_BAT_REQUEST_VALUE004() {
		input = new OwnerObj();
		input.setId("1");
		input.setName(null);

		OwnerServiceResponse response = target.perform(control, input);
		
		assertTrue(this.control.equals(response.getControl()));
		assertTrue(response.getResult() != null);
		assertTrue(response.getResult().getStatus() == Status.BAD_REQUEST_VALUE);
		assertTrue(response.getOutput() == null);
		
	}
	
	@Test
	public void testParform_BAT_REQUEST_VALUE005() {
		input = new OwnerObj();
		input.setId("1");
		input.setName("updateOwnerName");
		input.setLastUpdateTs(null);

		OwnerServiceResponse response = target.perform(control, input);
		
		assertTrue(this.control.equals(response.getControl()));
		assertTrue(response.getResult() != null);
		assertTrue(response.getResult().getStatus() == Status.BAD_REQUEST_VALUE);
		assertTrue(response.getOutput() == null);
		
	}
	
}
