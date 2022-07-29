package kr.co.pincar.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.pincar.exeception.BaseException;
import kr.co.pincar.exeception.BaseResponse;
import kr.co.pincar.exeception.BaseResponseCode;
import kr.co.pincar.jpa.dto.division.DivisionDeleteReq;
import kr.co.pincar.jpa.dto.division.DivisionReq;
import kr.co.pincar.jpa.dto.division.DivisionStatusUpdateReq;
import kr.co.pincar.jpa.dto.divisionHistory.DivisionHistoryCreateReq;
import kr.co.pincar.jpa.entity.enums.ConsultType;
import kr.co.pincar.jpa.entity.enums.PurchaseType;
import kr.co.pincar.jpa.dto.estimate.*;
import kr.co.pincar.jpa.entity.newEntity.Estimate;
import kr.co.pincar.jpa.entity.newEntity.Kakao;
import kr.co.pincar.service.EstimateService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

import static kr.co.pincar.utils.ValidationRegex.*;

@Api(tags = {"견적 문의"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class EstimateController {
    private final EstimateService estimateService;

    // 견적문의 상담 접수 등록
    @PostMapping("/estimates")
    @ApiOperation(value = "견적문의 상담접수 등록 API" , notes = "견적문의 상담접수를 등록합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "estimateCreateReq", value = "견적문의 생성 DTO", required = true)
    })
    public BaseResponse<Void> postEstimate(@RequestBody @Valid EstimateCreateReq estimateCreateReq) {
        if (!isStringEmpty(estimateCreateReq.getPurchaseType()) && !EnumUtils.isValidEnum(PurchaseType.class, estimateCreateReq.getPurchaseType())) {
            return new BaseResponse<>(BaseResponseCode.INVALID_PURCHASE_TYPE);
        }

        if (!isStringEmpty(estimateCreateReq.getConsultType()) && !EnumUtils.isValidEnum(ConsultType.class, estimateCreateReq.getConsultType())) {
            return new BaseResponse<>(BaseResponseCode.INVALID_CONSULT_TYPE);
        }

        // 상담 가능 시간
        estimateService.createEstimate(estimateCreateReq);
        return new BaseResponse<>(BaseResponseCode.OK);
    }

    // 견적문의 상담접수 조회
    @GetMapping("/estimates/list")
    @ApiOperation(value = "견적문의 상담접수 조회 API" , notes = "견적문의 상담접수 리스트를 조회합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "시작일자", dataType = "LocalDate"),
            @ApiImplicitParam(name = "endDate", value = "종료일자", dataType = "LocalDate")
    })
    public BaseResponse<EstimatePageRes> getEstimates(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam  @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @PageableDefault(size = 10, sort="createdAt",direction = Sort.Direction.DESC) Pageable pageable) {

        return new BaseResponse<>(estimateService.readEstimates(startDate, endDate, pageable));
    }

    // 견적문의 상담접수 상세조회
    @GetMapping("/estimates/{id}")
    @ApiOperation(value = "견적문의 상담접수 상세조회 API" , notes = "견적문의 상담접수를 상세조회합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "견적문의 id", dataType = "Long", required = true, paramType = "path")
    })
    public BaseResponse<EstimateRes> getEstimate(@PathVariable Long id) {

        Boolean existsIdx = estimateService.existsEstimate(id);

        if (!existsIdx) {
            return new BaseResponse<>(BaseResponseCode.NON_EXISTENT_ID);
        }

        return new BaseResponse<>(estimateService.readEstimate(id));
    }

    // 견적문의 상담접수 수정
    @PatchMapping("/estimates/{id}")
    @ApiOperation(value = "견적문의 상담접수 수정 API" , notes = "견적문의 상담접수를 수정합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "estimateUpdateReq", value = "견적문의 수정 DTO", required = true),
            @ApiImplicitParam(name = "id", value = "견적문의 id", dataType = "Long", required = true, paramType = "path")
    })
    public BaseResponse<Void> patchEstimate(@RequestBody @Valid EstimateUpdateReq estimateUpdateReq,
                                            @PathVariable Long id) {

        Boolean existsIdx = estimateService.existsEstimate(id);
        if (!existsIdx) {
            return new BaseResponse<>(BaseResponseCode.NON_EXISTENT_ID);
        }

        // 상담 가능 시간

        if (!isStringEmpty(estimateUpdateReq.getPurchaseType()) && !EnumUtils.isValidEnum(PurchaseType.class, estimateUpdateReq.getPurchaseType())) {
            return new BaseResponse<>(BaseResponseCode.INVALID_PURCHASE_TYPE);
        }

        if (!isStringEmpty(estimateUpdateReq.getConsultType()) && !EnumUtils.isValidEnum(ConsultType.class, estimateUpdateReq.getConsultType())) {
            return new BaseResponse<>(BaseResponseCode.INVALID_CONSULT_TYPE);
        }

        estimateService.updateEstimate(estimateUpdateReq, id);
        return new BaseResponse<>(BaseResponseCode.OK);
    }

    // 견적문의 배분 완료
    @PatchMapping("/estimates/division")
    @ApiOperation(value = "견적문의 배분 API", notes = "견적문의 상담에 대해 배분합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "divisionReq", value = "견적문의 배분완료 수정 DTO", required = true),
    })
    public BaseResponse<Void> postEstimatesDivision(@RequestBody DivisionReq divisionReq) {
        if(!divisionReq.getDivisionComplete().equals("true")) {
            throw new BaseException(BaseResponseCode.INVALID_DIVISION_TYPE_FALSE);
        }

        estimateService.createEstimateDivision(divisionReq);
        return new BaseResponse<>(BaseResponseCode.OK);
    }

    // 견적문의 배분 취소
    @PatchMapping("/estimates/divisions/cancel")
    @ApiOperation(value = "견적문의 배분 취소 API", notes = "견적문의에 대해 배분합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "divisionDeleteReq", value = "견적문의 배분완료 수정 DTO", required = true),
    })
    public BaseResponse<Void> deleteKakaosDivision(@RequestBody DivisionDeleteReq divisionDeleteReq) {
        if(!divisionDeleteReq.getDivisionComplete().equals("false")) {
            throw new BaseException(BaseResponseCode.INVALID_DIVISION_TYPE_TRUE);
        }
        estimateService.deleteEstimatesDivision(divisionDeleteReq);
        return new BaseResponse<>(BaseResponseCode.OK);
    }

    // 배분 상태 변경
    @PatchMapping("/estimates/division/{id}")
    @ApiOperation(value = "견적문의 배분 상태 변경 API", notes = "견적문의 배분 상태를 변경합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "divisionStatusUpdateReq", value = "방문상담 배분 상태 수정 DTO", required = true),
    })
    public BaseResponse<Void> kakaoDivisionChange(
            @RequestBody DivisionStatusUpdateReq divisionStatusUpdateReq
    ) {

        Estimate estimate = estimateService.findEstimate(divisionStatusUpdateReq.getId());
        if(!estimate.getDivision_complete()) {
            throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_DIVISION_STATUS);
        }

        estimateService.changeEstimateDivision(divisionStatusUpdateReq);
        return new BaseResponse<>(BaseResponseCode.OK);
    }

    @PatchMapping("/estimates/division/staff/{id}")
    @ApiOperation(value = "견적문의 배분 담당자 수정 API", notes = "배분 완료된 견적문의 담당자를 변경합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "배분 id", required = true),
            @ApiImplicitParam(name = "divisionHistoryCreateReq", value = "배분 담당자 수정 DTO", required = true),
    })
    public BaseResponse<Void> kakaoStaffIdChange(
            @PathVariable Long id,
            @RequestBody DivisionHistoryCreateReq divisionHistoryCreateReq
    ) {
        estimateService.changeStaffId(id, divisionHistoryCreateReq);
        return new BaseResponse<>(BaseResponseCode.OK);
    }

}
