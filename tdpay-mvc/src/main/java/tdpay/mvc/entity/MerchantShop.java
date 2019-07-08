package tdpay.mvc.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class MerchantShop implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long merchantCompanyId;

    private String ecShopCode;

    private String shopName;

    private String shopPhoneNumber;

    private String shopAddress;

    private String shopManagerName;

    private Long merchantPaymentTypeId;

    private String anyMessage;

    private String saltKey;

    private String runningStatus;

    private Integer enableFlag;

    private String createdBy;

    private LocalDateTime createdDatetime;

    private String updatedBy;

    private LocalDateTime updatedDatetime;

}