package tdpay.mvc.controller;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import jp.isols.common.constants.Flags;
import jp.isols.common.utils.JsonUtils;
import tdpay.mvc.common.UrlConstants;
import tdpay.mvc.dto.JsonResultDto;
import tdpay.mvc.dto.QrCodeDto;
import tdpay.mvc.service.CryptoService;
import tdpay.mvc.service.QrCodeDataService;
import tdpay.mvc.service.SendService;
import tdpay.mvc.service.SettingDataService;

/**
 * ログイン画面 コントローラークラス
 */
@Controller
@RequestMapping(value = UrlConstants.PAYMENT)
public class PaymentController extends BaseController {

    @SuppressWarnings("unused")
    private static final Logger logger = LogManager.getLogger(PaymentController.class);

    @Autowired
    protected QrCodeDataService qrCodeDataService; 

    @Autowired
    protected SendService sendService;

    @Autowired
    protected CryptoService cryptoService; 

    @Autowired
    protected SettingDataService settingDataService;

    @RequestMapping(value = { "", UrlConstants.INDEX }, method = RequestMethod.GET)
    public String index(@RequestParam(value = "qrcode") String qrCode, final Model model) {
        logger.debug("QRコード: " + qrCode);

        QrCodeDto qrCodeDto = null;
        try {
            qrCodeDto = JsonUtils.toObject(cryptoService.decrypt(qrCode), QrCodeDto.class);
            if (qrCodeDto == null) {
                return UrlConstants.PAYMENT_ERROR;
            }
        } catch(Exception e) {
            return UrlConstants.PAYMENT_ERROR;
        }

        logger.debug("QRコード-DTO: {}", qrCodeDto);

        return UrlConstants.PAYMENT + UrlConstants.INDEX;
    }

    /* サンプル */
    @RequestMapping(value = "aes256info", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String aes256info(final String param) {
        final Map<String, Object> paramMap = JsonUtils.toMap(param);

        JsonResultDto jsonResultDto = new JsonResultDto();
        jsonResultDto.setStatus(Flags.SUCCESS);
        jsonResultDto.addParamMap("encrypt", cryptoService.encrypt(paramMap.get("qrcode").toString()));

        return JsonUtils.toString(jsonResultDto);
    }

    /* サンプル */
    @RequestMapping(value = { "", UrlConstants.RESULT })
    public String result(final Model model) {
        final String param = "0101020506040708";

        /* 本来なら、ここでJSON形式で変換 */
        String hash = cryptoService.sha256Encode(param, "8401");

        model.addAttribute("hash", hash);

        return UrlConstants.PAYMENT + UrlConstants.RESULT;
    }

    /* send発火用 */
    @RequestMapping(value = UrlConstants.SEND)
    public void sender() {
        sendService.send();
    }

}