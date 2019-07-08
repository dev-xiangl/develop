package tdpay.mvc.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import jp.isols.common.utils.StringUtils;
import tdpay.mvc.service.UserManageService;

public class LoginIdValidator implements ConstraintValidator<LoginId, String> {

    @Autowired
    private UserManageService userInfoService;

    @Override
    public void initialize(LoginId annotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isBlank(value)) {
            return true;
        }

		return userInfoService.getUserByLoginId(value) == null ? true : false;
    }
}
