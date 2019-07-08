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
public class MerchantCompany implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mid;

    private String mallmapId;

    private String ccid;

    private String certificationKey;

    private String companyName;

    private String mainPhoneNumber;

    private String mainAddress;

    private String representativeName;

    private String email;

    private String runningStatus;

    private Integer enableFlag;

    private String createdBy;

    private LocalDateTime createdDatetime;

    private String updatedBy;

    private LocalDateTime updatedDatetime;

}