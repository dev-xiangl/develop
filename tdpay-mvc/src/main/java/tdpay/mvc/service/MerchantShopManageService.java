package tdpay.mvc.service;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import jp.isols.common.constants.Flags;
import jp.isols.common.utils.StringUtils;
import tdpay.mvc.dto.MerchantShopSearchDto;
import tdpay.mvc.entity.MerchantCompany;
import tdpay.mvc.entity.MerchantPaymentType;
import tdpay.mvc.entity.MerchantShop;
import tdpay.mvc.repository.MerchantCompanyRepository;
import tdpay.mvc.repository.MerchantPaymentTypeRepository;
import tdpay.mvc.repository.MerchantShopRepository;
import tdpay.mvc.utils.UserDetailsUtils;

/**
 *
 */
@Service
public class MerchantShopManageService {

    @SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(MerchantShopManageService.class);

    @Autowired
    protected MerchantCompanyRepository merchantCompanyRepository;

    @Autowired
    protected MerchantShopRepository merchantShopRepository;

    @Autowired
    protected MerchantPaymentTypeRepository merchantPaymentTypeRepository;

    @Transactional
    public MerchantShop getMerchantShop(final Long merchantShopId) {
    	return merchantShopRepository.findById(merchantShopId).orElse(null);
    }

    @Transactional
    public MerchantShop getMerchantShopecShopCode(final String ecShopCode) {
        return merchantShopRepository.findByecShopCode(ecShopCode);
    }

    @Transactional
    public List<MerchantShop> getMerchantShopList() {
        return merchantShopRepository.findByEnabled();
    }

    @Transactional
    public List<MerchantShop> getMerchantShopList(final MerchantShopSearchDto merchantShopSearchDto) {
        MerchantShop entity = new MerchantShop();
        if (!StringUtils.isBlank(merchantShopSearchDto.getEcShopCode())) {
            entity.setEcShopCode(merchantShopSearchDto.getEcShopCode());
        }
        if (!StringUtils.isBlank(merchantShopSearchDto.getShopName())) {
            entity.setShopName(merchantShopSearchDto.getShopName());
        }
        if (!StringUtils.isBlank(merchantShopSearchDto.getShopAddress())) {
            entity.setShopAddress(merchantShopSearchDto.getShopAddress());
        }
        if (!StringUtils.isBlank(merchantShopSearchDto.getShopPhoneNumber())) {
            entity.setShopPhoneNumber(merchantShopSearchDto.getShopPhoneNumber());
        }
        entity.setEnableFlag(Flags.ENABLE);

        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues();
    	Example<MerchantShop> example = Example.of(entity, matcher);

        List<MerchantShop> list = merchantShopRepository.findAll(example);
        return list;
    }

    @Transactional
    public List<MerchantPaymentType> getMerchantPaymentTypeList() {
        return getMerchantPaymentTypeList(null, null);
    }

    @Transactional
    public List<MerchantPaymentType> getMerchantPaymentTypeList(final Long merchantCompanyId, final Long merchantShopId) {
        MerchantPaymentType entity = new MerchantPaymentType();
        if (merchantCompanyId != null) {
            entity.setMerchantCompanyId(merchantCompanyId);
        }
        if (merchantShopId != null) {
            entity.setMerchantShopId(merchantShopId);
        }
        entity.setEnableFlag(Flags.ENABLE);

        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues();
    	Example<MerchantPaymentType> example = Example.of(entity, matcher);

        List<MerchantPaymentType> list = merchantPaymentTypeRepository.findAll(example);
        return list;
    }

    @Transactional
    public void saveMerchantShop(MerchantShop merchantShop) {
        merchantShop.setUpdatedBy(UserDetailsUtils.getLoginId());
        merchantShop.setUpdatedDatetime(LocalDateTime.now());
        merchantShop.setEnableFlag( Flags.ENABLE );
        merchantShop.setCreatedBy( UserDetailsUtils.getLoginId() );
        merchantShop.setCreatedDatetime( LocalDateTime.now() );

        merchantShopRepository.save(merchantShop);
    }

    @Transactional
    public void saveMerchantShop(List<Object> objectList, String running) {
        MerchantShop merchantShop = new MerchantShop();

        if( getMerchantShopecShopCode( objectList.get(1).toString() ) == null ){
            merchantShop.setId( merchantShopRepository.getMaxId() + 1 );
            Double merchantCompanyId = Double.parseDouble(String.valueOf(objectList.get(2)));
            merchantShop.setMerchantCompanyId( merchantCompanyId.longValue() );

            merchantShop.setRunningStatus( running );
            merchantShop.setEnableFlag( Flags.ENABLE );
            merchantShop.setCreatedBy( UserDetailsUtils.getLoginId() );
            merchantShop.setCreatedDatetime( LocalDateTime.now() );
        }else{
            merchantShop = getMerchantShopecShopCode( objectList.get(1).toString() );
            merchantShop.setUpdatedBy( UserDetailsUtils.getLoginId() );
            merchantShop.setUpdatedDatetime( LocalDateTime.now() );
        }
        Double merchantPaymentTypeId = Double.parseDouble(String.valueOf(objectList.get(2)));

        merchantShop.setEcShopCode( objectList.get(1).toString() );
        merchantShop.setShopName( objectList.get(0).toString() );
        merchantShop.setShopPhoneNumber( objectList.get(4).toString() );
        merchantShop.setShopAddress( objectList.get(5).toString() );
        merchantShop.setShopManagerName( objectList.get(6).toString() );
        merchantShop.setMerchantPaymentTypeId( merchantPaymentTypeId.longValue() );
        merchantShop.setAnyMessage( objectList.get(7).toString() );
        merchantShop.setSaltKey( objectList.get(3).toString() );
        logger.debug("merchantShop = {}", merchantShop);

        merchantShopRepository.save(merchantShop);
    }

}
