package kr.co.pincar.jpa.repository;

import kr.co.pincar.jpa.entity.newEntity.Kakao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface KakaoRepository extends JpaRepository<Kakao,Long> {
    Page<Kakao> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);
}
