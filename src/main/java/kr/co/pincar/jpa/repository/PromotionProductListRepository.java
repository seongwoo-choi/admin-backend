package kr.co.pincar.jpa.repository;

import kr.co.pincar.jpa.entity.enums.Status;
import kr.co.pincar.jpa.entity.newEntity.Promotion;
import kr.co.pincar.jpa.entity.newEntity.PromotionProductList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PromotionProductListRepository extends JpaRepository<PromotionProductList, Long> {

    void deleteAllByPromotion(Promotion promotion);

    List<PromotionProductList> findAllByPromotionAndStatus(Promotion promotion, Status active);

    List<PromotionProductList> findAllByStatus(Status active);
}
