package tdpay.mvc.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * サーブレットリクエストユーティリティクラス
 */
public class ServletRequestUtils extends org.springframework.web.bind.ServletRequestUtils {

    private ServletRequestUtils() {
        throw new IllegalAccessError("Constants class.");
    }

    /**
     * ServletRequestAttributesを取得する。
     *
     * @return ServletRequestAttributesクラスのインスタンス
     */
    private static ServletRequestAttributes getServletRequestAttributes() {
        return (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
    }

    /**
     * HttpServletRequestを取得する。
     *
     * @return HttpServletRequestクラスのインスタンス
     */
    public static HttpServletRequest getHttpServletRequest() {
        return getServletRequestAttributes().getRequest();
    }

    /**
     * HttpSessionを取得する。
     *
     * @return HttpSessionクラスのインスタンス
     */
    public static HttpSession getHttpSession() {
        return getHttpServletRequest().getSession();
    }
}
