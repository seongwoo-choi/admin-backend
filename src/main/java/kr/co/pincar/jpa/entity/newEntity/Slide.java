package kr.co.pincar.jpa.entity.newEntity;

import kr.co.pincar.jpa.entity.BaseEntity;
import kr.co.pincar.jpa.entity.enums.SaveType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Slide extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 제목
    @Column(nullable = false)
    private String title;

    // 내용
    @Column(nullable = false)
    private String content;

    // 링크 페이지
    @Column(nullable = false)
    private String link;

    // 게시 시작일
    @Column(nullable = false)
    private LocalDate start_at;

    // 게시 종료일
    @Column(nullable = false)
    private LocalDate end_at;

    // 메인 이미지
    @Column(nullable = false)
    private String image;

    // 게시 상태 (저장 임시저장)
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SaveType status;

    public void changeSlide(String title, String content, String link, LocalDate start_at, LocalDate end_at, String image, SaveType status) {
        this.title = title;
        this.content = content;
        this.link = link;
        this.start_at = start_at;
        this.end_at = end_at;
        this.image = image;
        this.status = status;
    }

    public void changeStatus() {
        this.status = SaveType.END;
    }
}
