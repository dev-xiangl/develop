package tdpay.mvc.utils;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import tdpay.mvc.security.AppSessionManagementFilter;
import tdpay.mvc.security.AppUserDetails;

/**
 * 認証ユーザー情報ユーティリティクラス
 */
public class UserDetailsUtils {

    @SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(UserDetailsUtils.class);

    private static SessionRegistry sessionRegistry;

    private UserDetailsUtils() {
        throw new IllegalAccessError("Constants class.");
    }

    /**
     * SessionRegistryクラスを設定する。
     *
     * @param sessionRegistry SessionRegistryクラスのインスタンス
     * @note {@link AppSessionManagementFilter} から呼び出される。
     */
    public static void setSessionRegistry(SessionRegistry sessionRegistry) {
        UserDetailsUtils.sessionRegistry = sessionRegistry;
    }

    /**
     * ユーザーIDを取得する。
     *
     * @return ユーザーID
     */
    public static Long getUserId() {
        AppUserDetails userDetails = getUserDetails();
        if (userDetails == null) {
            return null;
        }
        return userDetails.getUserId();
    }

    /**
     * ログインIDを取得する。
     *
     * @return ログインID
     */
    public static String getLoginId() {
        AppUserDetails userDetails = getUserDetails();
        if (userDetails == null) {
            return null;
        }
        return userDetails.getLoginId();
    }

    /**
     * 名前を取得する。
     *
     * @return 名前
     */
    public static String getName() {
        AppUserDetails userDetails = getUserDetails();
        if (userDetails == null) {
            return null;
        }
        return userDetails.getName();
    }

    /**
     * ロールIDを取得する。
     *
     * @return ロールID
     */
    public static Long getRoleId() {
        AppUserDetails userDetails = getUserDetails();
        if (userDetails == null) {
            return null;
        }
        return userDetails.getRoleId();
    }

    /**
     * ロール名を取得する。
     *
     * @return ロール名
     */
    public static String getRoleName() {
        AppUserDetails userDetails = getUserDetails();
        if (userDetails == null) {
            return null;
        }
        return userDetails.getRoleName();
    }

    /**
     * MIDを取得する。
     *
     * @return MID
     */
    public static String getMid() {
        AppUserDetails userDetails = getUserDetails();
        if (userDetails == null) {
            return null;
        }
        return userDetails.getMid();
    }

    /**
     * 認証ユーザー情報を取得する。
     *
     * @return AppUserDetails 認証ユーザー情報
     */
    public static AppUserDetails getUserDetails() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal == null) {
            return null;
        }
        if (!(principal instanceof AppUserDetails)) {
            return null;
        }
        return (AppUserDetails)principal;
    }

    /**
     * 認証ユーザー情報を取得する。
     *
     * @param userId 対象ユーザーID
     * @return AppUserDetails 認証ユーザー情報
     */
    public static AppUserDetails getUserDetails(final Long userId) {
        List<AppUserDetails> loggedUserList = getLoggedUserDetailsList();
        if (CollectionUtils.isEmpty(loggedUserList)) {
            return null;
        }

        for (Object principal : loggedUserList) {
            if (!(principal instanceof AppUserDetails)) {
                continue;
            }

            final AppUserDetails loggedUser = (AppUserDetails)principal;
            if (loggedUser != null && loggedUser.getUserId().equals(userId)) {
                return loggedUser;
            }
        }

        return null;
    }

    /**
     * ログイン状態を取得する。
     *
     * @return ログイン状態
     * @retval true ログイン中
     * @retval false 未ログイン
     */
    public static boolean isLoggedIn() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !ObjectUtils.isEmpty(authentication) && authentication.isAuthenticated();
    }

    /**
     * 接続(ログイン)中の認証ユーザー情報リストを取得する。
     *
     * @return 接続(ログイン)中の認証ユーザー情報リスト
     */
    public static List<AppUserDetails> getLoggedUserDetailsList() {
    	List<Object> loggedUserList = sessionRegistry.getAllPrincipals();
        if (CollectionUtils.isEmpty(loggedUserList)) {
            logger.debug("loggedUserList is empty.");
            return null;
        }

        return loggedUserList.stream().map(AppUserDetails.class::cast).collect(Collectors.toList());
    }

    /**
     * 接続(ログイン)中の認証ユーザー情報リストを取得する。
     *
     * @param userIdList 対象ユーザーIDリスト
     * @return 接続(ログイン)中の認証ユーザー情報リスト<br>
     *         正しく取得できない場合、認証ユーザー情報リストにヒットしない場合のみ空リストを返し、それ以外はnullを返す。
     */
    public static List<AppUserDetails> getLoggedUserDetailsList(final List<Long> userIdList) {
        logger.debug("userIdList: {}", userIdList);

        List<AppUserDetails> loggedUserList = getLoggedUserDetailsList();
        if (CollectionUtils.isEmpty(userIdList) || loggedUserList == null) {
            return null;
        }

        return loggedUserList.stream()
                    .filter(x -> userIdList.contains(x.getUserId()))
                    .collect(Collectors.toList());
    }

    /**
     * ユーザーセッションを破棄する。
     *
     * @param userId 対象ユーザーID
     */
    public static void expireUserSessions(final Long userId) {
        final AppUserDetails userDetails = getUserDetails(userId);
        if (userDetails == null) {
            logger.debug("userDetails is null.");
            return;
        }

        List<SessionInformation> sessionInformationList = sessionRegistry.getAllSessions(userDetails, false);
        if (CollectionUtils.isEmpty(sessionInformationList)) {
            logger.debug("sessionInformationList is null.");
            return;
        }

        for (final SessionInformation sessionInformation : sessionInformationList) {
            logger.info("Exprire: UserId=" + userId + " SessionId=" + sessionInformation.getSessionId());
            sessionInformation.expireNow();
            sessionRegistry.removeSessionInformation(sessionInformation.getSessionId());
        }
    }
}
