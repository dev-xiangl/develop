package tdpay.mvc.common;

/**
 * WebSocket 定数クラス
 */
public class WebSocketConstants {

    private WebSocketConstants() {
        throw new IllegalAccessError("Constants class.");
    }

    public static final String ENDPOINT = "/ws";

    public static final String DESTINATION_PREFIX = "/app";

    public static final String SIMPLE_BLOKER = "/simple_bloker";

    public static class Message {

        public static final String GET_STATUS = "/getStatus";

        public static final String SET_STATUS = "/setStatus";
    }

    public static class NativeHeader {

        public static final String USERNAME_HEADER = "username";

        public static final String PASSWORD_HEADER = "password";

        public static final String DESTINATION = "destination";
    }
}
