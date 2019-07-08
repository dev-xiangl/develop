package tdpay.mvc.validator;

import java.math.BigInteger;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import jp.isols.common.utils.StringUtils;

public class NaturalNumberValidator implements ConstraintValidator<NaturalNumber, String> {

    @Override
    public void initialize(NaturalNumber annotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isBlank(value)) {
            return true;
        }

        BigInteger naturalNumber = null;
        try {
            naturalNumber = new BigInteger(value);
        } catch(Exception e) {
            return false;
        }
		return BigInteger.ONE.compareTo(naturalNumber) != 1 ? true : false;
    }
}
