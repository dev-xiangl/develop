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
public class LoginFormTest {

	@Autowired
    private Validator validator;

    private Set<ConstraintViolation<LoginForm>> violations;

    @BeforeEach
    public void beforeEach() {
    }

    @Test
    public void validateAnnotationTest_通常() throws Exception {
        LoginForm form = new LoginForm();

        form.setUsername("test@test.com");
        form.setPassword("test");

        violations = validator.validate(form);
        assertEquals(violations.size(), 0);
        violations.clear();
    }

    @Test
    public void validateAnnotationTest_null() throws Exception {
        LoginForm form = new LoginForm();

        form.setUsername(null);
        form.setPassword(null);

        violations = validator.validate(form);
        assertEquals(violations.size(), 2);
        for (final ConstraintViolation<LoginForm> violation : violations) {
            assertEquals("{javax.validation.constraints.NotEmpty.message}", violation.getMessageTemplate());
        }
        violations.clear();
    }

    @Test
    public void validateAnnotationTest_空文字() throws Exception {
        LoginForm form = new LoginForm();

        form.setUsername("");
        form.setPassword("");

        violations = validator.validate(form);
        assertEquals(violations.size(), 2);
        for (final ConstraintViolation<LoginForm> violation : violations) {
            assertEquals("{javax.validation.constraints.NotEmpty.message}", violation.getMessageTemplate());
        }
        violations.clear();
    }

    @Test
    public void validateAnnotationTest_ログインIDのみ空文字() throws Exception {
        LoginForm form = new LoginForm();

        form.setUsername("");
        form.setPassword("test");

        violations = validator.validate(form);
        assertEquals(violations.size(), 1);
        for (final ConstraintViolation<LoginForm> violation : violations) {
            assertEquals("{javax.validation.constraints.NotEmpty.message}", violation.getMessageTemplate());
        }
        violations.clear();
    }

    @Test
    public void validateAnnotationTest_パスワードのみ空文字() throws Exception {
        LoginForm form = new LoginForm();

        form.setUsername("test@test.com");
        form.setPassword("");

        violations = validator.validate(form);
        assertEquals(violations.size(), 1);
        for (final ConstraintViolation<LoginForm> violation : violations) {
            assertEquals("{javax.validation.constraints.NotEmpty.message}", violation.getMessageTemplate());
        }
        violations.clear();
    }

    @Test
    public void validateAnnotationTest_ログインIDの形式不正() throws Exception {
        LoginForm form = new LoginForm();

        form.setUsername("test");
        form.setPassword("test");

        violations = validator.validate(form);
        assertEquals(violations.size(), 1);
        for (final ConstraintViolation<LoginForm> violation : violations) {
            assertEquals("{javax.validation.constraints.Email.message}", violation.getMessageTemplate());
        }
        violations.clear();
    }
}
