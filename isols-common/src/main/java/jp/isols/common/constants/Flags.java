package jp.isols.common.constants;

public class Flags {

    private Flags() {
        throw new IllegalAccessError("Constants class.");
    }

    /** 有効/無効フラグ: 有効 */
    public static final Integer ENABLE = 1;
    /** 有効/無効フラグ: 無効 */
    public static final Integer DISABLE = 0;

    /** 成功 */
    public static final Integer SUCCESS = 1;
    /** 失敗 */
    public static final Integer FAILURE = -1;
}
