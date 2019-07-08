package tdpay.mvc.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * セッション管理リスナー
 */
public class AppSessionListener implements HttpSessionListener {

    @SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(AppSessionListener.class);

    @Override
    public void sessionCreated(HttpSessionEvent sessionEvent) {
        HttpSession session = sessionEvent.getSession();
        logger.info("### sessionCreated: " + session.getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent sessionEvent) {
        HttpSession session = sessionEvent.getSession();
        if (session == null) {
            logger.info("session is null.");
            return;
        }

        logger.info("### sessionDestroyed: " + session.getId());
    }
}
