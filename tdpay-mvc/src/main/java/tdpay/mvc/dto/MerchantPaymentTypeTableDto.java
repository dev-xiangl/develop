package tdpay.mvc.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MerchantPaymentTypeTableDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long paymentTypeId;

    private Long merchantPaymentTypeId;

    private String name;

    private String startDateTime;

    private String endDateTime;
}
