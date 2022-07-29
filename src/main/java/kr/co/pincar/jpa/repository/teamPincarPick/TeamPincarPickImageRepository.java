package kr.co.pincar.jpa.repository.teamPincarPick;

import kr.co.pincar.jpa.entity.enums.VehicleType;
import kr.co.pincar.jpa.entity.newEntity.TeamPincarPickImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamPincarPickImageRepository extends JpaRepository<TeamPincarPickImage, Long> {

    Optional<TeamPincarPickImage> findByVehicleType(VehicleType vehicleType);
}
