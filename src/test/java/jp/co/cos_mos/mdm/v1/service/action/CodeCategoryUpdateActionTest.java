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

public class CodeCategoryUpdateActionTest {
	private Control control;
	private CodeCategoryObj input;

	@InjectMocks
	private CodeCategoryUpdateActionImpl target = new CodeCategoryUpdateActionImpl();
	
	@Mock
	private CodeCategoryMapper codeCategoryMapper;
	
	
	@Before
	public void setUp() throws Exception {
		control = new Control();
		control.setRequesterName("ut");
		control.setTransactionId(Math.abs(UUID.randomUUID().getLeastSignificantBits()));
		
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testParform_SUCCESS001() {
		Long id = 1L;
		String inactiveTs = "2099-12-31 23:59:59.999999";
		String lastUpdateTs = "2014-11-29 18:33:12.123456";
		
		input = new CodeCategoryObj();
		input.setId(String.valueOf(id));
		input.setOwnerId("6");
		input.setName("updateCodeName");
		input.setInactiveTs(inactiveTs);
		input.setLastUpdateTs(lastUpdateTs);
		
		when(codeCategoryMapper.update(anyObject())).thenReturn(1);

		CodeCategory updateCode = new CodeCategory();
		updateCode.setId(Long.valueOf(input.getId()));
		updateCode.setOwnerId(Long.valueOf(input.getOwnerId()));
		updateCode.setName(input.getName());
		updateCode.setInactiveTs(Timestamp.valueOf(input.getInactiveTs()));
		updateCode.setLastUpdateTs(new Timestamp(System.currentTimeMillis()));
		updateCode.setLastUpdateUser(control.getRequesterName());
		updateCode.setLastUpdateTxId(control.getTransactionId());
		
		when(codeCategoryMapper.select(id)).thenReturn(updateCode);
		
		CodeCategoryServiceResponse response = target.perform(control, input);
		
		assertTrue(this.control.equals(response.getControl()));
		assertTrue(response.getResult() != null);
		assertTrue(response.getResult().getStatus() == Status.SUCCESS);
		assertTrue(response.getOutput() != null);
		assertTrue(response.getOutput().size() == 1);
		assertTrue(response.getOutput().get(0).getId().equals(String.valueOf(input.getId())));
		assertTrue(response.getOutput().get(0).getInactiveTs().equals(String.valueOf(input.getInactiveTs())));
		assertTrue(response.getOutput().get(0).getOwnerId().equals(String.valueOf(input.getOwnerId())));
		assertTrue(response.getOutput().get(0).getName().equals(input.getName()));
		assertTrue(response.getOutput().get(0).getLastUpdateUser().equals(control.getRequesterName()));
		assertTrue(response.getOutput().get(0).getLastUpdateTxId().equals(control.getTransactionId().toString()));
		assertFalse(response.getOutput().get(0).getLastUpdateTs().equals(input.getLastUpdateTs()));
		
	}

	@Test
	public void testParform_EXCEPTION_CONFLICT001() {
		Long id = 1L;
		String inactiveTs = "2099-12-31 23:59:59.999999";
		String lastUpdateTs = "2014-11-29 18:33:12.123456";
		
		input = new CodeCategoryObj();
		input.setId(String.valueOf(id));
		input.setOwnerId("6");
		input.setName("updateCodeName");
		input.setInactiveTs(inactiveTs);
		input.setLastUpdateTs(lastUpdateTs);
		
		when(codeCategoryMapper.update(anyObject())).thenReturn(0);

		try {
			target.perform(control, input);
			fail();
		} catch (ConflictRequestException e) {
			assertTrue(e.getResult().getStatus() == Status.EXCEPTION_CONFLICT);
		}
	}
	
	@Test
	public void testParform_BAT_REQUEST_VALUE001() {
		CodeCategoryServiceResponse response = target.perform(control, null);
		
		assertTrue(this.control.equals(response.getControl()));
		assertTrue(response.getResult() != null);
		assertTrue(response.getResult().getStatus() == Status.BAD_REQUEST_VALUE);
		assertTrue(response.getOutput() == null);
	}

	
	@Test
	public void testParform_BAT_REQUEST_VALUE002() {
		Long id = 1L;
		String inactiveTs = "2099-12-31 23:59:59.999999";
		String lastUpdateTs = "2014-11-29 18:33:12.123456";
		
		input = new CodeCategoryObj();
		input.setId(String.valueOf(id));
		input.setOwnerId("6");
		input.setName(null);
		input.setInactiveTs(inactiveTs);
		input.setLastUpdateTs(lastUpdateTs);

		CodeCategoryServiceResponse response = target.perform(control, input);
		
		assertTrue(this.control.equals(response.getControl()));
		assertTrue(response.getResult() != null);
		assertTrue(response.getResult().getStatus() == Status.BAD_REQUEST_VALUE);
		assertTrue(response.getOutput() == null);
		
	}
	
	@Test
	public void testParform_BAT_REQUEST_VALUE003() {
		
		Long id = 1L;
		String inactiveTs = "2099-12-31 23:59:59.999999";
		String lastUpdateTs = "2014-11-29 18:33:12.123456";
		
		input = new CodeCategoryObj();
		input.setId(String.valueOf(id));
		input.setOwnerId("6");
		input.setName("");
		input.setInactiveTs(inactiveTs);
		input.setLastUpdateTs(lastUpdateTs);

		CodeCategoryServiceResponse response = target.perform(control, input);
		
		assertTrue(this.control.equals(response.getControl()));
		assertTrue(response.getResult() != null);
		assertTrue(response.getResult().getStatus() == Status.BAD_REQUEST_VALUE);
		assertTrue(response.getOutput() == null);
		
	}

}
