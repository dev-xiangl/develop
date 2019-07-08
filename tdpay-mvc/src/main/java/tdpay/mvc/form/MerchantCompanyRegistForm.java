package tdpay.mvc.form;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MerchantCompanyRegistForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty
    @Pattern(regexp = "^[\\w]{22,22}$", message="22桁で入力してください。")
    private String mid;

    @NotEmpty
    private String mallmapId;

    @NotEmpty
    private String ccid;

    @NotEmpty
    @Pattern(regexp = "^[\\w]{4,4}$", message="4桁で入力してください。")
    private String certificationKey;

    @NotEmpty
    private String companyName;

    @NotEmpty
    private String mainPhoneNumber;

    @NotEmpty
    private String mainAddress;

    @NotEmpty
    private String representativeName;

    @NotEmpty
    private String email;

    @NotEmpty
    private String runningStatus;

}
