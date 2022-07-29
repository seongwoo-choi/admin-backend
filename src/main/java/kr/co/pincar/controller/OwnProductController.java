package kr.co.pincar.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.pincar.exeception.BaseResponse;
import kr.co.pincar.exeception.BaseResponseCode;
import kr.co.pincar.jpa.dto.ownProduct.OwnProductPageRes;
import kr.co.pincar.jpa.dto.ownProduct.OwnProductReq;
import kr.co.pincar.service.OwnProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = {"보유 상품 리스트"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OwnProductController {
    private final OwnProductService ownProductService;

    // 보유상품 등록하기
    @PostMapping("/own-products")
    @ApiOperation(value = "보유 상품 등록하기 API", notes = "관리자 페이지에서 사용할 보유 상품을 등록합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ownProductReq", value = "보유상품 Request DTO", required = true),
    })
    public BaseResponse<Void> postOwnProducts(@RequestBody @Valid OwnProductReq ownProductReq) {

        ownProductService.createOwnProduct(ownProductReq);
        return new BaseResponse<>(BaseResponseCode.OK);
    }

    // 보유상품 조회하기
    @GetMapping("/own-products/list")
    @ApiOperation(value = "보유 상품 조회하기 API", notes = "관리자 페이지에서 사용할 보유 상품 리스트를 조회합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "보유 상품 타입(ALL, SUV, SEDAN, ECO)", required = true, dataType = "query"),
    })
    public BaseResponse<OwnProductPageRes> getOwnProductsList(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam String type) { // type 꼭 입력

        return new BaseResponse<>(ownProductService.readOwnProductList(pageable, type));
    }


}
