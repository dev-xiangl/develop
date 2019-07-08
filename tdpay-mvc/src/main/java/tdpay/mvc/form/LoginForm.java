package tdpay.mvc.form;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LoginForm implements Serializable {

    private static final long serialVersionUID = -1L;

    @NotEmpty
    @Email
    private String username;

    @NotEmpty
    private String password;
}
