package kr.co.pincar.jpa.repository.ownProduct;

import kr.co.pincar.jpa.entity.enums.Status;
import kr.co.pincar.jpa.entity.enums.VehicleType;
import kr.co.pincar.jpa.entity.newEntity.OwnProductList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface OwnProductRepository extends JpaRepository<OwnProductList, Long>, OwnProductRepositoryCustom {
    Page<OwnProductList> findByStatus(Status active, Pageable pageable);

    Optional<Boolean> existsByIdAndStatus(Long ownProductListId, Status active);

    Optional<OwnProductList> findByIdAndStatus(Long id, Status active);

    Page<OwnProductList> findByVehicleTypeAndStatus(VehicleType type, Status active, Pageable pageable);
}
