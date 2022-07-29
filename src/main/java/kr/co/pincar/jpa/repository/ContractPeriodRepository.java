package kr.co.pincar.jpa.repository;

import kr.co.pincar.jpa.entity.enums.Status;
import kr.co.pincar.jpa.entity.newEntity.ContractPeriod;
import kr.co.pincar.jpa.entity.newEntity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContractPeriodRepository extends JpaRepository<ContractPeriod,Long> {
    List<ContractPeriod> findByProduct(Product product);

    Optional<ContractPeriod> findByIdAndStatus(Long contractPeriodId, Status active);
}

