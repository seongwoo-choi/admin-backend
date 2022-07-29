package kr.co.pincar.jpa.repository;

import kr.co.pincar.jpa.entity.enums.Status;
import kr.co.pincar.jpa.entity.newEntity.Deposit;
import kr.co.pincar.jpa.entity.newEntity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepositRepository extends JpaRepository<Deposit,Long> {
    List<Deposit> findByProduct(Product product);

    Optional<Deposit> findByIdAndStatus(Long depositId, Status active);
}

