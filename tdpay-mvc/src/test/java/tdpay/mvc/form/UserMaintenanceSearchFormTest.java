package tdpay.mvc.form;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import tdpay.mvc.config.AppConfig;

@SpringJUnitWebConfig(classes = { AppConfig.class })
public class UserMaintenanceSearchFormTest {

	@Autowired
    private Validator validator;

    private Set<ConstraintViolation<UserMaintenanceSearchForm>> violations;

    @BeforeEach
    public void beforeEach() {
    }

    @Test
    public void validateAnnotationTest_null() throws Exception {
        UserMaintenanceSearchForm form = new UserMaintenanceSearchForm();

        form.setMid(null);
        form.setLoginId(null);
        form.setUserName(null);

        violations = validator.validate(form);
        assertEquals(violations.size(), 0);
        for (final ConstraintViolation<UserMaintenanceSearchForm> violation : violations) {
            assertEquals("{javax.validation.constraints.NotEmpty.message}", violation.getMessageTemplate());
        }
        violations.clear();
    }
}
