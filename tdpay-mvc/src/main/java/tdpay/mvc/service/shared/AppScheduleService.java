package tdpay.mvc.service.shared;

import java.math.BigInteger;
import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import jp.isols.common.utils.DateTimeUtils;

@Component
public class AppScheduleService {

    @SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(AppScheduleService.class);

    @SuppressWarnings("unused")
    @Autowired
    private SystemPropertyService systemPropertyService;

    private BigInteger counter = BigInteger.ZERO;

    @Scheduled(initialDelay = 1000, fixedRate = 1000*60*15)
    public void execute() {
        counter = counter.add(BigInteger.ONE);
        logger.debug("実行回数: " + counter + ", 実行時間: " + DateTimeUtils.toString(LocalDateTime.now()));
    }
}