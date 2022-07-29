package kr.co.pincar.jpa.entity.newEntity;

import kr.co.pincar.jpa.entity.enums.ConsultType;
import kr.co.pincar.jpa.entity.enums.PurchaseType;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@DynamicInsert
@DynamicUpdate
@DiscriminatorValue("E")
public class Estimate extends Question {
    // 이름
    @Column(nullable = false)
    private String customer_name;

    // 전화번호
    @Column(nullable = false)
    private String customer_phone;

    // 렌트/리스
    @Enumerated(EnumType.STRING)
    private PurchaseType purchase_type;

    // 상담 가능 시간
    private String cont_time;

    // 상품 (즉시출고, 메인화면, SUV ...)
    private String location_type;

    // 문의 내용
    private String content;

    // 상담방식 (카카오톡, 유선, 미선택)
    @Column(columnDefinition = "varchar(10) default 'CABLE'")
    @Enumerated(EnumType.STRING)
    private ConsultType consult_type;

    // 개인정보 취급 방침
    private Boolean personal_info_check;

    // 마케팅 동의
    private Boolean marketing_info_check;

    public void changeEstimate(String customer_name, String customer_phone, PurchaseType purchase_type, String cont_time, String location_type, String content, ConsultType consult_type, Boolean personal_info_check, Boolean marketing_info_check) {
        this.customer_name = customer_name;
        this.customer_phone = customer_phone;
        this.purchase_type = purchase_type;
        this.cont_time = cont_time;
        this.location_type = location_type;
        this.content = content;
        this.consult_type = consult_type;
        this.personal_info_check = personal_info_check;
        this.marketing_info_check = marketing_info_check;
    }

    public void changeDivision(boolean division) {
        setDivision_complete(division);
        if(division) {
            setDivision_at(LocalDateTime.now());
        } else {
            setDivision_at(null);
        }
    }
}
