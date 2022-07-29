package kr.co.pincar.jpa.entity.newEntity;

import kr.co.pincar.jpa.entity.newEntity.BaseEntity;
import kr.co.pincar.jpa.entity.enums.DisplayStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pick")
public class TeamPincarPick extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pick_id")
    private Long id;

    private Integer rentalFee; // 렌탈료 직접등록

    private Integer displayOrder; // 화면 정렬순서 정하기

    @Enumerated(EnumType.STRING)
    private DisplayStatus displayStatus;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "own_product_list_id")
    private OwnProductList ownProductList;

    public void changeTeamPincarPick(int rentalFee, OwnProductList ownProductList) {
        this.rentalFee = rentalFee;
        this.ownProductList = ownProductList;
    }

    public void changeDisplay(int displayOrder, DisplayStatus displayStatus) {
        this.displayOrder = displayOrder;
        this.displayStatus = displayStatus;
    }

}
