package kr.co.pincar.jpa.repository;

import kr.co.pincar.jpa.entity.enums.Status;
import kr.co.pincar.jpa.entity.newEntity.Product;
import kr.co.pincar.jpa.entity.newEntity.YearMileage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface YearMileageRepository extends JpaRepository<YearMileage, Long> {
    List<YearMileage> findByProduct(Product product);

    Optional<YearMileage> findByIdAndStatus(Long yearMileageId, Status active);
}
