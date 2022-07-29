package kr.co.pincar.jpa.entity.newEntity;

import kr.co.pincar.jpa.entity.enums.PurchaseType;
import kr.co.pincar.jpa.entity.newEntity.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@DynamicInsert
@DynamicUpdate
public class Sale extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private int price;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "division_id")
    private Division division;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PurchaseType purchaseType;

    public void changeSale(int price, Product product, Division division, PurchaseType purchaseType) {
        this.price = price;
        this.product = product;
        this.division = division;
        this.purchaseType = purchaseType;
    }
}
