package tdpay.mvc.form;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.ToString;
import tdpay.mvc.entity.MerchantPaymentType;

@Data
@ToString
public class MerchantShopSearchForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private String mid;

    private String ecShopCode;

    private String shopName;

    private List<MerchantPaymentType> merchantPaymentTypeList;

    private String merchantPaymentTypeId;

}
