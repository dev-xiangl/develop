package tdpay.mvc.form;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserMaintenanceRegistForm implements Serializable {

    private static final long serialVersionUID = -1L;

    private String userId;

    @NotEmpty
    private String mid;

    @NotEmpty
    private String userName;

    @NotEmpty
    private String loginId;

    private String password;

    private String prePassword;

    @NotEmpty
    private String roleId;
}
