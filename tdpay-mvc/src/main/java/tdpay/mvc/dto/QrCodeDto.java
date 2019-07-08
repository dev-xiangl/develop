package tdpay.mvc.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class QrCodeDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String mid;

    private String amt;

    private String tranid;
}
