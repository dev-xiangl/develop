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
public class MerchantCompanyRegistFormTest {

	@Autowired
    private Validator validator;

    private Set<ConstraintViolation<MerchantCompanyRegistForm>> violations;

    @BeforeEach
    public void beforeEach() {
    }

    @Test
    public void validateAnnotationTest_null() throws Exception {
        MerchantCompanyRegistForm form = new MerchantCompanyRegistForm();

        form.setMid(null);
        form.setMallmapId(null);
        form.setCcid(null);
        form.setCertificationKey(null);
        form.setCompanyName(null);
        form.setMainPhoneNumber(null);
        form.setMainAddress(null);
        form.setRepresentativeName(null);
        form.setEmail(null);
        form.setRunningStatus(null);

        violations = validator.validate(form);
        assertEquals(violations.size(), 10);
        for (final ConstraintViolation<MerchantCompanyRegistForm> violation : violations) {
            assertEquals("{javax.validation.constraints.NotEmpty.message}", violation.getMessageTemplate());
        }
        violations.clear();
    }

    @Test
    public void validateAnnotationTest_Mid_CertificationKey_桁数正常() throws Exception {
        MerchantCompanyRegistForm form = new MerchantCompanyRegistForm();

        form.setMid("1234567890123456789012");
        form.setMallmapId("dummy");
        form.setCcid("dummy");
        form.setCertificationKey("1234");
        form.setCompanyName("dummy");
        form.setMainPhoneNumber("dummy");
        form.setMainAddress("dummy");
        form.setRepresentativeName("dummy");
        form.setEmail("dummy");
        form.setRunningStatus("dummy");

        violations = validator.validate(form);
        assertEquals(violations.size(), 0);
        violations.clear();
    }

    @Test
    public void validateAnnotationTest_Mid_半角空白22桁() throws Exception {
        MerchantCompanyRegistForm form = new MerchantCompanyRegistForm();

        form.setMid("                      ");
        form.setMallmapId("dummy");
        form.setCcid("dummy");
        form.setCertificationKey("1234");
        form.setCompanyName("dummy");
        form.setMainPhoneNumber("dummy");
        form.setMainAddress("dummy");
        form.setRepresentativeName("dummy");
        form.setEmail("dummy");
        form.setRunningStatus("dummy");

        violations = validator.validate(form);
        assertEquals(violations.size(), 1);
        violations.clear();
    }

    @Test
    public void validateAnnotationTest_Mid_全角空白11桁() throws Exception {
        MerchantCompanyRegistForm form = new MerchantCompanyRegistForm();

        form.setMid("　　　　　　　　　　　");
        form.setMallmapId("dummy");
        form.setCcid("dummy");
        form.setCertificationKey("1234");
        form.setCompanyName("dummy");
        form.setMainPhoneNumber("dummy");
        form.setMainAddress("dummy");
        form.setRepresentativeName("dummy");
        form.setEmail("dummy");
        form.setRunningStatus("dummy");

        violations = validator.validate(form);
        assertEquals(violations.size(), 1);
        violations.clear();
    }

    @Test
    public void validateAnnotationTest_Mid_桁数異常1() throws Exception {
        MerchantCompanyRegistForm form = new MerchantCompanyRegistForm();

        form.setMid("1");
        form.setMallmapId("dummy");
        form.setCcid("dummy");
        form.setCertificationKey("1234");
        form.setCompanyName("dummy");
        form.setMainPhoneNumber("dummy");
        form.setMainAddress("dummy");
        form.setRepresentativeName("dummy");
        form.setEmail("dummy");
        form.setRunningStatus("dummy");

        violations = validator.validate(form);
        assertEquals(violations.size(), 1);
        violations.clear();
    }

    @Test
    public void validateAnnotationTest_Mid_桁数異常23() throws Exception {
        MerchantCompanyRegistForm form = new MerchantCompanyRegistForm();

        form.setMid("12345678901234567890123");
        form.setMallmapId("dummy");
        form.setCcid("dummy");
        form.setCertificationKey("1234");
        form.setCompanyName("dummy");
        form.setMainPhoneNumber("dummy");
        form.setMainAddress("dummy");
        form.setRepresentativeName("dummy");
        form.setEmail("dummy");
        form.setRunningStatus("dummy");

        violations = validator.validate(form);
        assertEquals(violations.size(), 1);
        violations.clear();
    }

    @Test
    public void validateAnnotationTest_CertificationKey_半角空白4桁() throws Exception {
        MerchantCompanyRegistForm form = new MerchantCompanyRegistForm();

        form.setMid("1234567890123456789012");
        form.setMallmapId("dummy");
        form.setCcid("dummy");
        form.setCertificationKey("    ");
        form.setCompanyName("dummy");
        form.setMainPhoneNumber("dummy");
        form.setMainAddress("dummy");
        form.setRepresentativeName("dummy");
        form.setEmail("dummy");
        form.setRunningStatus("dummy");

        violations = validator.validate(form);
        assertEquals(violations.size(), 1);
        violations.clear();
    }

    @Test
    public void validateAnnotationTest_CertificationKey_全角空白2桁() throws Exception {
        MerchantCompanyRegistForm form = new MerchantCompanyRegistForm();

        form.setMid("1234567890123456789012");
        form.setMallmapId("dummy");
        form.setCcid("dummy");
        form.setCertificationKey("　　");
        form.setCompanyName("dummy");
        form.setMainPhoneNumber("dummy");
        form.setMainAddress("dummy");
        form.setRepresentativeName("dummy");
        form.setEmail("dummy");
        form.setRunningStatus("dummy");

        violations = validator.validate(form);
        assertEquals(violations.size(), 1);
        violations.clear();
    }

    @Test
    public void validateAnnotationTest_CertificationKey_全角空白4桁() throws Exception {
        MerchantCompanyRegistForm form = new MerchantCompanyRegistForm();

        form.setMid("1234567890123456789012");
        form.setMallmapId("dummy");
        form.setCcid("dummy");
        form.setCertificationKey("　　　　");
        form.setCompanyName("dummy");
        form.setMainPhoneNumber("dummy");
        form.setMainAddress("dummy");
        form.setRepresentativeName("dummy");
        form.setEmail("dummy");
        form.setRunningStatus("dummy");

        violations = validator.validate(form);
        assertEquals(violations.size(), 1);
        violations.clear();
    }

    @Test
    public void validateAnnotationTest_CertificationKey_桁数異常1桁() throws Exception {
        MerchantCompanyRegistForm form = new MerchantCompanyRegistForm();

        form.setMid("1234567890123456789012");
        form.setMallmapId("dummy");
        form.setCcid("dummy");
        form.setCertificationKey("1");
        form.setCompanyName("dummy");
        form.setMainPhoneNumber("dummy");
        form.setMainAddress("dummy");
        form.setRepresentativeName("dummy");
        form.setEmail("dummy");
        form.setRunningStatus("dummy");

        violations = validator.validate(form);
        assertEquals(violations.size(), 1);
        violations.clear();
    }

    @Test
    public void validateAnnotationTest_CertificationKey_桁数異常5桁() throws Exception {
        MerchantCompanyRegistForm form = new MerchantCompanyRegistForm();

        form.setMid("1234567890123456789012");
        form.setMallmapId("dummy");
        form.setCcid("dummy");
        form.setCertificationKey("12345");
        form.setCompanyName("dummy");
        form.setMainPhoneNumber("dummy");
        form.setMainAddress("dummy");
        form.setRepresentativeName("dummy");
        form.setEmail("dummy");
        form.setRunningStatus("dummy");

        violations = validator.validate(form);
        assertEquals(violations.size(), 1);
        violations.clear();
    }

}
