package kr.co.pincar.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.pincar.exeception.BaseResponse;
import kr.co.pincar.exeception.BaseResponseCode;
import kr.co.pincar.jpa.dto.StatusReq;
import kr.co.pincar.jpa.dto.slide.*;
import kr.co.pincar.jpa.entity.enums.SaveType;
import kr.co.pincar.service.SlideService;
import kr.co.pincar.utils.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = {"슬라이드"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SlideController {
    private final SlideService slideService;
    private final JwtTokenProvider jwtTokenProvider;

    // 슬라이드 저장/임시저장
    @PostMapping("/slides")
    @ApiOperation(value = "슬라이드 등록 API", notes = "슬라이드를 등록합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "slideReq", value = "슬라이드 Request DTO", required = true, dataType = "SlideReq"),
    })
    public BaseResponse<Void> postSlides(@RequestBody @Valid SlideReq slideReq) {
        if (!EnumUtils.isValidEnum(SaveType.class, slideReq.getStatus())) {
            return new BaseResponse<>(BaseResponseCode.INVALID_SAVE_TYPE);
        }

        slideService.createSlides(slideReq);
        return new BaseResponse<>(BaseResponseCode.OK);

    }

    // 슬라이드 수정
    @PatchMapping("/slides/{id}")
    @ApiOperation(value = "슬라이드 수정 API", notes = "슬라이드를 수정합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "slideReq", value = "슬라이드 Request DTO", required = true, dataType = "SlideReq"),
            @ApiImplicitParam(name = "id", value = "슬라이드 id", dataType = "Long", required = true, paramType = "path")
    })
    public BaseResponse<Void> patchSlides(@RequestBody @Valid SlideReq slideReq, @PathVariable Long id) {

        boolean isSlide = slideService.existsById(id);
        if (!isSlide) {
            return new BaseResponse<>(BaseResponseCode.NON_EXISTENT_ID);
        }

//        if (!EnumUtils.isValidEnum(SaveType.class, slideReq.getStatus())) {
//            return new BaseResponse<>(BaseResponseCode.INVALID_SAVE_TYPE);
//        }

        slideService.updateSlides(slideReq, id);
        return new BaseResponse<>(BaseResponseCode.OK);
    }

    // 슬라이드 조회
    @GetMapping("/slides/list")
    @ApiOperation(value = "슬라이드 조회 API", notes = "슬라이드 리스트를 조회합니다.")
    public BaseResponse<SlidePageRes> getSlidesList(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        SlidePageRes slideListResList = slideService.readSlidesList(pageable);
        return new BaseResponse<>(slideListResList);

    }


    // 슬라이드 상세조회
    @GetMapping("/slides/{id}")
    @ApiOperation(value = "슬라이드 상세조회 API", notes = "슬라이드를 상세조회합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "슬라이드 id", dataType = "Long", required = true, paramType = "path")
    })
    public BaseResponse<SlideRes> getSlides(@PathVariable Long id) {

        boolean isSlide = slideService.existsById(id);
        if (!isSlide) {
            return new BaseResponse<>(BaseResponseCode.NON_EXISTENT_ID);
        }

        SlideRes slideRes = slideService.readSlides(id);
        return new BaseResponse<>(slideRes);
    }


    // 슬라이드 종료
    @PatchMapping("/slides")
    @ApiOperation(value = "슬라이드 종료 API", notes = "슬라이드를 종료합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "슬라이드 id", dataType = "Long", required = true, paramType = "path")
    })
    public BaseResponse<Void> endSlides(@RequestBody StatusReq req) {

        slideService.endSlides(req);
        return new BaseResponse<>(BaseResponseCode.OK);
    }

}
