package tdpay.mvc.service;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.isols.common.utils.JsonUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;

import tdpay.mvc.entity.SystemProperty;
import tdpay.mvc.repository.SystemPropertyRepository;

/**
 *
 */
@Service
public class QrCodeDataService {

    @SuppressWarnings("unused")
    private static final Logger logger = LogManager.getLogger(QrCodeDataService.class);

    @Autowired
    protected SystemPropertyRepository systemPropertyRepository;

	@Transactional
    public List<SystemProperty> findSystemProperty(SystemProperty systemProperty) {
        Example<SystemProperty> example = Example.of(systemProperty);
        return systemPropertyRepository.findAll(example);
    }

	@Transactional
    public void deleateSystemProperty(List<SystemProperty> systemPropertylist) {
        for( SystemProperty e : systemPropertylist ){
            logger.debug("systemPropertylist: {}", systemPropertylist);
            systemPropertyRepository.deleteById(e.getId());
        }
    }

    /**
     * 複合化QRコードのDB登録を行う。
     *
     * @param decrypted 複合化済み文字列
     */
    @Transactional
    public void setQrCodeData(String decrypted) {
        SystemProperty systemProperty = new SystemProperty();
        String str = "{\"paymentId\":\"b\",\"code1\":\"b\",\"code2\":\"b\",\"code3\":\"b\"}";

        systemProperty.setValue(JsonUtils.toString(decrypted));
        List<SystemProperty> systemPropertylist = findSystemProperty(systemProperty);
        logger.debug("LiseSize: {}", systemPropertylist.size());

        if (systemPropertylist.size() != 0) {
            logger.debug("Configured-systemProperty: {}", systemPropertylist);
            for( SystemProperty e : systemPropertylist ){
                e.setValue( JsonUtils.toString(decrypted) );
            }
            systemPropertyRepository.saveAll(systemPropertylist);
        } else {
            /*
            systemProperty.setValue(str);
            systemProperty.setCode("a");
            systemProperty.setCreatedBy("a");
            systemProperty.setCreatedDatetime(LocalDateTime.of(2019, 06, 11, 14, 35));
            logger.debug("Setting_systemProperty: {}", systemProperty);
            systemPropertyRepository.save(systemProperty);
            */
        }
        
    }

}