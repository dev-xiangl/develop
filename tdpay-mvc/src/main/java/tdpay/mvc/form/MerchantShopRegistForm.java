package tdpay.mvc.form;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MerchantShopRegistForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private String merchantCompanyId;

    private String merchantCompanyName;

    private String mid;

    @NotEmpty
    private String ecShopCode;

    @NotEmpty
    private String shopName;

    @NotEmpty
    private String shopPhoneNumber;

    @NotEmpty
    private String shopAddress;

    @NotEmpty
    private String shopManagerName;

    private String merchantPaymentTypeId;

    private String anyMessage;

    @NotEmpty
    @Pattern(regexp = "^[\\w]{4,4}$", message="4桁で入力してください。")
    private String saltKey;

    @NotEmpty
    private String runningStatus;

}
