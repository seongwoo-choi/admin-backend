package kr.co.pincar.jpa.repository;

import kr.co.pincar.jpa.entity.newEntity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
