package tdpay.mvc.security;

import org.springframework.security.core.GrantedAuthority;

import lombok.ToString;
import tdpay.mvc.common.AppRoleAuthority;

/**
 * ロール権限
 */
@ToString
public class AppGrantedAuthority implements GrantedAuthority {

    private static final long serialVersionUID = -1L;

    /** ロール権限 */
    private AppRoleAuthority appAuthority;


    public AppGrantedAuthority(final String code) {
        this.appAuthority = AppRoleAuthority.valueOf(code);
    }

    @Override
    public String getAuthority() {
        return appAuthority.code();
    }
}
