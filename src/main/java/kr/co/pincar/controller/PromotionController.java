package kr.co.pincar.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.pincar.exeception.BaseResponse;
import kr.co.pincar.exeception.BaseResponseCode;
import kr.co.pincar.jpa.dto.promotion.PromotionPageRes;
import kr.co.pincar.jpa.dto.promotion.PromotionProductListRes;
import kr.co.pincar.jpa.dto.promotion.PromotionReq;
import kr.co.pincar.jpa.dto.promotion.PromotionRes;
import kr.co.pincar.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Api(tags = {"프로모션"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PromotionController {
    private final PromotionService promotionService;

    // 프로모션 저장/임시저장
    @PostMapping("/promotions")
    @ApiOperation(value = "프로모션 저장/임시저장 API", notes = "프로모션을 저장/임시저장합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "promotionReq", value = "프로모션 Request DTO", required = true, dataType = "PromotionReq"),
    })
    public BaseResponse<Void> postPromotions(@RequestBody @Valid PromotionReq promotionReq) {

        promotionService.createPromotions(promotionReq);
        return new BaseResponse<>(BaseResponseCode.OK);
    }

    // 프로모션 수정
    @PatchMapping("/promotions/{id}")
    @ApiOperation(value = "프로모션 수정 API", notes = "프로모션을 수정합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "promotionReq", value = "프로모션 Request DTO", required = true, dataType = "PromotionReq"),
            @ApiImplicitParam(name = "id", value = "프로모션 id", dataType = "Long", required = true, paramType = "path")
    })
    public BaseResponse<Void> patchPromotions(@PathVariable Long id, @RequestBody @Valid PromotionReq promotionReq) {

        boolean isPromotion = promotionService.existsById(id);
        if (!isPromotion) {
            return new BaseResponse<>(BaseResponseCode.NON_EXISTENT_ID);
        }

        promotionService.updatePromotions(promotionReq, id);
        return new BaseResponse<>(BaseResponseCode.OK);
    }

    // 프로모션 조회
    @GetMapping("/promotions/list")
    @ApiOperation(value = "프로모션 조회 API", notes = "프로모션 리스트를 조회합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "시작일자", dataType = "LocalDate"),
            @ApiImplicitParam(name = "endDate", value = "종료일자", dataType = "LocalDate")
    })
    public BaseResponse<PromotionPageRes> getPromotionsList(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return new BaseResponse<>(promotionService.readPromotionsList(startDate, endDate, pageable));
    }

    // 프로모션 상세조회
    @GetMapping("/promotions/{id}")
    @ApiOperation(value = "프로모션 상세조회 API", notes = "프로모션을 상세조회합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "프로모션 id", required = true, paramType = "path")
    })
    public BaseResponse<PromotionRes> getPromotions(@PathVariable Long id) {

//        boolean isPromotion = promotionService.existsById(id);
//        if (!isPromotion) {
//            return new BaseResponse<>(BaseResponseCode.NON_EXISTENT_ID);
//        }

        PromotionRes promotionRes = promotionService.readPromotions(id);
        return new BaseResponse<>(promotionRes);
    }

    // 프로모션 삭제
    @DeleteMapping("/promotions/{id}")
    @ApiOperation(value = "프로모션 삭제 API", notes = "프로모션을 삭제합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "프로모션 idx", dataType = "Long", required = true, paramType = "path")
    })
    public BaseResponse<Void> deletePromotions(@PathVariable Long id) {

        boolean isPromotion = promotionService.existsById(id);
        if (!isPromotion) {
            return new BaseResponse<>(BaseResponseCode.NON_EXISTENT_ID);
        }

        promotionService.deletePromotions(id);
        return new BaseResponse<>(BaseResponseCode.OK);
    }

    // 프로모션 차량 조회
    @GetMapping("/promotions/product")
    @ApiOperation(value = "프로모션 차량 조회 API", notes = "프로모션 차량을 조회합니다.")
    public BaseResponse<List<PromotionProductListRes>> getPromotionProduct() {
        return new BaseResponse<>(promotionService.readPromotionsProduct());
    }
}
