package tdpay.mvc.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

import jp.isols.common.utils.StringUtils;
import tdpay.mvc.common.UrlConstants;
import tdpay.mvc.dto.MerchantPaymentTypeTableDto;
import tdpay.mvc.dto.MerchantShopSearchDto;
import tdpay.mvc.dto.MerchantShopTableDto;
import tdpay.mvc.entity.MerchantCompany;
import tdpay.mvc.entity.MerchantPaymentType;
import tdpay.mvc.entity.MerchantShop;
import tdpay.mvc.entity.PaymentType;
import tdpay.mvc.form.MerchantShopRegistForm;
import tdpay.mvc.form.MerchantShopSearchForm;
import tdpay.mvc.service.MerchantCompanyManageService;
import tdpay.mvc.service.MerchantShopManageService;
import tdpay.mvc.service.PaymentTypeManageService;
import tdpay.mvc.service.shared.SystemPropertyService;

/**
 * 店舗情報画面 コントローラークラス
 */
@Controller
@RequestMapping(value = UrlConstants.SHOP)
public class ShopController extends BaseController {

    @SuppressWarnings("unused")
    private static final Logger logger = LogManager.getLogger(ShopController.class);

    @Autowired
    protected MerchantCompanyManageService merchantCompanyManageService;

    @Autowired
    protected MerchantShopManageService merchantShopManageService;

    @Autowired
    protected SystemPropertyService systemPropertyService;

    @Autowired
    protected PaymentTypeManageService paymentTypeManageService;

    @Autowired
    protected ModelMapper modelMapper;

    @RequestMapping(value = { "", UrlConstants.INDEX }, method = RequestMethod.GET)
    public String index(final Model model) {

		model.addAttribute("merchantShopSearchForm", new MerchantShopSearchForm());

        return UrlConstants.SHOP + UrlConstants.INDEX;
    }

	@RequestMapping(value = UrlConstants.SEARCH, method = RequestMethod.POST)
    public String search(@Validated @ModelAttribute MerchantShopSearchForm form, final BindingResult bindingResult, final Model model) {
        logger.debug("MerchantShopSearchForm = {}", form);
        logger.debug("bindingResult = {}", bindingResult);

        final MerchantShopSearchDto merchantShopSearchDto = modelMapper.map(form, MerchantShopSearchDto.class);
		final List<MerchantShop> merchantShopList = merchantShopManageService.getMerchantShopList(merchantShopSearchDto);
		final List<MerchantCompany> merchantCompanyList = merchantCompanyManageService.getMerchantCompanyList();

		List<MerchantShopTableDto> merchantShopTableDtoList =
            merchantShopList.stream().map(merchantShop -> {
                final MerchantCompany merchantCompany =
                    merchantCompanyList.stream()
                        .filter(x -> x.getId().equals(merchantShop.getMerchantCompanyId()))
                        .findFirst()
                        .orElse(null);

                MerchantShopTableDto merchantShopTableDto = new MerchantShopTableDto();
    			merchantShopTableDto = modelMapper.map(merchantShop, MerchantShopTableDto.class);
                if (merchantCompany != null) {
                    merchantShopTableDto.setMid(merchantCompany.getMid());
                }
                merchantShopTableDto.setMerchantShopId(merchantShop.getId());
				return merchantShopTableDto;
			}).collect(Collectors.toList());

		model.addAttribute("merchantShopSearchForm", form);
		model.addAttribute("merchantShopTableDtoList", merchantShopTableDtoList);

        return UrlConstants.SHOP + UrlConstants.INDEX;
    }

    @RequestMapping(value = UrlConstants.ADD, method = RequestMethod.GET)
    public String add(@RequestParam(value = "cid", required = true) String paramMerchantCompanyId, final Model model) {

        final Long merchantCompanyId = Long.valueOf(StringUtils.decrypt(paramMerchantCompanyId));

		final List<MerchantPaymentType> merchantPaymentTypeList = merchantShopManageService.getMerchantPaymentTypeList(merchantCompanyId, null);
		List<MerchantPaymentTypeTableDto> merchantPaymentTypeTableDtoList = setMerchantPaymentTypeTableList(merchantPaymentTypeList);

		model.addAttribute("merchantShopRegistForm", new MerchantShopRegistForm());
        model.addAttribute("merchantPaymentTypeTableDto", merchantPaymentTypeTableDtoList);
        model.addAttribute("running", systemPropertyService.getCompannyRunning());
        model.addAttribute("stopOperation", systemPropertyService.getCompannyStopOperation());        
    
        return UrlConstants.SHOP + UrlConstants.REGIST;
    }

    @RequestMapping(value = UrlConstants.EDIT, method = RequestMethod.GET)
    public String edit(@RequestParam(value = "id", required = true) String paramMerchantShopId, final Model model) {

        final Long merchantShopId = Long.valueOf(StringUtils.decrypt(paramMerchantShopId));

        final MerchantShop merchantShop = merchantShopManageService.getMerchantShop(merchantShopId);
        final List<MerchantPaymentType> merchantPaymentTypeList = merchantShopManageService.getMerchantPaymentTypeList(merchantShop.getMerchantCompanyId(), null);
		List<MerchantPaymentTypeTableDto> merchantPaymentTypeTableDtoList = setMerchantPaymentTypeTableList(merchantPaymentTypeList);

        MerchantShopRegistForm merchantShopRegistForm = getMerchantShopRegistForm(merchantShop);

        model.addAttribute("running", systemPropertyService.getCompannyRunning());
        model.addAttribute("stopOperation", systemPropertyService.getCompannyStopOperation());
		model.addAttribute("merchantShopRegistForm", merchantShopRegistForm);
		model.addAttribute("merchantPaymentTypeTableDto", merchantPaymentTypeTableDtoList);

        return UrlConstants.SHOP + UrlConstants.REGIST;
    }

    @RequestMapping(value = UrlConstants.DELETE, method = RequestMethod.GET)
    public String delete(final Model model) {
        return UrlConstants.SHOP + UrlConstants.REGIST;
    }

	@RequestMapping(value = UrlConstants.REGIST, method = RequestMethod.POST)
    public String regist(@Validated @ModelAttribute MerchantShopRegistForm form, final BindingResult bindingResult, final Model model) {

        MerchantShop merchantShop = merchantShopManageService.getMerchantShopecShopCode(form.getEcShopCode());
        if(merchantShop == null){
            merchantShop = modelMapper.map(form, MerchantShop.class);
        }
        final List<MerchantPaymentType> merchantPaymentTypeList = merchantShopManageService.getMerchantPaymentTypeList(merchantShop.getMerchantCompanyId(), null);
		List<MerchantPaymentTypeTableDto> merchantPaymentTypeTableDtoList = setMerchantPaymentTypeTableList(merchantPaymentTypeList);

        model.addAttribute("running", systemPropertyService.getCompannyRunning());
        model.addAttribute("stopOperation", systemPropertyService.getCompannyStopOperation());    
        model.addAttribute("merchantPaymentTypeTableDto", merchantPaymentTypeTableDtoList);

        if (bindingResult.hasErrors()) {
            return UrlConstants.SHOP + UrlConstants.REGIST;
        }
        merchantShop.setShopName(form.getShopName());
        merchantShop.setShopPhoneNumber(form.getShopPhoneNumber());
        merchantShop.setShopAddress(form.getShopAddress());
        merchantShop.setShopManagerName(form.getShopManagerName());
        merchantShop.setAnyMessage(form.getAnyMessage());
        merchantShop.setSaltKey(form.getSaltKey());
        merchantShop.setRunningStatus(form.getRunningStatus());

        logger.debug("merchantShop = {}", merchantShop);
        merchantShopManageService.saveMerchantShop(merchantShop);

        return UrlConstants.SHOP + UrlConstants.REGIST;
    }

	@RequestMapping(value = UrlConstants.CONFIRM, method = RequestMethod.GET)
    public String confirm(@Validated @ModelAttribute MerchantShopRegistForm form, final BindingResult bindingResult, final Model model) {
        logger.debug("MerchantShopRegistForm = {}", form);
        MerchantShop merchantShop = merchantShopManageService.getMerchantShopecShopCode(form.getEcShopCode());
        if(merchantShop == null){
            merchantShop = modelMapper.map(form, MerchantShop.class);
        }
        merchantShop.setShopName(form.getShopName());
        merchantShop.setShopPhoneNumber(form.getShopPhoneNumber());
        merchantShop.setShopAddress(form.getShopAddress());
        merchantShop.setShopManagerName(form.getShopManagerName());
        merchantShop.setAnyMessage(form.getAnyMessage());
        merchantShop.setSaltKey(form.getSaltKey());
        merchantShop.setRunningStatus(form.getRunningStatus());

        logger.debug("merchantShop = {}", merchantShop);
        merchantShopManageService.saveMerchantShop(merchantShop);

        return UrlConstants.COMPANY + UrlConstants.INDEX;
    }

    private List<MerchantPaymentTypeTableDto> setMerchantPaymentTypeTableList(final List<MerchantPaymentType> merchantPaymentTypeList) {
        final List<PaymentType> paymentTypeList = paymentTypeManageService.getPaymentTypeList();

        List<MerchantPaymentTypeTableDto> merchantPaymentTypeTableDtoList =
            merchantPaymentTypeList.stream().map(merchantPaymentType -> {
                MerchantPaymentTypeTableDto merchantPaymentTypeTableDto = new MerchantPaymentTypeTableDto();
    			merchantPaymentTypeTableDto = modelMapper.map(merchantPaymentType, MerchantPaymentTypeTableDto.class);
                merchantPaymentTypeTableDto.setPaymentTypeId(merchantPaymentType.getId());
                merchantPaymentTypeTableDto.setMerchantPaymentTypeId(merchantPaymentType.getPaymentTypeId());
                merchantPaymentTypeTableDto.setName(
                    paymentTypeList.stream().filter(x -> x.getId().equals(merchantPaymentType.getPaymentTypeId())).findFirst().map(x -> x.getName()).orElse(null));

                return merchantPaymentTypeTableDto;
			}).collect(Collectors.toList());

        return merchantPaymentTypeTableDtoList;
    }    

    private MerchantShopRegistForm getMerchantShopRegistForm(final MerchantShop merchantShop) {
        MerchantShopRegistForm merchantShopRegistForm = modelMapper.map(merchantShop, MerchantShopRegistForm.class);
        merchantShopRegistForm.setMerchantCompanyId(merchantShop.getMerchantCompanyId().toString());
        merchantShopRegistForm.setEcShopCode(merchantShop.getEcShopCode());
        merchantShopRegistForm.setShopName(merchantShop.getShopName());
        merchantShopRegistForm.setShopPhoneNumber(merchantShop.getShopPhoneNumber());
        merchantShopRegistForm.setShopAddress(merchantShop.getShopAddress());
        merchantShopRegistForm.setShopManagerName(merchantShop.getShopManagerName());
        merchantShopRegistForm.setMerchantPaymentTypeId(merchantShop.getMerchantPaymentTypeId().toString());
        merchantShopRegistForm.setAnyMessage(merchantShop.getAnyMessage());
        merchantShopRegistForm.setSaltKey(merchantShop.getSaltKey());
        merchantShopRegistForm.setRunningStatus(merchantShop.getRunningStatus());

        return merchantShopRegistForm;
    }

}
