package kr.co.pincar.jpa.repository.event;

import kr.co.pincar.jpa.entity.newEntity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, EventRepositoryCustom {
    Page<Event> findByStartAtBetween(LocalDate startDatetime, LocalDate endDatetime, Pageable pageable);

//    Optional<Event> findByIdxAndStatus(Long eventIdx, String active);
//
//    Page<Event> findByStatus(String active, Pageable pageable);
//
//    Page<Event> findByCreatedAtBetweenAndStatus(LocalDateTime startDatetime, LocalDateTime endDatetime, String active, Pageable pageable);
//
//    Optional<Boolean> existsByIdxAndStatus(Long idx, String active);
}
