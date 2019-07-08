package tdpay.mvc.controller;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import jp.isols.common.constants.Flags;
import jp.isols.common.utils.StringUtils;
import tdpay.mvc.common.UrlConstants;
import tdpay.mvc.dto.JsonResultDto;
import tdpay.mvc.utils.ServletRequestUtils;
import tdpay.mvc.utils.UserDetailsUtils;

/**
 * 基底コントローラークラス
 */
public abstract class BaseController {

    @SuppressWarnings("unused")
    private static final Logger logger = LogManager.getLogger(BaseController.class);

    @Autowired
    protected MessageSource messageSource;

    @PostConstruct
    public void initializeBase() {
    }

    /**
     * Modelに対する、画面初期動作を行う。
     *
     * @param model
     */
    @ModelAttribute
    protected void initModelAttributeBase(final Model model) {
        if (UserDetailsUtils.getUserDetails() != null) {
            model.addAttribute("loggedInUserId", UserDetailsUtils.getUserId());
            model.addAttribute("loggedInLoginId", UserDetailsUtils.getLoginId());
            model.addAttribute("loggedInName", UserDetailsUtils.getName());
            model.addAttribute("loggedInRoleId", UserDetailsUtils.getRoleId());
            model.addAttribute("loggedInRoleName", UserDetailsUtils.getRoleName());
        } else {
            model.addAttribute("loggedInUserId", "");
            model.addAttribute("loggedInAccountId", "");
            model.addAttribute("loggedInUserName", "");
            model.addAttribute("loggedInRoleId", "");
        }
    }

    /**
     * 画面フォームの表示モードを取得する。
     *
     * @param baseUrlRoot ベースとなるURLルート
     * @return 表示モード
     * @retval UrlConstants.Mode UrlConstants.Modeクラスのインスタンス
     */
    protected String getFormMode(final String baseUrlRoot) {
        final String servletPath = ServletRequestUtils.getHttpServletRequest().getServletPath();
        logger.debug("servletPath: {}", servletPath);

        if (servletPath.equals(baseUrlRoot + "/" + UrlConstants.Mode.ADD)) {
            /* ADD */
            return UrlConstants.Mode.ADD;
        } else if (servletPath.equals(baseUrlRoot + "/" + UrlConstants.Mode.INSERT)) {
            /* INSERT */
            return UrlConstants.Mode.INSERT;
        } else if (servletPath.equals(baseUrlRoot + "/" + UrlConstants.Mode.UPDATE)) {
            /* UPDATE */
            return UrlConstants.Mode.UPDATE;
        } else if (servletPath.equals(baseUrlRoot + "/" + UrlConstants.Mode.EDIT)) {
            /* EDIT */
            return UrlConstants.Mode.EDIT;
        } else if (servletPath.equals(baseUrlRoot + "/" + UrlConstants.Mode.DELETE)) {
            /* DELETE */
            return UrlConstants.Mode.DELETE;
        }

        /* VIEW */
        return UrlConstants.Mode.VIEW;
    }

    /**
     * 正常終了時のメッセージを設定する。
     *
     * @param model
     * @param mode
     */
    protected void setSuccessMessage(final Model model, final String mode) {
        JsonResultDto jsonResultDto = new JsonResultDto();
        jsonResultDto.setStatus(Flags.SUCCESS);
        if (StringUtils.isEmpty(mode)) {
            jsonResultDto.setMessage(messageSource.getMessage("common.success.message", null, null));
        } else if (mode.equals(UrlConstants.Mode.ADD)) {
            jsonResultDto.setMessage(messageSource.getMessage("common.save.finish.message", null, null));
        } else if (mode.equals(UrlConstants.Mode.UPDATE)) {
            jsonResultDto.setMessage(messageSource.getMessage("common.update.finish.message", null, null));
        } else if (mode.equals(UrlConstants.Mode.DELETE)) {
            jsonResultDto.setMessage(messageSource.getMessage("common.delete.finish.message", null, null));
        } else {
            jsonResultDto.setMessage(messageSource.getMessage(mode, null, null));
        }
        model.addAttribute("jsonResultDto", jsonResultDto);
    }

    /**
     * 正常終了時のメッセージを設定する。
     *
     * @param model
     */
    protected void setSuccessMessage(final Model model) {
        setSuccessMessage(model, null);
    }

    /**
     * エラー時のメッセージを設定する。
     *
     * @param model
     * @param mode
     */
    protected void setErrorMessage(final Model model, final String mode) {
        JsonResultDto jsonResultDto = new JsonResultDto();
        jsonResultDto.setStatus(Flags.FAILURE);
        if (StringUtils.isEmpty(mode)) {
            jsonResultDto.setMessage(messageSource.getMessage("common.error.message", null, null));
        } else if (mode.equals(UrlConstants.Mode.ADD)) {
            jsonResultDto.setMessage(messageSource.getMessage("common.save.error.message", null, null));
        } else if (mode.equals(UrlConstants.Mode.UPDATE)) {
            jsonResultDto.setMessage(messageSource.getMessage("common.update.error.message", null, null));
        } else if (mode.equals(UrlConstants.Mode.DELETE)) {
            jsonResultDto.setMessage(messageSource.getMessage("common.delete.error.message", null, null));
        } else {
            jsonResultDto.setMessage(messageSource.getMessage(mode, null, null));
        }
        model.addAttribute("jsonResultDto", jsonResultDto);
    }

    /**
     * エラー時のメッセージを設定する。
     *
     * @param model
     */
    protected void setErrorMessage(final Model model) {
        setErrorMessage(model, null);
    }

    /**
     * 失敗時のメッセージを設定する。
     *
     * @param model
     * @param mode
     */
    protected void setFailureMessage(final Model model, final String mode) {
        JsonResultDto jsonResultDto = new JsonResultDto();
        jsonResultDto.setStatus(Flags.FAILURE);
        if (StringUtils.isEmpty(mode)) {
            jsonResultDto.setMessage(messageSource.getMessage("common.failure.message", null, null));
        } else if (mode.equals(UrlConstants.Mode.ADD)) {
            jsonResultDto.setMessage(messageSource.getMessage("common.save.failure.message", null, null));
        } else if (mode.equals(UrlConstants.Mode.UPDATE)) {
            jsonResultDto.setMessage(messageSource.getMessage("common.update.failure.message", null, null));
        } else if (mode.equals(UrlConstants.Mode.DELETE)) {
            jsonResultDto.setMessage(messageSource.getMessage("common.delete.failure.message", null, null));
        } else {
            jsonResultDto.setMessage(messageSource.getMessage(mode, null, null));
        }
        model.addAttribute("jsonResultDto", jsonResultDto);
    }

    /**
     * 失敗時のメッセージを設定する。
     *
     * @param model
     */
    protected void setFailureMessage(final Model model) {
        setFailureMessage(model, null);
    }
}
