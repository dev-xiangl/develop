package tdpay.mvc.controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.isols.common.utils.ExcelUtils;
import jp.isols.common.utils.StringUtils;
import tdpay.mvc.common.UrlConstants;
import tdpay.mvc.dto.MerchantCompanySearchDto;
import tdpay.mvc.dto.MerchantCompanyTableDto;
import tdpay.mvc.entity.MerchantCompany;
import tdpay.mvc.form.MerchantCompanyRegistForm;
import tdpay.mvc.form.MerchantCompanySearchForm;
import tdpay.mvc.form.UploadForm;
import tdpay.mvc.service.MerchantCompanyManageService;
import tdpay.mvc.service.MerchantShopManageService;
import tdpay.mvc.service.SettingDataService;
import tdpay.mvc.service.shared.SystemPropertyService;

/**
 * 加盟店情報画面 コントローラークラス
 */
@Controller
@RequestMapping(value = UrlConstants.COMPANY)
public class CompanyController extends BaseController {

    @SuppressWarnings("unused")
    private static final Logger logger = LogManager.getLogger(CompanyController.class);

    @Autowired
    protected MerchantCompanyManageService merchantCompanyManageService;

    @Autowired
    protected MerchantShopManageService merchantShopManageService;

    @Autowired
    protected SystemPropertyService systemPropertyService;

    @Autowired
    protected SettingDataService settingDataService;

    @Autowired
    protected ModelMapper modelMapper;

    @RequestMapping(value = { "", UrlConstants.INDEX }, method = RequestMethod.GET)
    public String index(final Model model) {

		model.addAttribute("merchantCompanySearchForm", new MerchantCompanySearchForm());

        return UrlConstants.COMPANY + UrlConstants.INDEX;
    }

	@RequestMapping(value = UrlConstants.SEARCH, method = RequestMethod.POST)
    public String search(@Validated @ModelAttribute MerchantCompanySearchForm form, final BindingResult bindingResult, final Model model) {
        logger.debug("MerchantCompanySearchForm = {}", form);
        logger.debug("bindingResult = {}", bindingResult);

        final MerchantCompanySearchDto merchantCompanySearchDto = modelMapper.map(form, MerchantCompanySearchDto.class);
		final List<MerchantCompany> merchantCompanyList = merchantCompanyManageService.getMerchantCompanyList(merchantCompanySearchDto);
		List<MerchantCompanyTableDto> merchantCompanyTableDtoList =
            merchantCompanyList.stream().map(merchantCompany -> {
                MerchantCompanyTableDto merchantCompanyTableDto = new MerchantCompanyTableDto();
    			merchantCompanyTableDto = modelMapper.map(merchantCompany, MerchantCompanyTableDto.class);
                merchantCompanyTableDto.setCompanyId(merchantCompany.getId());
				return merchantCompanyTableDto;
			}).collect(Collectors.toList());

        model.addAttribute("merchantCompanySearchForm", form);
		model.addAttribute("merchantCompanyTableDtoList", merchantCompanyTableDtoList);

        return UrlConstants.COMPANY + UrlConstants.INDEX;
    }

    @RequestMapping(value = UrlConstants.ADD, method = RequestMethod.GET)
    public String add(final Model model) {

        model.addAttribute("uploadForm", new UploadForm());
        model.addAttribute("merchantCompanyRegistForm", new MerchantCompanyRegistForm());
        model.addAttribute("running", systemPropertyService.getCompannyRunning());
        model.addAttribute("stopOperation", systemPropertyService.getCompannyStopOperation());

        return UrlConstants.COMPANY + UrlConstants.REGIST;
    }

    @RequestMapping(value = UrlConstants.EDIT, method = RequestMethod.GET)
    public String edit(@RequestParam(value = "id", required = true) String companyId, final Model model) {

        final Long merchantCompanyId = Long.valueOf(StringUtils.decrypt(companyId));

        final MerchantCompany merchantCompany = merchantCompanyManageService.getMerchantCompany(merchantCompanyId);
        MerchantCompanyRegistForm merchantCompanyRegistForm = getMerchantCompanyRegistForm(merchantCompany);

        model.addAttribute("uploadForm", new UploadForm());
        model.addAttribute("merchantCompanyRegistForm", merchantCompanyRegistForm);
        model.addAttribute("running", systemPropertyService.getCompannyRunning());
        model.addAttribute("stopOperation", systemPropertyService.getCompannyStopOperation());

        return UrlConstants.COMPANY + UrlConstants.REGIST;
    }

    @RequestMapping(value = UrlConstants.DELETE, method = RequestMethod.GET)
    public String delete(final Model model) {
        return UrlConstants.COMPANY + UrlConstants.REGIST;
    }

	@RequestMapping(value = UrlConstants.REGIST, method = RequestMethod.POST)
    public String regist(@Validated @ModelAttribute MerchantCompanyRegistForm form, final BindingResult bindingResult, final Model model) {
        model.addAttribute("uploadForm", new UploadForm());
        model.addAttribute("running", systemPropertyService.getCompannyRunning());
        model.addAttribute("stopOperation", systemPropertyService.getCompannyStopOperation());

        if (bindingResult.hasErrors()) {
            return UrlConstants.COMPANY + UrlConstants.REGIST;
        }

        return UrlConstants.COMPANY + UrlConstants.CONFIRM;
    }

	@RequestMapping(value = UrlConstants.CONFIRM, method = RequestMethod.POST)
    public String confirm(@Validated @ModelAttribute MerchantCompanyRegistForm form, final BindingResult bindingResult, final Model model) {
        logger.debug("MerchantCompanyRegistForm = {}", form);
        MerchantCompany merchantCompany = merchantCompanyManageService.getMerchantCompanyByMId(form.getMid());
        if(merchantCompany == null){
            merchantCompany = modelMapper.map(form, MerchantCompany.class);
        }
        merchantCompany.setMid(form.getMid());
        merchantCompany.setCcid(form.getCcid());
        merchantCompany.setCertificationKey(form.getCertificationKey());
        merchantCompany.setCompanyName(form.getCompanyName());
        merchantCompany.setMainPhoneNumber(form.getMainPhoneNumber());
        merchantCompany.setMainAddress(form.getMainAddress());
        merchantCompany.setRepresentativeName(form.getRepresentativeName());
        merchantCompany.setEmail(form.getEmail());
        merchantCompany.setRunningStatus(form.getRunningStatus());

        logger.debug("merchantCompany = {}", merchantCompany);
        merchantCompanyManageService.saveMerchantCompany(merchantCompany);

        model.addAttribute("merchantCompanySearchForm", new MerchantCompanySearchForm());

        return UrlConstants.COMPANY + UrlConstants.INDEX;
    }

	@RequestMapping(value = UrlConstants.IMPORT, method = RequestMethod.POST)
    public String importCompany(@Validated @ModelAttribute UploadForm form, final Model model) {
        Long merchantCompanyId = null;

        model.addAttribute("running", systemPropertyService.getCompannyRunning());
        model.addAttribute("stopOperation", systemPropertyService.getCompannyStopOperation());

        if( form.getMultipartFile() == null ){
            model.addAttribute("merchantCompanyRegistForm", new MerchantCompanyRegistForm());
            return UrlConstants.COMPANY + UrlConstants.REGIST;
        }

        final Path path = Paths.get(System.getProperty("java.io.tmpdir"), form.getMultipartFile().getOriginalFilename());
        logger.debug("path: " +  path.toAbsolutePath().toString());

        if( path != null ){
            merchantCompanyId = saveImportData(path);
        }

        if( merchantCompanyId != null ){
            final MerchantCompany merchantCompany = merchantCompanyManageService.getMerchantCompany(merchantCompanyId);
            MerchantCompanyRegistForm merchantCompanyRegistForm = getMerchantCompanyRegistForm(merchantCompany);
            model.addAttribute("merchantCompanyRegistForm", merchantCompanyRegistForm);
        }else{
            model.addAttribute("merchantCompanyRegistForm", new MerchantCompanyRegistForm());
        }

        return UrlConstants.COMPANY + UrlConstants.REGIST;
    }

    private Long saveImportData(final Path path) {
        Long merchantCompanyId = null;
        Workbook workbook = ExcelUtils.getWorkbook(path);
        if( workbook == null ){
            return null;
        }
        Integer sheetMaxIndex = workbook.getNumberOfSheets();
        Integer counter = 0;
        while(counter < sheetMaxIndex){
            Sheet sheet = ExcelUtils.getSheet(workbook, counter);
            List<Object> objectList = settingDataService.getObjectList(workbook, sheet);
            if( counter == 0 ){
                merchantCompanyId = merchantCompanyManageService.saveMerchantCompany(objectList, systemPropertyService.getCompannyRunning());
            }else{
                merchantShopManageService.saveMerchantShop(objectList, systemPropertyService.getCompannyRunning());
            }
            counter++;
        }
        return merchantCompanyId;
    }

    private MerchantCompanyRegistForm getMerchantCompanyRegistForm(final MerchantCompany merchantCompany) {
        MerchantCompanyRegistForm merchantCompanyRegistForm = modelMapper.map(merchantCompany, MerchantCompanyRegistForm.class);
        merchantCompanyRegistForm.setMid(merchantCompany.getMid());
        merchantCompanyRegistForm.setMallmapId(merchantCompany.getMallmapId());
        merchantCompanyRegistForm.setCcid(merchantCompany.getCcid());
        merchantCompanyRegistForm.setCertificationKey(merchantCompany.getCertificationKey());
        merchantCompanyRegistForm.setCompanyName(merchantCompany.getCompanyName());
        merchantCompanyRegistForm.setMainPhoneNumber(merchantCompany.getMainPhoneNumber());
        merchantCompanyRegistForm.setMainAddress(merchantCompany.getMainAddress());
        merchantCompanyRegistForm.setRepresentativeName(merchantCompany.getRepresentativeName());
        merchantCompanyRegistForm.setEmail(merchantCompany.getEmail());
        merchantCompanyRegistForm.setRunningStatus(merchantCompany.getRunningStatus());

        return merchantCompanyRegistForm;
    }

}
