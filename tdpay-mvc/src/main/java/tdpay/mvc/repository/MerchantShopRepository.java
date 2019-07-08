package tdpay.mvc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tdpay.mvc.entity.MerchantShop;
 
/**
 * MerchantShopRepository
 */
@Repository
public interface MerchantShopRepository extends BaseRepository<MerchantShop> {

    @Query("select tbl from MerchantShop tbl where tbl.ecShopCode = ?1")
    MerchantShop findByecShopCode(String ecShopCode);

    @Query("select tbl from MerchantShop tbl where tbl.enableFlag = jp.isols.common.constants.Flags.ENABLE")
    List<MerchantShop> findByEnabled();

}
