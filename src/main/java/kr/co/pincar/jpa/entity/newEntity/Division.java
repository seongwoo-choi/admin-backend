package kr.co.pincar.jpa.entity.newEntity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import kr.co.pincar.jpa.entity.BaseEntity;
import kr.co.pincar.jpa.entity.enums.DivisionStatus;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@DynamicInsert
@DynamicUpdate
public class Division extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String staff_id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(10) default 'READY'")
    private DivisionStatus division_status;

    private LocalDate complete_at;

    @OneToOne(mappedBy = "division")
    @JsonManagedReference
    private Question question;

    public void changeStatus(DivisionStatus divisionStatus) {
        this.division_status = divisionStatus;
    }
}
