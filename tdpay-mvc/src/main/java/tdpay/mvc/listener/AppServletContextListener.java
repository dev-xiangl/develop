package tdpay.mvc.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tdpay.mvc.utils.PropertiesUtils;

/**
 * サーブレットコンテキストリスナー
 */
@WebListener
public class AppServletContextListener implements ServletContextListener {

    @SuppressWarnings("unused")
    private static final Logger logger = LogManager.getLogger(AppServletContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent event) {
        logger.info("########## contextInitialized. ##########");
        final WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());

        initializeDatabaseData(ctx);

        logger.debug("profile = {} ", PropertiesUtils.getProperty("spring.profiles.active"));
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        logger.info("########## contextDestroyed. ##########");
    }

    /**
     * データベースのデータを初期化する。
     * 
     * @param ctx WebApplicationContextクラスのインスタンス
     */
    private void initializeDatabaseData(final WebApplicationContext ctx) {
    }
}
