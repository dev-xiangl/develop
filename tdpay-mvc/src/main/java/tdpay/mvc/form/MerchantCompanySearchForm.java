package tdpay.mvc.form;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MerchantCompanySearchForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private String mid;

    private String mallmapId;

    private String companyName;

    private String email;

}
