package kr.co.pincar.service;

import kr.co.pincar.exeception.BaseException;
import kr.co.pincar.exeception.BaseResponseCode;
import kr.co.pincar.jpa.dto.promotion.*;
import kr.co.pincar.jpa.entity.enums.Status;
import kr.co.pincar.jpa.entity.newEntity.OwnProductList;
import kr.co.pincar.jpa.entity.newEntity.Promotion;
import kr.co.pincar.jpa.entity.enums.SaveType;
import kr.co.pincar.jpa.entity.newEntity.PromotionProductList;
import kr.co.pincar.jpa.repository.PromotionProductListRepository;
import kr.co.pincar.jpa.repository.PromotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PromotionService {
    private final PromotionRepository promotionRepository;
    private final PromotionProductListRepository promotionProductListRepository;
    private final OwnProductService ownProductService;

    public boolean existsById(Long id) {

        return promotionRepository.existsByIdAndStatus(id, Status.ACTIVE);

    }

    // 프로모션 등록
    @Transactional
    public void createPromotions(PromotionReq req) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start_at = LocalDate.parse(req.getStart_at(), formatter);
        LocalDate end_at = LocalDate.parse(req.getEnd_at(), formatter);

        SaveType saveType;

        if(req.getSaveType().equals("SAVE")) {
            saveType = SaveType.SAVE;
        } else if(req.getSaveType().equals("TEMPORARY")) {
            saveType = SaveType.TEMPORARY;
        } else {
            throw new BaseException(BaseResponseCode.INVALID_ENUM_TYPE);
        }

        Promotion promotion = Promotion.builder()
                .title(req.getTitle())
                .content(req.getContent())
                .start_at(start_at)
                .end_at(end_at)
                .main_image(req.getMain_image())
                .detail_image(req.getDetail_image())
                .saveType(saveType)
                .build();

        try {
            promotionRepository.save(promotion);
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_PROMOTION);
        }

        for(Long id: req.getOwner_product_id()) {
            List<PromotionProductList> productLists = promotionProductListRepository.findAll();
            OwnProductList ownProductList = ownProductService.readOwnProductListById(id);
            PromotionProductList promotionProductList = PromotionProductList.builder()
                    .own_product_list(ownProductList)
                    .display_order(productLists.size()+1)
                    .promotion(promotion)
                    .build();
            try {
                promotionProductListRepository.save(promotionProductList);
            } catch (Exception e) {
                throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_PROMOTION_PRODUCT_LIST);
            }
        }
    }

    // 프로모션 수정
    @Transactional
    public void updatePromotions(PromotionReq req, Long id) {

        Promotion promotion = promotionRepository.findByIdAndStatus(id, Status.ACTIVE)
                .orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_PROMOTION));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start_at = LocalDate.parse(req.getStart_at(), formatter);
        LocalDate end_at = LocalDate.parse(req.getEnd_at(), formatter);

        SaveType saveType;

        if(req.getSaveType().equals("SAVE")) {
            saveType = SaveType.SAVE;
        } else if(req.getSaveType().equals("TEMPORARY")) {
            saveType = SaveType.TEMPORARY;
        } else {
            throw new BaseException(BaseResponseCode.INVALID_ENUM_TYPE);
        }

        try {
            promotion.changePromotion(
                    req.getTitle(),
                    req.getContent(),
                    start_at,
                    end_at,
                    req.getDetail_image(),
                    req.getMain_image(),
                    saveType
            );
            promotionRepository.save(promotion);
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_PROMOTION);
        }

        // promotionProductList 비우기
        promotionProductListRepository.deleteAllByPromotion(promotion);

        for(Long product_id: req.getOwner_product_id()) {
            List<PromotionProductList> productLists = promotionProductListRepository.findAll();
            OwnProductList ownProductList = ownProductService.readOwnProductListById(product_id);
            PromotionProductList promotionProductList = PromotionProductList.builder()
                    .own_product_list(ownProductList)
                    .display_order(productLists.size()+1)
                    .promotion(promotion)
                    .build();
            try {
                promotionProductListRepository.save(promotionProductList);
            } catch (Exception e) {
                throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_PROMOTION_PRODUCT_LIST);
            }
        }
    }

    // 프로모션 조회
    public PromotionPageRes readPromotionsList(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        Page<Promotion> promotionList;

        LocalDateTime startDatetime = LocalDateTime.of(startDate, LocalTime.of(0, 0, 0));
        LocalDateTime endDatetime = LocalDateTime.of(endDate, LocalTime.of(23, 59, 59));


        if(pageable.getPageNumber() < 1) {
            throw new BaseException(BaseResponseCode.INVALID_PAGE);
        }

        try {
            promotionList = promotionRepository.findByCreatedAtBetweenAndStatus(startDatetime, endDatetime, Status.ACTIVE, PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort()));
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_FIND_PROMOTION);
        }

        List<PromotionListRes> promotionListResList = promotionList.stream().map(promotion -> {

            return PromotionListRes.builder()
                    .id(promotion.getId())
                    .title(promotion.getTitle())
                    .main_image(promotion.getMain_image())
                    .content(promotion.getContent())
                    .start_at(promotion.getStart_at().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .end_at(promotion.getEnd_at().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .build();
        }).collect(Collectors.toList());

        return PromotionPageRes.builder()
                .totalPage(promotionList.getTotalPages())
                .promotionListResList(promotionListResList)
                .build();
    }

    // 프로모션 상세조회
    public PromotionRes readPromotions(Long id) {
        String brand;
        String product_name;
        int product_price;
        String period;
        String deposit;
        String year_mileage;
        int monthly_fee;

        List<PromotionProductRes> promotionProductResList = new ArrayList<>();

        Promotion promotion = promotionRepository.findByIdAndStatus(id, Status.ACTIVE)
                .orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_PROMOTION));

        List<PromotionProductList> promotionProductLists = promotionProductListRepository.findAllByPromotionAndStatus(promotion, Status.ACTIVE);

        for(PromotionProductList promotionProductList: promotionProductLists) {
            brand = promotionProductList.getOwn_product_list().getProduct().getBrand().getName();
            product_name = promotionProductList.getOwn_product_list().getProduct().getName();
            product_price = promotionProductList.getOwn_product_list().getProduct().getPrice();
            period = promotionProductList.getOwn_product_list().getContractPeriod().getPeriodType().getPeriod();
            deposit = promotionProductList.getOwn_product_list().getDeposit().getView_text();
            year_mileage = promotionProductList.getOwn_product_list().getYearMileage().getViewText();
            monthly_fee = product_price / Integer.parseInt(period.substring(0, 2));

            PromotionProductRes promotionProductRes = PromotionProductRes.builder()
                    .brand(brand)
                    .product_name(product_name)
                    .product_price(product_price)
                    .periodType(period)
                    .depositType(deposit)
                    .mileageType(year_mileage)
                    .monthly_fee(monthly_fee).build();

            promotionProductResList.add(promotionProductRes);
        }

        return PromotionRes.builder()
                .id(promotion.getId())
                .title(promotion.getTitle())
                .content(promotion.getContent())
                .start_at(promotion.getStart_at().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .end_at(promotion.getEnd_at().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .detail_image(promotion.getDetail_image())
                .promotionProductRes(promotionProductResList)
                .saveType(promotion.getSaveType().name())
                .build();
    }

    // 프로모션 삭제
    @Transactional
    public void deletePromotions(Long id) {

        Promotion promotion = promotionRepository.findByIdAndStatus(id, Status.ACTIVE)
                .orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_PROMOTION));

        List<PromotionProductList> promotionProductLists = promotionProductListRepository.findAllByPromotionAndStatus(promotion, Status.ACTIVE);

        for(PromotionProductList promotionProductList: promotionProductLists) {
            try {
                promotionProductList.changeStatus();
                promotionProductListRepository.save(promotionProductList);
            } catch (Exception e) {
                throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_PROMOTION_PRODUCT_LIST);
            }
        }

        try {
            promotion.changeStatus();
            promotionRepository.save(promotion);
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_PROMOTION);
        }
    }

    public List<PromotionProductListRes> readPromotionsProduct() {
        List<PromotionProductList> promotionProductLists;

        try {
            promotionProductLists = promotionProductListRepository.findAllByStatus(Status.ACTIVE);
        } catch (Exception e){
            throw new BaseException(BaseResponseCode.FAILED_TO_FIND_PROMOTION_PRODUCT_LIST);
        }


        return promotionProductLists.stream().map(product -> {
            return PromotionProductListRes.builder()
                    .promotionProductListId(product.getId())
                    .promotionId(product.getPromotion().getId())
                    .ownProductListId(product.getOwn_product_list().getId())
                    .productName(product.getOwn_product_list().getProduct().getName())
                    .productPrice(product.getOwn_product_list().getProduct().getPrice())
                    .productImage(product.getOwn_product_list().getProduct().getImage())
                    .contractPeriod(product.getOwn_product_list().getContractPeriod().getPeriodType().getPeriod())
                    .yearMileage(product.getOwn_product_list().getYearMileage().getViewText())
                    .deposit(product.getOwn_product_list().getDeposit().getView_text())
                    .prepayment(product.getOwn_product_list().getPrepayment().getView_text())
                    .build();
        }).collect(Collectors.toList());




    }
}
