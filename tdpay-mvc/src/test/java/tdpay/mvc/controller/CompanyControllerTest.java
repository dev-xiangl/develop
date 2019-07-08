package tdpay.mvc.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
import org.springframework.ui.ModelMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import tdpay.mvc.common.UrlConstants;
import tdpay.mvc.config.AppConfig;
import tdpay.mvc.form.UploadForm;
import tdpay.mvc.service.MerchantCompanyManageService;
import tdpay.mvc.service.MerchantShopManageService;
import tdpay.mvc.service.SettingDataService;
import tdpay.mvc.service.shared.SystemPropertyService;
import tdpay.mvc.testutils.AppTestUtils;
import tdpay.mvc.testutils.AppTestUtils.PERFORM;

@SpringJUnitWebConfig(classes = { AppConfig.class })
public class CompanyControllerTest {

    @SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(CompanyControllerTest.class);

  	@InjectMocks
    private CompanyController controller;

    private MockMvc mockMvc;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * 
     *
     */
	@BeforeEach
    public void beforeEach() {
	    MockitoAnnotations.initMocks(this);

		mockMvc = AppTestUtils.createMockMvc(controller);

		controller.merchantCompanyManageService = mock(MerchantCompanyManageService.class);
		controller.merchantShopManageService = mock(MerchantShopManageService.class);
        controller.systemPropertyService = mock(SystemPropertyService.class);
		controller.settingDataService = mock(SettingDataService.class);
        controller.modelMapper = this.modelMapper;
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:messages");
        controller.messageSource = messageSource;
	}

    /**
     * 検索画面のテスト
     *
     */
    @Test
    public void indexTest() throws Exception {
		AppTestUtils.verifyMvcPerform(mockMvc, PERFORM.GET,
			UrlConstants.COMPANY,
			null,
			UrlConstants.COMPANY + UrlConstants.INDEX);

		AppTestUtils.verifyMvcPerform(mockMvc, PERFORM.GET,
			UrlConstants.COMPANY + UrlConstants.INDEX,
			null,
			UrlConstants.COMPANY + UrlConstants.INDEX);
    }

    /**
     * 検索結果のテスト
     *
     */
    @Test
    public void searchTest() throws Exception {

    	MultiValueMap<String, String> formMap = new LinkedMultiValueMap<String, String>();
		formMap.add("mid", "");
		formMap.add("mallmapId", "");
		formMap.add("ccid", "");
        formMap.add("certificationKey", "");
        formMap.add("companyName", "");
        formMap.add("mainPhoneNumber", "");
		formMap.add("mainAddress", "");
        formMap.add("representativeName", "");
		formMap.add("email", "");

		MvcResult mvcResult = AppTestUtils.verifyMvcPerform(mockMvc, PERFORM.POST,
			UrlConstants.COMPANY + UrlConstants.SEARCH,
			formMap,
			UrlConstants.COMPANY + UrlConstants.INDEX);
    }

    /**
     * 追加画面のテスト
     *
     */
    @Test
    public void addTest() throws Exception {
		AppTestUtils.verifyMvcPerform(mockMvc, PERFORM.GET,
			UrlConstants.COMPANY + UrlConstants.ADD,
			null,
			UrlConstants.COMPANY + UrlConstants.REGIST);
    }

    /**
     * 加盟店インポートのテスト
     *
     */
    @Test
    public void importCompanyTest_通常() throws Exception {
        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("dummy.xls");

        UploadForm form = new UploadForm();
        form.setMultipartFile(file);

        mockMvc.perform(post(UrlConstants.COMPANY + UrlConstants.IMPORT).flashAttr("uploadForm", form))
                .andExpect(status().isOk())
                .andExpect(view().name(UrlConstants.COMPANY + UrlConstants.REGIST)).andReturn();
    }

    /**
     * 加盟店インポートのテスト
     *
     */
    @Test
    public void importCompanyTest_null() throws Exception {
        MultipartFile file = null;

        UploadForm form = new UploadForm();
        form.setMultipartFile(file);

        mockMvc.perform(post(UrlConstants.COMPANY + UrlConstants.IMPORT).flashAttr("uploadForm", form))
                .andExpect(status().isOk())
                .andExpect(view().name(UrlConstants.COMPANY + UrlConstants.REGIST)).andReturn();
    }

    /**
     * 登録画面のテスト
     *
     */
    @Test
    public void registTest() throws Exception {

    	MultiValueMap<String, String> formMap = new LinkedMultiValueMap<String, String>();
		formMap.add("mid", "1234568790123456789012");
		formMap.add("mallmapId", "test");
		formMap.add("ccid", "test");
        formMap.add("certificationKey", "test");
        formMap.add("companyName", "test");
        formMap.add("mainPhoneNumber", "test");
		formMap.add("mainAddress", "test");
        formMap.add("representativeName", "test");
        formMap.add("email", "test");
        formMap.add("runningStatus", "稼働中");

		MvcResult mvcResult = AppTestUtils.verifyMvcPerform(mockMvc, PERFORM.POST,
			UrlConstants.COMPANY + UrlConstants.REGIST,
			formMap,
            UrlConstants.COMPANY + UrlConstants.CONFIRM);
    }

}
