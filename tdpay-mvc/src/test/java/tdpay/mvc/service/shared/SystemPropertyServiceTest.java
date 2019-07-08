package tdpay.mvc.service.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tdpay.mvc.common.SystemPropertyCode;
import tdpay.mvc.entity.SystemProperty;
import tdpay.mvc.repository.SystemPropertyRepository;

public class SystemPropertyServiceTest {

    @SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(SystemPropertyServiceTest.class);

	private SystemPropertyService service;

	@BeforeEach
    public void beforeEach() {
        service = new SystemPropertyService();
    }

    /**
     * 企業稼働状態の情報取得機能のテスト
     *
     */
	@Test
    public void getCompannyRunningTest() {
        SystemPropertyRepository systemPropertyRepository = mock(SystemPropertyRepository.class);

        SystemProperty systemProperty = new SystemProperty();
        systemProperty.setCode(SystemPropertyCode.COMPANY_RUNNING);
        systemProperty.setValue("稼働中");
        when(systemPropertyRepository.findByCode(SystemPropertyCode.COMPANY_RUNNING)).thenReturn(systemProperty);

        service.systemPropertyRepository = systemPropertyRepository;

        assertNotNull(service.getCompannyRunning());
        assertEquals(service.getCompannyRunning(),"稼働中");
    }

    /**
     * 企業稼働状態の情報取得機能のテスト
     *
     */
	@Test
    public void getCompannyStopOperationTest() {
        SystemPropertyRepository systemPropertyRepository = mock(SystemPropertyRepository.class);

        SystemProperty systemProperty = new SystemProperty();
        systemProperty.setCode(SystemPropertyCode.COMPANY_STOP_OPERATION);
        systemProperty.setValue("稼働停止");
        when(systemPropertyRepository.findByCode(SystemPropertyCode.COMPANY_STOP_OPERATION)).thenReturn(systemProperty);

        service.systemPropertyRepository = systemPropertyRepository;

        assertNotNull(service.getCompannyStopOperation());
        assertEquals(service.getCompannyStopOperation(),"稼働停止");
    }

}
