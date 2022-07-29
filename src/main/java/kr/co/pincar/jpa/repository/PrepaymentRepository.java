package kr.co.pincar.jpa.repository;

import kr.co.pincar.jpa.entity.enums.Status;
import kr.co.pincar.jpa.entity.newEntity.Prepayment;
import kr.co.pincar.jpa.entity.newEntity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrepaymentRepository extends JpaRepository<Prepayment,Long> {
    List<Prepayment> findByProduct(Product product);

    Optional<Prepayment> findByIdAndStatus(Long prepaymentId, Status active);
}
