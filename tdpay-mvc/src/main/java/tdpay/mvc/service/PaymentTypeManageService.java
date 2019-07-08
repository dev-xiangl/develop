package tdpay.mvc.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tdpay.mvc.entity.PaymentType;
import tdpay.mvc.repository.PaymentTypeRepository;

/**
 *
 */
@Service
public class PaymentTypeManageService {

    @SuppressWarnings("unused")
    private static final Logger logger = LogManager.getLogger(PaymentTypeManageService.class);

    @Autowired
    protected PaymentTypeRepository paymentTypeRepository;

    @Transactional
    public PaymentType getPaymentType(final Long paymentTypeId) {
        return paymentTypeRepository.findById(paymentTypeId).orElse(null);
    }

    public List<PaymentType> getPaymentTypeList() {
        return paymentTypeRepository.findByEnabled();
    }

}
