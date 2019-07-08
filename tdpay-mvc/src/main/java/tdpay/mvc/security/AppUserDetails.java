package tdpay.mvc.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import lombok.Data;

@Data
public class AppUserDetails implements UserDetails {

    private static final long serialVersionUID = -1L;

    /** ユーザー名 */
    private String username;
    /** パスワード */
    private String password;
    /** 有効/無効 */
    private boolean enabled;

    /** ユーザーID */
    private Long userId;

    /** ログインID */
    private String loginId;

    /** 名前 */
    private String name;

    /** ロールID */
    private Long roleId;

    /** ロール名 */
    private String roleName;

    /** ロール権限 */
    private List<AppGrantedAuthority> authorityList = new ArrayList<>();

    /** MID */
    private String mid;

    @Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorityList;
	}

    /**
     * ロール権限を設定する
     */
    public void setAuthorities(final List<AppGrantedAuthority> authorityList) {
        this.authorityList = authorityList;
    }

    /**
     * ロール権限を設定する
     */
    public void setAuthoritiesByCode(final List<String> authorityCodeList) {
        setAuthorities(authorityCodeList.stream().map(x -> new AppGrantedAuthority(x)).collect(Collectors.toList()));
    }

    /**
     * ロール権限を追加する
     */
    public void addAuthority(final AppGrantedAuthority authority) {
        if (CollectionUtils.isEmpty(authorityList)) {
            this.authorityList = new ArrayList<>();
        }
        this.authorityList.add(authority);
    }

    /**
     * ロール権限を追加する
     */
    public void addAuthority(final List<String> authorityCodeList) {
        if (CollectionUtils.isEmpty(authorityCodeList)) {
            return;
        }
        if (CollectionUtils.isEmpty(authorityList)) {
            this.authorityList = new ArrayList<>();
        }
        authorityCodeList.stream().forEach(x -> this.authorityList.add(new AppGrantedAuthority(x)));
    }

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
}
