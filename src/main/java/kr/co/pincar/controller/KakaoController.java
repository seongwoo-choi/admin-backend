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
import kr.co.pincar.jpa.dto.kakao.*;
import kr.co.pincar.jpa.entity.newEntity.Kakao;
import kr.co.pincar.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@Api(tags = {"카카오톡 상담"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class KakaoController {
    private final KakaoService kakaoService;

    // 카카오톡 상담 등록
    @PostMapping("/kakaos")
    @ApiOperation(value = "카카오톡 상담 등록 API", notes = "카카오톡 상담을 등록합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "kakaoCreateReq", value = "카카오톡 상담 등록 DTO", required = true),
    })
    public BaseResponse<Void> postKakaos(@RequestBody @Valid KakaoCreateReq kakaoCreateReq) {

        kakaoService.createKakaos(kakaoCreateReq);
        return new BaseResponse<>(BaseResponseCode.OK);
    }

    // 카카오톡 상담 수정
    @PatchMapping("/kakaos/{id}")
    @ApiOperation(value = "카카오톡 상담 수정 API", notes = "카카오톡 상담을 수정합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "카카오톡 상담 id", dataType = "Long", required = true, paramType = "path"),
            @ApiImplicitParam(name = "kakaoUpdateReq", value = "카카오톡 상담 등 DTO", required = true)
    })
    public BaseResponse<Void> patchKakaos(@PathVariable Long id, @RequestBody @Valid KakaoUpdateReq kakaoUpdateReq) {

        Boolean existsId = kakaoService.existsById(id);
        if (!existsId) {
            return new BaseResponse<>(BaseResponseCode.NON_EXISTENT_ID);
        }

        kakaoService.updateKakaos(kakaoUpdateReq, id);
        return new BaseResponse<>(BaseResponseCode.OK);
    }

    // 카카오톡 상담 조회
    @GetMapping("/kakaos/list")
    @ApiOperation(value = "카카오톡 상담 조회 API", notes = "카카오톡 상담 리스트를 조회합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "시작일자", dataType = "LocalDate"),
            @ApiImplicitParam(name = "endDate", value = "종료일자", dataType = "LocalDate")
    })
    public BaseResponse<KakaoPageRes> getkakaosList(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) { // 파라미터 page, size 지정할 수 있음, page 0부터

        return new BaseResponse<>(kakaoService.readKakaosList(startDate, endDate, pageable));
    }


    // 카카오톡 상담 상세조회
    @GetMapping("/kakaos/{id}")
    @ApiOperation(value = "카카오톡 상담 상세조회 API", notes = "카카오톡 상담을 상세조회합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "카카오톡 상담 id", dataType = "Long", required = true, paramType = "path")
    })
    public BaseResponse<KakaoRes> getKakaos(@PathVariable Long id) {
        Boolean existsId = kakaoService.existsById(id);
        if (!existsId) {
            return new BaseResponse<>(BaseResponseCode.NON_EXISTENT_ID);
        }

        return new BaseResponse<>(kakaoService.readKakaos(id));

    }


    // 배분 완료 -> question 을 타고들어가서 division 에 값을 입력해야 된다.
    // 저장해야 할 값들은 question_id, division_name,

    // 카카오톡 상담 배분완료
    @PatchMapping("/kakaos/divisions")
    @ApiOperation(value = "카카오톡 상담 배분 API", notes = "카카오톡 상담에 대해 배분합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "divisionReq", value = "견적문의 배분완료 수정 DTO", required = true),
    })
    public BaseResponse<Void> postKakaosDivision(@RequestBody DivisionReq divisionReq) {
        if(!divisionReq.getDivisionComplete().equals("true")) {
            throw new BaseException(BaseResponseCode.INVALID_DIVISION_TYPE_FALSE);
        }
        kakaoService.createKakaosDivision(divisionReq);
        return new BaseResponse<>(BaseResponseCode.OK);
    }

    // 카카오톡 상담 배분 취소
    @PatchMapping("/kakaos/divisions/cancel")
    @ApiOperation(value = "카카오톡 상담 배분 취소 API", notes = "카카오톡 상담에 대해 배분합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "divisionDeleteReq", value = "견적문의 배분완료 수정 DTO", required = true),
    })
    public BaseResponse<Void> deleteKakaosDivision(@RequestBody DivisionDeleteReq divisionDeleteReq) {
        if(!divisionDeleteReq.getDivisionComplete().equals("false")) {
            throw new BaseException(BaseResponseCode.INVALID_DIVISION_TYPE_TRUE);
        }
        kakaoService.deleteKakaosDivision(divisionDeleteReq);
        return new BaseResponse<>(BaseResponseCode.OK);
    }

    @PatchMapping("/kakaos/division/{id}")
    @ApiOperation(value = "카카오톡 상담 배분 상태 변경 API", notes = "카카오톡 상담의 배분 상태를 변경합니다.")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "divisionStatusUpdateReq", value = "카카오톡 상담 배분 상태 수정 DTO", required = true),
            })
    public BaseResponse<Void> kakaoDivisionChange(
            @RequestBody DivisionStatusUpdateReq divisionStatusUpdateReq
            ) {

        Kakao kakao = kakaoService.findKakao(divisionStatusUpdateReq.getId());
        if(!kakao.getDivision_complete()) {
            throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_DIVISION_STATUS);
        }

        kakaoService.changeKakaoDivision(divisionStatusUpdateReq);
        return new BaseResponse<>(BaseResponseCode.OK);
    }

    @PatchMapping("/kakaos/division/staff/{id}")
    @ApiOperation(value = "카카오톡 상담 배분 담당자 수정 API", notes = "배분 완료된 카카오톡 상담의 담당자를 변경합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "문의 id", required = true),
            @ApiImplicitParam(name = "divisionHistoryCreateReq", value = "배분 담당자 수정 DTO", required = true),
    })
    public BaseResponse<Void> kakaoStaffIdChange(
            @PathVariable Long id,
            @RequestBody DivisionHistoryCreateReq divisionHistoryCreateReq
    ) {
        kakaoService.changeStaffId(id, divisionHistoryCreateReq);
        return new BaseResponse<>(BaseResponseCode.OK);
    }
}
