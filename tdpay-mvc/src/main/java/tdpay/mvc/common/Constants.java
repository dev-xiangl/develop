package tdpay.mvc.common;

/**
 * 定数クラス
 */
public class Constants {

    private Constants() {
        throw new IllegalAccessError("Constants class.");
    }

    public static final String DUMMY = "dummy";

    public static class Profiles {
        private Profiles() {
            throw new IllegalAccessError("Constants class.");
        }

        public static final String LOCAL = "local";

        public static final String DEV = "dev";

        public static final String SAMPLE = "sample";

        public static final String PRODUCT = "product";
    }
}
