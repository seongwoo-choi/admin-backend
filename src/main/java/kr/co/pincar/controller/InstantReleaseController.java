package kr.co.pincar.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.pincar.exeception.BaseResponse;
import kr.co.pincar.exeception.BaseResponseCode;
import kr.co.pincar.jpa.dto.instantRelease.InstantReleaseOrderReq;
import kr.co.pincar.jpa.dto.instantRelease.InstantReleaseReq;
import kr.co.pincar.jpa.dto.instantRelease.InstantReleaseRes;
import kr.co.pincar.service.InstantReleaseService;
import kr.co.pincar.service.OwnProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = {"즉시출고"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class InstantReleaseController {
    private final InstantReleaseService instantReleaseService;
    private final OwnProductService ownProductService;

    // 즉시출고 순서 변경 = 노출 건 추가
    @PatchMapping("/instant-releases/order")
    @ApiOperation(value = "즉시출고 순서 변경 API", notes = "즉시출고 순서를 변경합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "instantReleaseOrderReq", value = "즉시출고 Request DTO", required = true),
    })
    public BaseResponse<Void> patchInstantReleasesOrder(@RequestBody @Valid InstantReleaseOrderReq instantReleaseOrderReq) {
        instantReleaseService.updateInstantReleaseOrder(instantReleaseOrderReq);
        return new BaseResponse<>(BaseResponseCode.OK);
    }

    // 즉시출고 차량 등록
    @PostMapping("/instant-releases")
    @ApiOperation(value = "즉시출고 차량 등록 API", notes = "즉시출고 차량을 등록합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "instantReleaseReq", value = "즉시출고 Request DTO", required = true),
    })
    public BaseResponse<Void> postInstantReleases(@RequestBody @Valid InstantReleaseReq instantReleaseReq) {

        boolean ownProduct = ownProductService.existsById(instantReleaseReq.getOwnProductListId());
        if (!ownProduct) {
            return new BaseResponse<>(BaseResponseCode.NON_EXISTENT_ID);
        }

        instantReleaseService.createInstantRelease(instantReleaseReq);
        return new BaseResponse<>(BaseResponseCode.OK);
    }

    // 즉시출고 차량 조회
    @GetMapping("/instant-releases/list")
    @ApiOperation(value = "즉시출고 차량 조회 API", notes = "즉시출고 차량 리스트를 조회합니다.")
    public BaseResponse<InstantReleaseRes> getReleaseVehiclesList() {
        return new BaseResponse<>(instantReleaseService.readInstantReleaseList());
    }

    // 즉시출고 차량 삭제
    @DeleteMapping("/instant-releases/{id}")
    @ApiOperation(value = "즉시출고 차량 삭제 API", notes = "즉시출고 차량을 삭제합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "즉시출고 차량 id", dataType = "Long", required = true, paramType = "path")
    })
    public BaseResponse<Void> deleteInstantReleases(@PathVariable Long id) {

        boolean instantRelease = instantReleaseService.existsById(id);
        if (!instantRelease) {
            return new BaseResponse<>(BaseResponseCode.NON_EXISTENT_ID);
        }

        instantReleaseService.deleteInstantRelease(id);
        return new BaseResponse<>(BaseResponseCode.OK);
    }

    // 즉시출고 차량 마감
    @PatchMapping("/instant-releases/status/{id}")
    @ApiOperation(value = "즉시출고 차량 마감 API", notes = "즉시출고 차량을 마감합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "즉시출고 차량 id", dataType = "Long", required = true, paramType = "path")
    })
    public BaseResponse<Void> patchInstantReleases(@PathVariable Long id) {

        boolean instantRelease = instantReleaseService.existsById(id);
        if (!instantRelease) {
            return new BaseResponse<>(BaseResponseCode.NON_EXISTENT_ID);
        }

        instantReleaseService.updateInstantRelease(id);
        return new BaseResponse<>(BaseResponseCode.OK);
    }


}
