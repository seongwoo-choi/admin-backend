package kr.co.pincar.jpa.entity.newEntity;

import kr.co.pincar.jpa.entity.User;
import kr.co.pincar.jpa.entity.enums.ReleaseStatus;
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
public class InstantRelease extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column
    private Integer displayOrder; // 즉시출고 화면에서 보여질 순서

    @Column(nullable = false)
    private String image; // 화면에 보여질 차량 이미지 id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ownProductList_id", nullable = false)
    private OwnProductList ownProductList;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReleaseStatus releaseStatus;

    public void changeInstantReleaseStatus() {
        this.releaseStatus = ReleaseStatus.CLOSED;
    }

    public void displayInstantRelease(Integer displayOrder) {
        this.displayOrder = displayOrder;
        this.releaseStatus = ReleaseStatus.ACTIVE;
    }

    public void changeOrderToNull() {
        this.displayOrder = null;
    }

}
