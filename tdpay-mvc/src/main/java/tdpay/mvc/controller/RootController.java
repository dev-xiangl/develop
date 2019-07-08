package tdpay.mvc.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import tdpay.mvc.common.UrlConstants;
import tdpay.mvc.utils.UserDetailsUtils;

/**
 * ルート コントローラークラス
 */
@Controller
@RequestMapping
public class RootController extends BaseController {

    @SuppressWarnings("unused")
    private static final Logger logger = LogManager.getLogger(RootController.class);

    @RequestMapping(value = { "", UrlConstants.INDEX }, method = RequestMethod.GET)
    public String index(final Model model) {
        if (!UserDetailsUtils.isLoggedIn()) {
            return "redirect:" + UrlConstants.LOGIN;
        }

        return UrlConstants.INDEX;
    }

    // サンプル画面
    @RequestMapping(value = "qrcode", method = RequestMethod.GET)
    public String qrcode(final Model model) {
        return "qrcode";
    }
}
