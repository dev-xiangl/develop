package tdpay.mvc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tdpay.mvc.entity.PaymentType;
 
/**
 * PaymentTypeRepository
 */
@Repository
public interface PaymentTypeRepository extends BaseRepository<PaymentType> {

    @Query("select tbl from PaymentType tbl where tbl.enableFlag = jp.isols.common.constants.Flags.ENABLE")
    List<PaymentType> findByEnabled();

}
