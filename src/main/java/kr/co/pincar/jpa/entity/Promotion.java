//package kr.co.pincar.jpa.entity;
//
//import kr.co.pincar.jpa.entity.enums.SaveType;
//import lombok.*;
//import org.hibernate.annotations.DynamicInsert;
//
//import javax.persistence.*;
//import java.time.LocalDate;
//
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Builder
//@Entity
//@DynamicInsert
//// 프로모션
//public class Promotion extends BaseEntity{
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(nullable = false, updatable = false)
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "vehicle_id", nullable = false)
//    private Vehicle vehicle;
//
//    // 제목
//    @Column(nullable = false, length = 50)
//    private String title;
//
//    // 썸네일
//    @Column(nullable = false, columnDefinition = "TEXT")
//    private String thumbnail;
//
//    // 내용
//    @Column(nullable = false, columnDefinition = "TEXT")
//    private String content;
//
//    // 시작일자
//    @Column(nullable = false)
//    private LocalDate startDate;
//
//    // 종료일자
//    @Column(nullable = false)
//    private LocalDate endDate;
//
//    // 내용이미지
//    @Column(nullable = false, columnDefinition = "TEXT")
//    private String contentImage;
//
//    // 저장타입
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private SaveType saveType;
//
//    // 상태
//    @Column(nullable = false, columnDefinition = "varchar(10) default 'ACTIVE'")
//    private String status;
//
//    public void changePromotion(Vehicle vehicle, String title, String thumbnail, String content, LocalDate startDate, LocalDate endDate, String contentImage, SaveType saveType) {
//        this.vehicle = vehicle;
//        this.title = title;
//        this.thumbnail = thumbnail;
//        this.content = content;
//        this.startDate = startDate;
//        this.endDate = endDate;
//        this.contentImage = contentImage;
//        this.saveType = saveType;
//    }
//
//    public void changeStatus(){
//        this.status = "INACTIVE";
//    }
//
//
//
//}
