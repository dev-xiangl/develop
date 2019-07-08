package tdpay.mvc.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import jp.isols.common.utils.JsonUtils;
import jp.isols.common.utils.StringUtils;
import tdpay.mvc.dto.QrCodeDto;

/**
 *
 */
@Service
public class SendService {

    @SuppressWarnings("unused")
    private static final Logger logger = LogManager.getLogger(SendService.class);

    @Autowired
    protected RestTemplate restTemplate;

    /**
     * データ送信を行う。
     *
     * @param json 暗号化対象のJSON文字列
     * @param url  送信先のURL
     */
    @Transactional
    public void send() {
        String json = "{\"paymentId\":\"b\",\"code1\":\"b\",\"code2\":\"b\",\"code3\":\"b\"}";
        String encrypted = StringUtils.encrypt(json);
        String URLjika = "http://localhost:8080/tdpay/payment?json=" + encrypted;
        logger.debug("JSON: " + json);
        logger.debug("StringUtils.encrypt: " + encrypted);
        logger.debug("URL: " + URLjika);

        Integer counter = 0;
        logger.debug("Send-Start");

        while (sendEncryptData(json, URLjika) != true) {
            counter++;
            if (counter >= 3) break;
            logger.debug("Send-Retly");
        }
        logger.debug("Send-End");
    }

    /**
     * 暗号化データ送信を行う。
     *
     * @param json 暗号化対象のJSON文字列
     * @param url  送信先のURL
     */
    @Transactional
    public Boolean sendEncryptData(String json, String url) {
        QrCodeDto qrCodeDto = null;
        Boolean result = true;

        try {
            qrCodeDto = restTemplate.getForObject(url, QrCodeDto.class);
            logger.debug("qrCodeDto: {}", qrCodeDto);
        } catch (RestClientException e) {
            /* 例外ハンドリング */
            checkError(e);
            result = false;
        }
        return result;
    }

    @Transactional
    public void checkError(RestClientException e) {
        if(e.getRootCause() instanceof ResourceAccessException){
            logger.debug("Send-Error: {}", e);
        }else if(e.getRootCause() instanceof RestClientResponseException){
            logger.debug("Send-Error: {}", e);
        }else{
            logger.debug("Send-Error: {}", e.getMessage());
        }
    }
    
    @ResponseBody
    public QrCodeDto receive(@RequestParam(value = "json", required = true) String json) {
        try {
            int sleepTime = 1000; 
            Thread.sleep(sleepTime); /* 1000ms */
        } catch (InterruptedException e) {
            /* 例外ハンドリング */
        }
        logger.debug("json: " + json);
        logger.debug("StringUtils.decrypt: {}", StringUtils.decrypt(json));

        QrCodeDto qrCodeDto = JsonUtils.toObject(StringUtils.decrypt(json), QrCodeDto.class);
        logger.debug("Return_qrCodeDto: {}", qrCodeDto);

        return qrCodeDto;
    }

}