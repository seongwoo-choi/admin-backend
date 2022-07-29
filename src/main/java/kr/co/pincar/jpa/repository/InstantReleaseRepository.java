package kr.co.pincar.jpa.repository;

import kr.co.pincar.jpa.entity.enums.Status;
import kr.co.pincar.jpa.entity.newEntity.InstantRelease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InstantReleaseRepository extends JpaRepository<InstantRelease, Long> {
    Optional<Boolean> existsByIdAndStatus(Long id, Status active);

    Optional<InstantRelease> findByIdAndStatus(Long id, Status active);

    List<InstantRelease> findByDisplayOrderAndStatus(Integer displayOrder, Status active);

    List<InstantRelease> findByStatusAndDisplayOrderNotNullOrderByDisplayOrder(Status active);
}
