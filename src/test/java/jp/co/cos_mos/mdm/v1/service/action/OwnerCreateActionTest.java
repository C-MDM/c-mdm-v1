package jp.co.cos_mos.mdm.v1.service.action;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.sql.Timestamp;
import java.util.UUID;

import jp.co.cos_mos.mdm.core.service.action.SequenceNumberNumberingAction;
import jp.co.cos_mos.mdm.core.service.domain.entity.Control;
import jp.co.cos_mos.mdm.core.service.domain.entity.Result;
import jp.co.cos_mos.mdm.core.service.domain.entity.Status;
import jp.co.cos_mos.mdm.core.service.exception.GetEntityNumberingIdException;
import jp.co.cos_mos.mdm.v1.dao.entity.Owner;
import jp.co.cos_mos.mdm.v1.dao.mapper.OwnerMapper;
import jp.co.cos_mos.mdm.v1.service.domain.OwnerServiceResponse;
import jp.co.cos_mos.mdm.v1.service.domain.entity.OwnerObj;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class OwnerCreateActionTest {
	
	private Control control;
	private OwnerObj input;
	
	@InjectMocks
	private OwnerCreateAction target = new OwnerCreateActionImpl();
	
	@Mock
	private OwnerMapper ownerMapper;	
	@Mock
	private SequenceNumberNumberingAction numberingAction;
    
	@Before
    public void setup() {
		control = new Control();
		control.setRequesterName("ut");
		control.setTransactionId(Math.abs(UUID.randomUUID().getLeastSignificantBits()));
		
		MockitoAnnotations.initMocks(this);
    }
    
	@Test
	public void testParform_SUCCESS001() {
		input = new OwnerObj();
		input.setId(null);
		input.setName("OwnerName");

		Long ownerId = 1L;

		when(numberingAction.getEntityNumberingId(Owner.class.getName())).thenReturn(ownerId);
		when(ownerMapper.insert(anyObject())).thenReturn(1);
		
		Owner insertedOwner = new Owner();
		insertedOwner.setId(ownerId);
		insertedOwner.setName("OwnerName");
		insertedOwner.setLastUpdateTs(new Timestamp(System.currentTimeMillis()));
		insertedOwner.setLastUpdateUser(control.getRequesterName());
		insertedOwner.setLastUpdateTxId(control.getTransactionId());

		when(ownerMapper.select(ownerId)).thenReturn(insertedOwner);
		
		OwnerServiceResponse response = target.perform(control, input);

		assertTrue(this.control.equals(response.getControl()));
		assertTrue(response.getResult() != null);
		assertTrue(response.getResult().getStatus() == Status.SUCCESS);
		assertTrue(response.getOutput() != null);
		assertTrue(response.getOutput().size() == 1);
		assertTrue(response.getOutput().get(0).getId().equals(String.valueOf(ownerId)));
		assertTrue(response.getOutput().get(0).getLastUpdateTs() != null);
		assertTrue(response.getOutput().get(0).getLastUpdateUser().equals(control.getRequesterName()));
		assertTrue(response.getOutput().get(0).getLastUpdateTxId().equals(control.getTransactionId().toString()));
		
	}
	
	@Test
	public void testParform_BAT_REQUEST_VALUE001() {
		input = null;

		OwnerServiceResponse response = target.perform(control, input);
		
		assertTrue(response.getResult().getStatus() == Status.BAD_REQUEST_VALUE);
		
	}
	
	@Test
	public void testParform_BAT_REQUEST_VALUE002() {
		input = new OwnerObj();
		input.setId("11111");

		OwnerServiceResponse response = target.perform(control, input);
		
		assertTrue(response.getResult().getStatus() == Status.BAD_REQUEST_VALUE);
		
	}
	
	@Test
	public void testParform_BAT_REQUEST_VALUE003() {
		input = new OwnerObj();
		input.setId(null);
		input.setName(null);

		OwnerServiceResponse response = target.perform(control, input);
		
		assertTrue(response.getResult().getStatus() == Status.BAD_REQUEST_VALUE);
		
	}

	@Test
	public void testParform_EXCEPTION_GET_ENTITY_NUMBERING_ID001() {
		input = new OwnerObj();
		input.setId(null);
		input.setName("OwnerName");

		Result result = new Result();
		result.setStatus(Status.EXCEPTION_GET_ENTITY_NUMBERING_ID);
		when(numberingAction.getEntityNumberingId(Owner.class.getName()))
			.thenThrow(new GetEntityNumberingIdException(result));
		
		try {
			target.perform(control, input);
			fail();
		} catch (GetEntityNumberingIdException e) {
			assertTrue(e.getResult().getStatus() == Status.EXCEPTION_GET_ENTITY_NUMBERING_ID);
		}

	}

}
