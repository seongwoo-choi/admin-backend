package kr.co.pincar.jpa.repository;

import kr.co.pincar.jpa.entity.newEntity.Slide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlideRepository extends JpaRepository<Slide, Long> {
}
