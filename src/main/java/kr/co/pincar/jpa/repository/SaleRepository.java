package kr.co.pincar.jpa.repository;

import kr.co.pincar.jpa.entity.enums.Status;
import kr.co.pincar.jpa.entity.newEntity.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface SaleRepository extends JpaRepository<Sale, Long> {
    Page<Sale> findByStatus(Status status, Pageable pageable);

    Optional<Sale> findByIdAndStatus(Long id, Status status);
}
