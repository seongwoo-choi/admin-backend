package kr.co.pincar.jpa.repository;

import kr.co.pincar.jpa.entity.enums.Status;
import kr.co.pincar.jpa.entity.newEntity.Promotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion,Long> {
    Optional<Promotion> findByIdAndStatus(Long id, Status active);

    Page<Promotion> findByStatus(String active, Pageable pageable);

    Page<Promotion> findByCreatedAtBetweenAndStatus(LocalDateTime startDatetime, LocalDateTime endDatetime, Status active, Pageable pageable);

    boolean existsByIdAndStatus(Long id, Status status);
}
