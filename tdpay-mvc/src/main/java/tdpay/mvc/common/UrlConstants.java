package tdpay.mvc.common;

/**
 * URL 定数クラス
 *
 * @note コントローラークラスで指定する画面遷移先を規定する。
 */
public class UrlConstants {

    private UrlConstants() {
        throw new IllegalAccessError("Constants class.");
    }

    public static final String ROOT = "/";

    public static final String INDEX = "/index";

    public static final String EXECUTE = "/execute";

    public static final String DELETE = "/delete";

    public static final String REGIST = "/regist";

    public static final String ADD = "/add";

    public static final String EDIT = "/edit";

    public static final String SEARCH = "/search";

    public static final String IMPORT = "/import";

    public static final String CONFIRM = "/confirm";

    public static final String RESULT = "/result";

    public static final String ERROR = "/error";


    public static final String PAYMENT = "/payment";

    public static final String PAYMENT_ERROR = PAYMENT + ERROR;

    public static final String PAYMENT_RESULT = PAYMENT + RESULT;

    public static final String SEND = "/send";

    public static final String COMPANY = "/company";

    public static final String COMPANY_ADD = COMPANY + ADD;

    public static final String COMPANY_EDIT = COMPANY + EDIT;

    public static final String COMPANY_DELETE = COMPANY + DELETE;

    public static final String COMPANY_SEARCH = COMPANY + SEARCH;

    public static final String COMPANY_REGIST = COMPANY + REGIST;
    
    public static final String COMPANY_IMPORT = COMPANY + IMPORT;

    public static final String COMPANY_CONFIRM = COMPANY + CONFIRM;

    public static final String SHOP = "/shop";

    public static final String SHOP_ADD = SHOP + ADD;

    public static final String SHOP_EDIT = SHOP + EDIT;

    public static final String SHOP_DELETE = SHOP + DELETE;

    public static final String SHOP_SEARCH = SHOP + SEARCH;

    public static final String SHOP_REGIST = SHOP + REGIST;

    public static final String SHOP_CONFIRM = SHOP + CONFIRM;

    public static final String USER_MAINTENANCE = "/userMaintenance";

    public static final String USER_MAINTENANCE_ADD = USER_MAINTENANCE + ADD;

    public static final String USER_MAINTENANCE_EDIT = USER_MAINTENANCE + EDIT;

    public static final String USER_MAINTENANCE_DELETE = USER_MAINTENANCE + DELETE;

    public static final String USER_MAINTENANCE_SEARCH = USER_MAINTENANCE + SEARCH;

    public static final String USER_MAINTENANCE_REGIST = USER_MAINTENANCE + REGIST;

    public static final String USER_MAINTENANCE_CONFIRM = USER_MAINTENANCE + CONFIRM;


    public static final String LOGIN = "/login";

    public static final String LOGOUT = "/logout";

    /**
     * 画面操作モード
     *
     * @note URLと異なり、各画面の操作モードを示す。
     */
    public static class Mode {
        private Mode() {
            throw new IllegalAccessError("Constants class.");
        }

        public static final String param = "mode";

        /** 参照 */
        public static final String VIEW = "view";

        /** 検索 */
        public static final String SEARCH = "search";

        /** 追加(新規) */
        public static final String ADD = "add";

        /** 追加(新規) */
        public static final String INSERT = "insert";

        /** 変更 */
        public static final String UPDATE = "update";

        /** 変更 */
        public static final String EDIT = "edit";

        /** 削除 */
        public static final String DELETE = "delete";

        /** コピー */
        public static final String COPY = "copy";
    }
}
