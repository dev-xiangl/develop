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
public class MerchantCompanySearchFormTest {

	@Autowired
    private Validator validator;

    private Set<ConstraintViolation<MerchantCompanySearchForm>> violations;

    @BeforeEach
    public void beforeEach() {
    }

    @Test
    public void validateAnnotationTest_null() throws Exception {
        MerchantCompanySearchForm form = new MerchantCompanySearchForm();

        form.setMid(null);
        form.setMallmapId(null);
        form.setCompanyName(null);
        form.setEmail(null);

        violations = validator.validate(form);
        assertEquals(violations.size(), 0);
        violations.clear();
    }

    @Test
    public void validateAnnotationTest_正常() throws Exception {
        MerchantCompanySearchForm form = new MerchantCompanySearchForm();

        form.setMid("1234567890123456789012");
        form.setMallmapId("dummy");
        form.setCompanyName("dummy");
        form.setEmail("dummy");

        violations = validator.validate(form);
        assertEquals(violations.size(), 0);
        violations.clear();
    }

}
