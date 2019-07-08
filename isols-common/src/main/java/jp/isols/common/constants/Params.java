package jp.isols.common.constants;

public class Params {

    private Params() {
        throw new IllegalAccessError("Constants class.");
    }

    /** mvc: host */
    public static final String SYSTEM_MVC_HOST = "localhost";

    /** mvc: port */
    public static final Integer SYSTEM_MVC_PORT = 8080;
}
