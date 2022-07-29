package kr.co.pincar.jpa.entity.newEntity;

import kr.co.pincar.jpa.entity.enums.CountryType;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Entity
@Getter
public class Brand extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CountryType countryType;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String image;


}
