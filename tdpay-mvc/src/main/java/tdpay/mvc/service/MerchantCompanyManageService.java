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

import jp.isols.common.constants.Flags;
import jp.isols.common.utils.StringUtils;
import tdpay.mvc.dto.MerchantCompanySearchDto;
import tdpay.mvc.entity.MerchantCompany;
import tdpay.mvc.repository.MerchantCompanyRepository;
import tdpay.mvc.utils.UserDetailsUtils;

/**
 *
 */
@Service
public class MerchantCompanyManageService {

    @SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(MerchantCompanyManageService.class);

    @Autowired
    protected MerchantCompanyRepository merchantCompanyRepository;

    @Transactional
    public MerchantCompany getMerchantCompany(final Long merchantCompanyId) {
        Long count = merchantCompanyRepository.count();
        logger.info("merchantCompanyRepository.count(): " + count);

    	return merchantCompanyRepository.findById(merchantCompanyId).orElse(null);
    }

    @Transactional
    public MerchantCompany getMerchantCompanyByMId(final String mid) {
        return merchantCompanyRepository.findByMid(mid);
    }

    @Transactional
    public List<MerchantCompany> getMerchantCompanyList() {
        return merchantCompanyRepository.findByEnabled();
    }

    @Transactional
    public List<MerchantCompany> getMerchantCompanyList(final MerchantCompanySearchDto merchantCompanySearchDto) {
        MerchantCompany entity = new MerchantCompany();
        if (!StringUtils.isBlank(merchantCompanySearchDto.getCompanyName())) {
            entity.setCompanyName(merchantCompanySearchDto.getCompanyName());
        }
        if (!StringUtils.isBlank(merchantCompanySearchDto.getRepresentativeName())) {
            entity.setRepresentativeName(merchantCompanySearchDto.getRepresentativeName());
        }
        if (!StringUtils.isBlank(merchantCompanySearchDto.getMainAddress())) {
            entity.setMainAddress(merchantCompanySearchDto.getMainAddress());
        }
        if (!StringUtils.isBlank(merchantCompanySearchDto.getMainPhoneNumber())) {
            entity.setMainPhoneNumber(merchantCompanySearchDto.getMainPhoneNumber());
        }
        if (!StringUtils.isBlank(merchantCompanySearchDto.getEmail())){
            entity.setEmail(merchantCompanySearchDto.getEmail());
        }
        if (!StringUtils.isBlank(merchantCompanySearchDto.getRunningStatus())){
            entity.setRunningStatus(merchantCompanySearchDto.getRunningStatus());
        }
        
        entity.setEnableFlag(Flags.ENABLE);

        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues();
      	Example<MerchantCompany> example = Example.of(entity, matcher);

        List<MerchantCompany> list = merchantCompanyRepository.findAll(example);
        return list;
    }

    @Transactional
    public void saveMerchantCompany(MerchantCompany merchantCompany) {
        merchantCompany.setUpdatedBy(UserDetailsUtils.getLoginId());
        merchantCompany.setUpdatedDatetime(LocalDateTime.now());
        merchantCompany.setEnableFlag( Flags.ENABLE );
        merchantCompany.setCreatedBy( UserDetailsUtils.getLoginId() );
        merchantCompany.setCreatedDatetime( LocalDateTime.now() );

        merchantCompanyRepository.save(merchantCompany);
    }

    @Transactional
    public Long saveMerchantCompany(List<Object> objectList, String running) {
        MerchantCompany merchantCompany = new MerchantCompany();

        if( getMerchantCompanyByMId( objectList.get(1).toString() ) == null ){
            merchantCompany.setId( merchantCompanyRepository.getMaxId() + 1 );
            merchantCompany.setMid( objectList.get(1).toString() );
            merchantCompany.setCompanyName( objectList.get(0).toString() );
 
            merchantCompany.setRunningStatus( running );
            merchantCompany.setEnableFlag( Flags.ENABLE );
            merchantCompany.setCreatedBy( UserDetailsUtils.getLoginId() );
            merchantCompany.setCreatedDatetime( LocalDateTime.now() );
        }else{
            merchantCompany = getMerchantCompanyByMId( objectList.get(1).toString() );
            merchantCompany.setUpdatedBy( UserDetailsUtils.getLoginId() );
            merchantCompany.setUpdatedDatetime( LocalDateTime.now() );
        }
        merchantCompany.setMallmapId( objectList.get(2).toString() );
        merchantCompany.setCcid( objectList.get(3).toString() );
        merchantCompany.setCertificationKey( objectList.get(4).toString() );
        merchantCompany.setMainPhoneNumber( objectList.get(5).toString() );
        merchantCompany.setMainAddress( objectList.get(6).toString() );
        merchantCompany.setRepresentativeName( objectList.get(7).toString() );
        merchantCompany.setEmail( objectList.get(8).toString() );
        logger.debug("merchantCompany = {}", merchantCompany);

        merchantCompanyRepository.save(merchantCompany);

        return merchantCompany.getId();
    }

}
