package kr.co.pincar.jpa.entity.newEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.co.pincar.jpa.entity.User;
import kr.co.pincar.jpa.entity.enums.VehicleStatus;
import kr.co.pincar.jpa.entity.enums.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@DynamicInsert
public class OwnProductList extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleType vehicleType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleStatus vehicleStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contractPeriod_id")
    private ContractPeriod contractPeriod;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "deposit_id")
    private Deposit deposit;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "prepayment_id")
    private Prepayment prepayment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "yearMileage_id")
    private YearMileage yearMileage;


}
