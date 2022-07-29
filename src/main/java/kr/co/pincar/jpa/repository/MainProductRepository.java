package kr.co.pincar.jpa.repository;

import kr.co.pincar.jpa.entity.enums.MainProductType;
import kr.co.pincar.jpa.entity.enums.Status;
import kr.co.pincar.jpa.entity.newEntity.MainProductList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MainProductRepository extends JpaRepository<MainProductList,Long> {
    Optional<Boolean> existsByIdAndStatus(Long idx, Status status);

    Optional<MainProductList> findByIdAndStatus(Long id, Status status);

    Optional<MainProductList> findByMainProductTypeAndIdAndStatus(MainProductType type, Long instantReleaseId, Status active);

    List<MainProductList> findByMainProductTypeAndStatusOrderByDisplayOrder(MainProductType type, Status active);

    Optional<MainProductList>  findFirstByMainProductTypeAndStatusOrderByDisplayOrderDesc(MainProductType valueOf, Status active);

    List<MainProductList> findByMainProductTypeAndStatus(MainProductType valueOf, Status active);
}
