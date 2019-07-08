package tdpay.mvc.controller;

import static org.mockito.Mockito.mock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import tdpay.mvc.common.UrlConstants;
import tdpay.mvc.config.AppConfig;
import tdpay.mvc.service.UserManageService;
import tdpay.mvc.testutils.AppTestUtils;
import tdpay.mvc.testutils.AppTestUtils.PERFORM;

@SpringJUnitWebConfig(classes = { AppConfig.class })
public class UserMaintenanceControllerTest {

    @SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(UserMaintenanceControllerTest.class);

  	@InjectMocks
    private UserMaintenanceController controller;

    private MockMvc mockMvc;

    @Autowired
    private ModelMapper modelMapper;

	@BeforeEach
    public void beforeEach() {
	    MockitoAnnotations.initMocks(this);

		mockMvc = AppTestUtils.createMockMvc(controller);

		controller.userManageService = mock(UserManageService.class);
		controller.modelMapper = this.modelMapper;
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:messages");
        controller.messageSource = messageSource;
	}

    @Test
    public void indexTest() throws Exception {
		AppTestUtils.verifyMvcPerform(mockMvc, PERFORM.GET,
			UrlConstants.USER_MAINTENANCE,
			null,
			UrlConstants.USER_MAINTENANCE + UrlConstants.INDEX);

		AppTestUtils.verifyMvcPerform(mockMvc, PERFORM.GET,
			UrlConstants.USER_MAINTENANCE + UrlConstants.INDEX,
			null,
			UrlConstants.USER_MAINTENANCE + UrlConstants.INDEX);
    }

    @Test
    public void searchTest() throws Exception {

    	MultiValueMap<String, String> formMap = new LinkedMultiValueMap<String, String>();
		formMap.add("mid", "");
		formMap.add("userName", "");
		formMap.add("loginId", "");

		MvcResult mvcResult = AppTestUtils.verifyMvcPerform(mockMvc, PERFORM.POST,
			UrlConstants.USER_MAINTENANCE + UrlConstants.SEARCH,
			formMap,
			UrlConstants.USER_MAINTENANCE + UrlConstants.INDEX);
    }

}
