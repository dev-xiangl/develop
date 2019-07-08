package tdpay.mvc.common;

/**
 * ロール権限
 */
public enum AppRoleAuthority {

    /** */
    COMPANY_INDEX_VIEW(Code.COMPANY_INDEX_VIEW),

    /** */
    SHOP_INDEX_VIEW(Code.SHOP_INDEX_VIEW),

    /** */
    USER_MAINTENANCE_INDEX_VIEW(Code.USER_MAINTENANCE_INDEX_VIEW),

    /** */
    COMPANY_EDIT_VIEW(Code.COMPANY_EDIT_VIEW),

    /** */
    SHOP_EDIT_VIEW(Code.SHOP_EDIT_VIEW),

    /** */
    USER_MAINTENANCE_EDIT_VIEW(Code.USER_MAINTENANCE_EDIT_VIEW),

    /** */
    COMPANY_DELETE_VIEW(Code.COMPANY_DELETE_VIEW),

    /** */
    SHOP_DELETE_VIEW(Code.SHOP_DELETE_VIEW),

    /** */
    USER_MAINTENANCE_DELETE_VIEW(Code.USER_MAINTENANCE_DELETE_VIEW),

    NONE("");

    /**
     * ロール権限コードクラス
     */
    public class Code {
        public static final String COMPANY_INDEX_VIEW = "COMPANY_INDEX_VIEW";

        public static final String SHOP_INDEX_VIEW = "SHOP_INDEX_VIEW";

        public static final String USER_MAINTENANCE_INDEX_VIEW = "USER_MAINTENANCE_INDEX_VIEW";

        public static final String COMPANY_EDIT_VIEW = "COMPANY_EDIT_VIEW";

        public static final String SHOP_EDIT_VIEW = "SHOP_EDIT_VIEW";

        public static final String USER_MAINTENANCE_EDIT_VIEW = "USER_MAINTENANCE_EDIT_VIEW";

        public static final String COMPANY_DELETE_VIEW = "COMPANY_DELETE_VIEW";

        public static final String SHOP_DELETE_VIEW = "SHOP_DELETE_VIEW";

        public static final String USER_MAINTENANCE_DELETE_VIEW = "USER_MAINTENANCE_DELETE_VIEW";
    }

    /* [A.I.]追加、編集、削除　を追加 */

    private AppRoleAuthority(final String code) {
        this.code = code;
    }

    /** コード */
    private String code;

    public String code() {
        return code;
    }
}
