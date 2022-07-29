package kr.co.pincar.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.pincar.config.response.ResponseException;
import kr.co.pincar.exeception.BaseException;
import kr.co.pincar.exeception.BaseResponse;
import kr.co.pincar.exeception.BaseResponseCode;
import kr.co.pincar.jpa.dto.division.DivisionDeleteReq;
import kr.co.pincar.jpa.dto.division.DivisionReq;
import kr.co.pincar.jpa.dto.division.DivisionStatusUpdateReq;
import kr.co.pincar.jpa.dto.divisionHistory.DivisionHistoryCreateReq;
import kr.co.pincar.jpa.dto.visit.*;
import kr.co.pincar.jpa.entity.newEntity.Visit;
import kr.co.pincar.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@Api(tags = {"방문 상담"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class VisitController {
    private final VisitService visitService;

    // 방문상담 상담접수 등록
    @PostMapping("/visits")
    @ApiOperation(value = "방문 상담 등록 API", notes = "방문 상담을 등록합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "visitCreateReq", value = "방문 상담 등록 DTO", required = true)
    })
    public BaseResponse<Void> postVisits(@RequestBody @Valid VisitCreateReq visitCreateReq) {

        visitService.createVisits(visitCreateReq);
        return new BaseResponse<>(BaseResponseCode.OK);
    }

    // 방문상담 상담접수 수정
    @PatchMapping("/visits/{id}")
    @ApiOperation(value = "방문 상담 수정 API", notes = "방문 상담을 수정합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "visitUpdateReq", value = "방문 상담 수정 DTO", required = true, dataType = "VisitConsultUpdateReq"),
            @ApiImplicitParam(name = "id", value = "방문 상담 id", dataType = "Long", required = true, paramType = "path"),
    })
    public BaseResponse<Void> patchVisits(@RequestBody @Valid VisitUpdateReq visitUpdateReq, @PathVariable Long id) throws ResponseException {

        Boolean existsId = visitService.existsVisit(id);
        if (!existsId) {
            return new BaseResponse<>(BaseResponseCode.NON_EXISTENT_ID);
        }


        visitService.updateVisits(visitUpdateReq, id);
        return new BaseResponse<>(BaseResponseCode.OK);

    }

    // 방문상담 상담접수 조회
    @GetMapping("/visits/list")
    @ApiOperation(value = "방문 상담 조회 API" , notes = "방 상담 리스트를 조회합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "시작일자", dataType = "LocalDate"),
            @ApiImplicitParam(name = "endDate", value = "종료일자", dataType = "LocalDate")
    })
    public BaseResponse<VisitPageRes> getVisitsList(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return new BaseResponse<>(visitService.readVisitsList(startDate, endDate, pageable));
    }

    // 방문상담 상담접수 상세조회
    @ApiOperation(value = "방문 상담 조회 API" , notes = "방 상담 리스트를 조회합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "방문 상담 id", dataType = "Long", required = true, paramType = "path")
    })
    @GetMapping("/visits/{id}")
    public BaseResponse<VisitRes> getVisits(@PathVariable Long id) {

        Boolean existId = visitService.existsVisit(id);
        if (!existId) {
            return new BaseResponse<>(BaseResponseCode.NON_EXISTENT_ID);
        }

        return new BaseResponse<>(visitService.readVisits(id));
    }

    // 방문상담 배분 완료
    @PatchMapping("/visits/division")
    @ApiOperation(value = "방문상담 배분 API", notes = "방문상담에 대해 배분합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "divisionReq", value = "방문상담 배분완료 수정 DTO", required = true),
    })
    public BaseResponse<Void> postEstimatesDivision(@RequestBody DivisionReq divisionReq) {
        if(!divisionReq.getDivisionComplete().equals("true")) {
            throw new BaseException(BaseResponseCode.INVALID_DIVISION_TYPE_FALSE);
        }

        visitService.createVisitDivision(divisionReq);
        return new BaseResponse<>(BaseResponseCode.OK);
    }

    // 방문상담 배분 취소
    @PatchMapping("/visits/divisions/cancel")
    @ApiOperation(value = "방문상담 배분 취소 API", notes = "방문상담에 대해 배분합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "divisionDeleteReq", value = "방문상담 배분완료 수정 DTO", required = true),
    })
    public BaseResponse<Void> deleteKakaosDivision(@RequestBody DivisionDeleteReq divisionDeleteReq) {
        if(!divisionDeleteReq.getDivisionComplete().equals("false")) {
            throw new BaseException(BaseResponseCode.INVALID_DIVISION_TYPE_TRUE);
        }
        visitService.deleteVisitDivision(divisionDeleteReq);
        return new BaseResponse<>(BaseResponseCode.OK);
    }

    // 배분 상태 변경
    @PatchMapping("/visits/division/{id}")
    @ApiOperation(value = "방문상담 배분 상태 변경 API", notes = "방문상담 배분 상태를 변경합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "divisionStatusUpdateReq", value = "방문상담 배분 상태 수정 DTO", required = true),
    })
    public BaseResponse<Void> kakaoDivisionChange(@RequestBody DivisionStatusUpdateReq divisionStatusUpdateReq) {

        Visit visit = visitService.findVisit(divisionStatusUpdateReq.getId());
        if(!visit.getDivision_complete()) {
            throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_DIVISION_STATUS);
        }

        visitService.changeVisitDivision(divisionStatusUpdateReq);
        return new BaseResponse<>(BaseResponseCode.OK);
    }

    @PatchMapping("/visits/division/staff/{id}")
    @ApiOperation(value = "방문상담 배분 담당자 수정 API", notes = "배분 완료된 방문상담 담당자를 변경합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "배분 id", required = true),
            @ApiImplicitParam(name = "divisionHistoryCreateReq", value = "배분 담당자 수정 DTO", required = true),
    })
    public BaseResponse<Void> kakaoStaffIdChange(
            @PathVariable Long id,
            @RequestBody DivisionHistoryCreateReq divisionHistoryCreateReq
    ) {
        visitService.changeStaffId(id, divisionHistoryCreateReq);
        return new BaseResponse<>(BaseResponseCode.OK);
    }


}
