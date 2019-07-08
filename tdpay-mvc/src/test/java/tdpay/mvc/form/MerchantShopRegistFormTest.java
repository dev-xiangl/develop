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
public class MerchantShopRegistFormTest {

	@Autowired
    private Validator validator;

    private Set<ConstraintViolation<MerchantShopRegistForm>> violations;

    @BeforeEach
    public void beforeEach() {
    }

    @Test
    public void validateAnnotationTest_null() throws Exception {
        MerchantShopRegistForm form = new MerchantShopRegistForm();

        form.setMerchantCompanyId(null);
        form.setMerchantCompanyName(null);
        form.setMid(null);
        form.setEcShopCode(null);
        form.setShopName(null);
        form.setShopPhoneNumber(null);
        form.setShopAddress(null);
        form.setShopManagerName(null);
        form.setMerchantPaymentTypeId(null);
        form.setAnyMessage(null);
        form.setSaltKey(null);
        form.setRunningStatus(null);

        violations = validator.validate(form);
        assertEquals(violations.size(), 7);
        violations.clear();
    }

    @Test
    public void validateAnnotationTest_SoltKey_桁数正常() throws Exception {
        MerchantShopRegistForm form = new MerchantShopRegistForm();

        form.setMerchantCompanyId(null);
        form.setMerchantCompanyName(null);
        form.setMid(null);
        form.setEcShopCode("dummy");
        form.setShopName("dummy");
        form.setShopPhoneNumber("dummy");
        form.setShopAddress("dummy");
        form.setShopManagerName("dummy");
        form.setMerchantPaymentTypeId(null);
        form.setAnyMessage(null);
        form.setSaltKey("1234");
        form.setRunningStatus("dummy");

        violations = validator.validate(form);
        assertEquals(violations.size(), 0);
        violations.clear();
    }

    @Test
    public void validateAnnotationTest_SoltKey_半角空白4桁() throws Exception {
        MerchantShopRegistForm form = new MerchantShopRegistForm();

        form.setMerchantCompanyId(null);
        form.setMerchantCompanyName(null);
        form.setMid(null);
        form.setEcShopCode("dummy");
        form.setShopName("dummy");
        form.setShopPhoneNumber("dummy");
        form.setShopAddress("dummy");
        form.setShopManagerName("dummy");
        form.setMerchantPaymentTypeId(null);
        form.setAnyMessage(null);
        form.setSaltKey("    ");
        form.setRunningStatus("dummy");

        violations = validator.validate(form);
        assertEquals(violations.size(), 1);
        violations.clear();
    }

    @Test
    public void validateAnnotationTest_SoltKey_全角空白2桁() throws Exception {
        MerchantShopRegistForm form = new MerchantShopRegistForm();

        form.setMerchantCompanyId(null);
        form.setMerchantCompanyName(null);
        form.setMid(null);
        form.setEcShopCode("dummy");
        form.setShopName("dummy");
        form.setShopPhoneNumber("dummy");
        form.setShopAddress("dummy");
        form.setShopManagerName("dummy");
        form.setMerchantPaymentTypeId(null);
        form.setAnyMessage(null);
        form.setSaltKey("　　");
        form.setRunningStatus("dummy");

        violations = validator.validate(form);
        assertEquals(violations.size(), 1);
        violations.clear();
    }

    @Test
    public void validateAnnotationTest_SoltKey_全角空白4桁() throws Exception {
        MerchantShopRegistForm form = new MerchantShopRegistForm();

        form.setMerchantCompanyId(null);
        form.setMerchantCompanyName(null);
        form.setMid(null);
        form.setEcShopCode("dummy");
        form.setShopName("dummy");
        form.setShopPhoneNumber("dummy");
        form.setShopAddress("dummy");
        form.setShopManagerName("dummy");
        form.setMerchantPaymentTypeId(null);
        form.setAnyMessage(null);
        form.setSaltKey("　　　　");
        form.setRunningStatus("dummy");

        violations = validator.validate(form);
        assertEquals(violations.size(), 1);
        violations.clear();
    }

    @Test
    public void validateAnnotationTest_SoltKey_桁数異常1() throws Exception {
        MerchantShopRegistForm form = new MerchantShopRegistForm();

        form.setMerchantCompanyId(null);
        form.setMerchantCompanyName(null);
        form.setMid(null);
        form.setEcShopCode("dummy");
        form.setShopName("dummy");
        form.setShopPhoneNumber("dummy");
        form.setShopAddress("dummy");
        form.setShopManagerName("dummy");
        form.setMerchantPaymentTypeId(null);
        form.setAnyMessage(null);
        form.setSaltKey("1");
        form.setRunningStatus("dummy");

        violations = validator.validate(form);
        assertEquals(violations.size(), 1);
        violations.clear();
    }

    @Test
    public void validateAnnotationTest_SoltKey_桁数異常5() throws Exception {
        MerchantShopRegistForm form = new MerchantShopRegistForm();

        form.setMerchantCompanyId(null);
        form.setMerchantCompanyName(null);
        form.setMid(null);
        form.setEcShopCode("dummy");
        form.setShopName("dummy");
        form.setShopPhoneNumber("dummy");
        form.setShopAddress("dummy");
        form.setShopManagerName("dummy");
        form.setMerchantPaymentTypeId(null);
        form.setAnyMessage(null);
        form.setSaltKey("12345");
        form.setRunningStatus("dummy");

        violations = validator.validate(form);
        assertEquals(violations.size(), 1);
        violations.clear();
    }
}
