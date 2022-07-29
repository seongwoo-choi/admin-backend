package kr.co.pincar.jpa.entity.newEntity;


import kr.co.pincar.jpa.entity.BaseEntity;
import kr.co.pincar.jpa.entity.enums.DivisionStatus;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@DynamicInsert
@DynamicUpdate
public class DivisionHistory extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String staff_id;

    private String reason;

    @Enumerated(EnumType.STRING)
    private DivisionStatus division_status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "division_id")
    private Division division;
}
