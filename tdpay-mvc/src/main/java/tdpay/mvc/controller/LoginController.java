package tdpay.mvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import tdpay.mvc.common.UrlConstants;
import tdpay.mvc.form.LoginForm;

/**
 * ログイン画面 コントローラークラス
 */
@Controller
@RequestMapping(value = UrlConstants.LOGIN)
public class LoginController extends BaseController {

    @SuppressWarnings("unused")
    private static final Logger logger = LogManager.getLogger(LoginController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String login(final Model model, final HttpServletRequest request) {
        model.addAttribute("loginForm", new LoginForm());

        this.clearSession(request);

        return UrlConstants.LOGIN;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String execute(@Validated @ModelAttribute LoginForm form, final BindingResult bindingResult) {
        logger.debug("{}", form);

        if (bindingResult.hasErrors()) {
            return UrlConstants.LOGIN;
        }

        return UrlConstants.LOGIN;
    }

    /**
     * セッションをクリアする。
     */
    private void clearSession(HttpServletRequest request) {
        SecurityContextHolder.clearContext();
        HttpSession session = request.getSession();
        if (session == null) {
            return;
        }

        session.invalidate();
        request.getSession(true);
    }

}
