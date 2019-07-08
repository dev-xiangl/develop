package tdpay.mvc.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MerchantShopSearchDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String soltKey;

    private Long merchantCompanyId;

    private String ecShopCode;

    private String shopName;

    private String shopPhoneNumber;

    private String shopAddress;

    private String shopManagerName;

    private Long merchantPaymentTypeId;

    private String anyMessage;

    private String runningStatus;

}
