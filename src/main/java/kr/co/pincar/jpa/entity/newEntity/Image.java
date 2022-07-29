package kr.co.pincar.jpa.entity.newEntity;

import kr.co.pincar.jpa.entity.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Image {

    @Column(name = "image_id")
    @Id @GeneratedValue
    private Long id;

    private String imageTitle;

    private String imagePath;

    public void changeImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

}
