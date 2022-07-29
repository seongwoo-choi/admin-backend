package kr.co.pincar.jpa.entity.newEntity;

import kr.co.pincar.jpa.entity.User;
import kr.co.pincar.jpa.entity.enums.MainProductType;
import kr.co.pincar.jpa.entity.enums.ReleaseStatus;
import kr.co.pincar.jpa.entity.enums.VehicleStatus;
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
public class MainProductList extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MainProductType mainProductType;

    @Column
    private Integer displayOrder;

    @Column(nullable = false)
    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ownProductList_id", nullable = false)
    private OwnProductList ownProductList;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleStatus vehicleStatus;

    public void changeVehicleStatus(VehicleStatus vehicleStatus){
        this.vehicleStatus = vehicleStatus;
    }

    public void changeOrderToNull() {
        this.displayOrder = null;
    }

    public void displayMainProductList(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

}

