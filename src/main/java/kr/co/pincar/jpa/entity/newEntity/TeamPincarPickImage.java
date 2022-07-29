package kr.co.pincar.jpa.entity.newEntity;

import kr.co.pincar.jpa.entity.enums.VehicleType;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "pick_image")
public class TeamPincarPickImage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pick_image_id")
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "image_id")
    private Image image;
}
