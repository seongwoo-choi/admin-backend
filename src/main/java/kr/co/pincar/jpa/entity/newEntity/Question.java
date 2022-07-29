package kr.co.pincar.jpa.entity.newEntity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import kr.co.pincar.jpa.entity.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorColumn
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@DynamicInsert
@DynamicUpdate
public abstract class Question extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 배분 여부
    @Column(columnDefinition = "boolean default false")
    protected Boolean division_complete;

    // 배분 일자
    private LocalDateTime division_at;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Division division;
}
