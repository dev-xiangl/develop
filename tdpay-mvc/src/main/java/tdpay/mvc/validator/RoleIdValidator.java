package tdpay.mvc.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import tdpay.mvc.service.UserManageService;

public class RoleIdValidator implements ConstraintValidator<RoleId, Long> {

    @Autowired
    private UserManageService userManageService;

    @Override
    public void initialize(RoleId annotation) {
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if (ObjectUtils.isEmpty(value)) {
            return true;
        }

        return userManageService.getRole(value) != null ? true : false;
    }
}
