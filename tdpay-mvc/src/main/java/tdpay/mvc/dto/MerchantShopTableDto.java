package tdpay.mvc.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MerchantShopTableDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long merchantCompanyId;

    private Long merchantShopId;

    private String mid;

    private String ecShopCode;

    private String shopName;

    private String shopPhoneNumber;

    private String runningStatus;

    private Long merchantPaymentTypeId;

}
