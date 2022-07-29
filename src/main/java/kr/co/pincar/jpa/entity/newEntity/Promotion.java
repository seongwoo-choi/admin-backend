package kr.co.pincar.jpa.entity.newEntity;

import kr.co.pincar.jpa.entity.enums.SaveType;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Promotion extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 제목
    @Column(nullable = false)
    private String title;

    // 내용
    @Column(nullable = false)
    private String content;

    // 기간(시작일)
    @Column(nullable = false)
    private LocalDate start_at;

    // 기간(종료일)
    @Column(nullable = false)
    private LocalDate end_at;

    // 선택화면 이미지
    @Column(nullable = false)
    private String main_image;

    // 상세화면 이미지
    @Column(nullable = false)
    private String detail_image;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SaveType saveType;

    public void changePromotion(String title, String content, LocalDate start_at, LocalDate end_at, String main_image, String detail_image, SaveType saveType) {
        this.title = title;
        this.content = content;
        this.start_at = start_at;
        this.end_at = end_at;
        this.detail_image = detail_image;
        this.main_image = main_image;
        this.saveType = saveType;
    }
}
