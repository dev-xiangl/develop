package tdpay.mvc.dto;

import java.io.Serializable;
import java.math.BigInteger;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RoleManageDto implements Serializable {

    private static final long serialVersionUID = -1L;

    private BigInteger roleId;

    private String roleName;
}
