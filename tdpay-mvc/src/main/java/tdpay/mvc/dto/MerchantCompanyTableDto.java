package tdpay.mvc.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MerchantCompanyTableDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long companyId;

    private String mid;

    private String mallmapId;

    private String ccid;

    private String certificationKey;

    private String companyName;

    private String mainPhoneNumber;

    private String mainAddress;

    private String representativeName;

    private String email;

    private String runningStatus;

}
