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
public class UserMaintenanceRegistFormTest {

	@Autowired
    private Validator validator;

    private Set<ConstraintViolation<UserMaintenanceRegistForm>> violations;

    @BeforeEach
    public void beforeEach() {
    }

    @Test
    public void validateAnnotationTest_null() throws Exception {
        UserMaintenanceRegistForm form = new UserMaintenanceRegistForm();

        form.setMid(null);
        form.setLoginId(null);
        form.setPassword(null);
        form.setPrePassword(null);
        form.setRoleId(null);
        form.setUserId(null);
        form.setUserName(null);

        violations = validator.validate(form);
        assertEquals(violations.size(), 4);
        for (final ConstraintViolation<UserMaintenanceRegistForm> violation : violations) {
            assertEquals("{javax.validation.constraints.NotEmpty.message}", violation.getMessageTemplate());
        }
        violations.clear();
    }
}
