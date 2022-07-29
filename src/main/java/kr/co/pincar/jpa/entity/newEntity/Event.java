package kr.co.pincar.jpa.entity.newEntity;

import kr.co.pincar.jpa.entity.BaseEntity;
import kr.co.pincar.jpa.entity.enums.EventStatus;
import kr.co.pincar.jpa.entity.enums.SaveType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Event extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDate startAt;

    @Column(nullable = false)
    private LocalDate endAt;

    private String link; // 추가

    @Column(columnDefinition = "varchar(10) default 'STAND_BY'")
    @Enumerated(EnumType.STRING)
    private EventStatus eventStatus;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "main_image_id")
    private Image mainImage;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "detail_image_id")
    private Image detailImage;

    @Enumerated(EnumType.STRING)
    private SaveType saveType;

    // Event 수정 메소드
    public void changeEvent(String title, String content, LocalDate startAt, LocalDate endAt, String link,
                            EventStatus eventStatus, Image mainImage, Image detailImage, SaveType saveType) {
        this.title = title;
        this.content = content;
        this.startAt = startAt;
        this.endAt = endAt;
        this.link = link;
        this.eventStatus = eventStatus;
        this.mainImage = mainImage;
        this.detailImage = detailImage;
        this.saveType = saveType;
    }
}