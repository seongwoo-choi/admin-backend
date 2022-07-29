package kr.co.pincar.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.pincar.exeception.BaseResponse;
import kr.co.pincar.exeception.BaseResponseCode;
import kr.co.pincar.jpa.dto.mainProduct.DisplayOrderUpdateReq;
import kr.co.pincar.jpa.dto.mainProduct.MainProductCreateReq;
import kr.co.pincar.jpa.dto.mainProduct.MainProductListRes;
import kr.co.pincar.jpa.entity.enums.MainProductType;
import kr.co.pincar.jpa.entity.enums.VehicleStatus;
import kr.co.pincar.service.MainProductService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = {"메인 상품 리스트"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MainProductController {
    private final MainProductService mainProductService;

    // 메인 상품 리스트 등록
    @PostMapping("/main-products")
    @ApiOperation(value = "메인 상품 리스트 등록 API", notes = "상품리스트를 등록합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mainProductCreateReq", value = "메인 상품 리스트 등록 DTO", required = true),
    })
    public BaseResponse<Void> postMainProducts(@RequestBody @Valid MainProductCreateReq mainProductCreateReq) {
        if (!EnumUtils.isValidEnum(MainProductType.class, mainProductCreateReq.getMainProductType())) {
            return new BaseResponse<>(BaseResponseCode.INVALID_MAIN_PRODUCT_TYPE);
        }

        mainProductService.createMainProduct(mainProductCreateReq);
        return new BaseResponse<>(BaseResponseCode.OK);
    }

    // 메인 상품 리스트 삭제
    @DeleteMapping("/main-products/{id}")
    @ApiOperation(value = "메인 상품 리스트 삭제 API", notes = "메인 상품 리스트를 삭제합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "메인 상품 리스트 id", dataType = "Long", required = true, paramType = "path")
    })
    public BaseResponse<Void> deleteMainProducts(@PathVariable Long id) {

        boolean mainProduct = mainProductService.existsById(id);
        if (!mainProduct) {
            return new BaseResponse<>(BaseResponseCode.NON_EXISTENT_ID);
        }
        mainProductService.deleteMainProduct(id);
        return new BaseResponse<>(BaseResponseCode.OK);
    }

    // 메인 상품 리스트 상태변경
    @PatchMapping("/main-products/status/{id}")
    @ApiOperation(value = "메인 상품 리스트 상태 변경 API", notes = "상품 리스트 상태를 변경합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "메인 상품 리스트 id", dataType = "Long", required = true, paramType = "path"),
            @ApiImplicitParam(name = "status", value = "상태(ACTIVE, END)", dataType = "String", required = true, paramType = "query")
    })
    public BaseResponse<Void> patchMainProductsStatus(@PathVariable Long id,
                                                      @RequestParam String status) {

        boolean mainProduct = mainProductService.existsById(id);
        if (!mainProduct) {
            return new BaseResponse<>(BaseResponseCode.NON_EXISTENT_ID);
        }

        if (!EnumUtils.isValidEnum(VehicleStatus.class, status)) {
            return new BaseResponse<>(BaseResponseCode.INVALID_VEHICLE_STATUS);
        }

        mainProductService.updateMainProductStatus(id, status);
        return new BaseResponse<>(BaseResponseCode.OK);
    }

    // 메인 상품 조회하기
    @GetMapping("/main-products/list")
    @ApiOperation(value = "메인 상품 리스트 조회하기 API", notes =  "메인에 띄울 메인 상품 리스트를 조회합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "메인 상품 타입(SUV, SEDAN, ECO, INSTANT, PROMOTION)", dataType = "String", required = true, paramType = "query")
    })
    public BaseResponse<List<MainProductListRes>> getMainProductsList(@RequestParam String type) {

        return new BaseResponse<>(mainProductService.readMainProductList(type));
    }

    // 메인 상품 리스트 순서 변경
    @PatchMapping("/main-products/order")
    @ApiOperation(value = "메인 상품 리스트 순서 변경 API", notes = "메인 상품 리스트 순서를 변경합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "req", value = "메인 상품 리스트 순서변경 Request DTO", required = true, dataType = "MainProductOrderReq"),
            @ApiImplicitParam(name = "type", value = "메인 상품 타입(SUV, SEDAN, ECO, INSTANT, PROMOTION)", dataType = "String", required = true, paramType = "query")
    })
    public BaseResponse<Void> patchInstantReleasesOrder(@RequestBody @Valid DisplayOrderUpdateReq req, @RequestParam String type) {
        mainProductService.updateMainProductsOrder(req, type);
        return new BaseResponse<>(BaseResponseCode.OK);
    }


}
