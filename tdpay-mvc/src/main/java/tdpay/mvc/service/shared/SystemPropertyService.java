package tdpay.mvc.service.shared;

import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.isols.common.utils.StringUtils;
import tdpay.mvc.common.SystemPropertyCode;
import tdpay.mvc.entity.SystemProperty;
import tdpay.mvc.repository.SystemPropertyRepository;

/**
 * システムプロパティサービス
 */
@Component
public class SystemPropertyService {

    @SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(SystemPropertyService.class);

    @Autowired
    protected SystemPropertyRepository systemPropertyRepository;

    public String getSixHash(final String hash) {
        return hash.substring(hash.length() - 6);
    }

    /**
     * システムプロパティの加盟店稼働状態を取得する。
     *
     * @return システムプロパティの値
     */
    public String getCompannyRunning() {
        return getValueAsString(SystemPropertyCode.COMPANY_RUNNING);
    }

    /**
     * システムプロパティの加盟店稼働状態を取得する。
     *
     * @return システムプロパティの値
     */
    public String getCompannyStopOperation() {
        return getValueAsString(SystemPropertyCode.COMPANY_STOP_OPERATION);
    }

    /**
     * システムプロパティの店舗稼働中状態を取得する。
     *
     * @return システムプロパティの値
     */
    public int getShopRunning() {
        return getValueAsInt(SystemPropertyCode.SHOP_RUNNING);
    }

    /**
     * システムプロパティの店舗稼働停止状態を取得する。
     *
     * @return システムプロパティの値
     */
    public int getShopStopOperation() {
        return getValueAsInt(SystemPropertyCode.SHOP_STOP_OPERATION);
    }

    /**
     * システムプロパティのQRコード保持時間を取得する。
     *
     * @return システムプロパティの値
     */
    public long getQrCodeTimer() {
        return getValueAsLong(SystemPropertyCode.QR_CODE_TIMER);
    }


    /**
     * システムプロパティの値をStringで取得する。
     *
     * @param code システムプロパティのコード
     * @param defaultValue システムプロパティの値が不正な場合の初期値(通常は使用しない)
     * @return システムプロパティの値
     */
    private String getValueAsString(final String code, final String defaultValue) {
        SystemProperty systemProperty = systemPropertyRepository.findByCode(code);
        if (systemProperty == null || StringUtils.isBlank(systemProperty.getValue())) {
            logger.error("SystemProperty {} is not found. Return default {}.", code, defaultValue);
            return defaultValue;
        }

        return systemProperty.getValue();
    }

    /**
     * システムプロパティの値をStringで取得する。
     *
     * @param code システムプロパティのコード
     * @return システムプロパティの値
     */
    private String getValueAsString(final String code) {
        SystemProperty systemProperty = systemPropertyRepository.findByCode(code);
        if (systemProperty == null || StringUtils.isBlank(systemProperty.getValue())) {
            logger.error("SystemProperty {} is not found.", code);
            return null;
        }

        return systemProperty.getValue();
    }

    /**
     * システムプロパティの値をintで取得する。
     *
     * @param code システムプロパティのコード
     * @param defaultValue システムプロパティの値が不正な場合の初期値(通常は使用しない)
     * @return システムプロパティの値
     */
    private int getValueAsInt(final String code, final int defaultValue) {
        final String value = getValueAsString(code, String.valueOf(defaultValue));
        return Integer.parseInt(value);
    }

    /**
     * システムプロパティの値をintで取得する。
     *
     * @param code システムプロパティのコード
     * @return システムプロパティの値
     */
    private int getValueAsInt(final String code) {
        final String value = getValueAsString(code);
        return Integer.parseInt(value);
    }

    /**
     * システムプロパティの値をlongで取得する。
     *
     * @param code システムプロパティのコード
     * @param defaultValue システムプロパティの値が不正な場合の初期値(通常は使用しない)
     * @return システムプロパティの値
     */
    private long getValueAsLong(final String code, final long defaultValue) {
        final String value = getValueAsString(code, String.valueOf(defaultValue));
        return Long.parseLong(value);
    }

    /**
     * システムプロパティの値をlongで取得する。
     *
     * @param code システムプロパティのコード
     * @return システムプロパティの値
     */
    private long getValueAsLong(final String code) {
        final String value = getValueAsString(code);
        return Long.parseLong(value);
    }

    /**
     * システムプロパティの値をBigDecimalで取得する。
     *
     * @param code システムプロパティのコード
     * @param defaultValue システムプロパティの値が不正な場合の初期値(通常は使用しない)
     * @return システムプロパティの値
     */
    private BigDecimal getValueAsBigDecimal(final String code, final int defaultValue) {
        final String value = getValueAsString(code, String.valueOf(defaultValue));
        return new BigDecimal(value);
    }

    /**
     * システムプロパティの値をBigDecimalで取得する。
     *
     * @param code システムプロパティのコード
     * @return システムプロパティの値
     */
    private BigDecimal getValueAsBigDecimal(final String code) {
        final String value = getValueAsString(code);
        return new BigDecimal(value);
    }

}
