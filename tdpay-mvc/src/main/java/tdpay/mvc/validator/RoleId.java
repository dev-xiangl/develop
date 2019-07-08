package tdpay.mvc.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {RoleIdValidator.class})
@ReportAsSingleViolation
public @interface RoleId {

    String message() default "{validation.value.Invalid.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public static @interface List {
        RoleId[] value();
    }

}
