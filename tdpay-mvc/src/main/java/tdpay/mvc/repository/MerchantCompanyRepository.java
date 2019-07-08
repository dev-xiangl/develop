package tdpay.mvc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tdpay.mvc.entity.MerchantCompany;

/**
 * MerchantCompanyRepository
 */
@Repository
public interface MerchantCompanyRepository extends BaseRepository<MerchantCompany> {

    @Query("select tbl from MerchantCompany tbl where tbl.mid = ?1")
    MerchantCompany findByMid(String mid);

    @Query("select tbl from MerchantCompany tbl where tbl.enableFlag = jp.isols.common.constants.Flags.ENABLE")
    List<MerchantCompany> findByEnabled();

}
