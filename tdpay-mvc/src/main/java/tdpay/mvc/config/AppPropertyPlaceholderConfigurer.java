package tdpay.mvc.config;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import tdpay.mvc.utils.PropertiesUtils;

/**
 * プロパティファイル参照クラス
 */
public class AppPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

    /**
     * プロパティを処理する。
     *
     * @param beanFactory
     * @param properties
     * @note PropertiesUtilクラスから参照できるように、Propertiesクラスのインスタンスを設定する。
     */
    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties properties) throws BeansException {
        super.processProperties(beanFactory, properties);

        PropertiesUtils.setProperty(properties);
    }
}
