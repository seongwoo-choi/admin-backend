package kr.co.pincar.jpa.entity.newEntity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@DiscriminatorValue("K")
@DynamicInsert
public class Kakao extends Question{

    // 카카오톡 ID
    @Column(nullable = false)
    private String kakao_id;

    //문의 차량
    @Column(nullable = false)
    private String car_name;

    public void changeKakaoConsult(String kakao_id, String car_name) {
        this.kakao_id = kakao_id;
        this.car_name = car_name;
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
