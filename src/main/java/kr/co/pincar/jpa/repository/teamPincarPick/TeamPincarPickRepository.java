package kr.co.pincar.jpa.repository.teamPincarPick;

import kr.co.pincar.jpa.entity.enums.DisplayStatus;
import kr.co.pincar.jpa.entity.enums.Status;
import kr.co.pincar.jpa.entity.newEntity.TeamPincarPick;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamPincarPickRepository extends JpaRepository<TeamPincarPick,Long>, TeamPincarPickRepositoryCustom {
    Optional<TeamPincarPick> findByIdAndStatus(Long pickId, Status status);

}
