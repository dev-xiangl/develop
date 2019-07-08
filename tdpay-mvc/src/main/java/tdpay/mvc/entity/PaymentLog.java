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
public class PaymentLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String mid;

    private Long merchantShopId;

    private String terminalId;

    private String slipNo;

    private Long paymentTypeId;

    private Integer enableFlag;

    private String createdBy;

    private LocalDateTime createdDatetime;

    private String updatedBy;

    private LocalDateTime updatedDatetime;
}