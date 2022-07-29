package kr.co.pincar.jpa.entity.newEntity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("V")
public class Visit extends Question {

    // 이름
    @Column(nullable = false)
    private String customer_name;

    // 전화번호
    @Column(nullable = false)
    private String customer_phone;

    // 방문 희망 주소
    @Column(nullable = false)
    private String address;

    // 개인정보 동의
    @Column(nullable = false)
    private Boolean personal_info_check;

    // 마케팅 동의
    @Column(nullable = false)
    private Boolean marketing_info_check;

    public void changeVisit(String customer_name, String customer_phone, String address, Boolean personal_info_check, Boolean marketing_info_check) {
        this.customer_name = customer_name;
        this.customer_phone = customer_phone;
        this.address = address;
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
