package kr.co.pincar.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.pincar.exeception.BaseResponse;
import kr.co.pincar.exeception.BaseResponseCode;
import kr.co.pincar.jpa.dto.buffer.*;
import kr.co.pincar.jpa.dto.product.ProductListRes;
import kr.co.pincar.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@Api(tags = {"상품"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;

    // 상품 전체조회
    // 브랜드 , 국산 수입 선택되어있어야함 기본값 맨 앞값
    // 국산수입, 브랜드, 차종, 계약기간, 보증금, 선납금, (주행거리생략)
    @GetMapping("/products/list")
    @ApiOperation(value = "상품 조회 API", notes = "상품을 조회합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "country", value = "국산/수입", dataType = "String", required = true, paramType = "query"),
            @ApiImplicitParam(name = "brandId", value = "브랜드 id", dataType = "Long", required = true, paramType = "query"),
            @ApiImplicitParam(name = "productId", value = "상품 id", dataType = "Long", required = true, paramType = "query")
    })
    public BaseResponse<ProductListRes> getMainProductsList(
            @RequestParam String country,
            @RequestParam Long brandId,
            @RequestParam Long productId) {
        // country enum 형식 지키기
        // brandid 없는거 조회하면 안됨
        // 두개의 값이 비었을 때 오류를 내뱉는지 ?
        ProductListRes productListRes = productService.readProductList(country, brandId, productId);
        return new BaseResponse<>(productListRes);
    }


    /**
     * 상품 등록 Buffer Code
     */
    // 1. 브랜드 생성
    @PostMapping("/new-brand")
    public BaseResponse<Void> createNewBrand(@RequestBody BrandBufferDto brandBufferDto) {
        productService.createNewBrand(brandBufferDto);
        return new BaseResponse<>(BaseResponseCode.OK);
    }

    // 2. 상품 생성
    @PostMapping("/new-product/{id}") // brand_id
    public BaseResponse<Void> createNewProduct(@RequestBody ProductBufferDto productBufferDto, @PathVariable Long id) {
        productService.createNewProduct(productBufferDto, id);
        return new BaseResponse<>(BaseResponseCode.OK);
    }

    // 3. 해당 상품에 대한 트림 생성
    @PostMapping("/new-trim/{id}") // product_id
    public BaseResponse<Void> createNewTrim(@RequestBody TrimBufferDto trimBufferDto, @PathVariable Long id) {
        productService.createNewTrim(trimBufferDto, id);
        return new BaseResponse<>(BaseResponseCode.OK);
    }

    // 4. 하나의 트림 당 옵션 생성
    @PostMapping("/new-trim-option/{id}") // trim_id
    public BaseResponse<Void> createNewTrimOption(@RequestBody TrimOptionBufferDto trimOptionBufferDto, @PathVariable Long id) {
        productService.createNewTrimOption(trimOptionBufferDto, id);
        return new BaseResponse<>(BaseResponseCode.OK);
    }

    // 5. 해당 상품에 대한 색상 생성
//    @PostMapping("/new-color/{id}") // product_id
//    public BaseResponse<Void> createNewColor(@RequestBody ColorBufferDto colorBufferDto, @PathVariable Long id) {
//        productService.createNewColor(colorBufferDto, id);
//        return new BaseResponse<>(BaseResponseCode.OK);
//    }

    // 6. 계약기간 생성
    @PostMapping("/new-period/{id}") // product_id
    public BaseResponse<Void> createNewPeriod(@RequestBody ContPeriodBufferDto contPeriodBufferDto, @PathVariable Long id) {
        productService.createNewPeriod(contPeriodBufferDto, id);
        return new BaseResponse<>(BaseResponseCode.OK);
    }

    // 7. 연간주행거리 생성
    @PostMapping("/new-mileage/{id}") // product_id
    public BaseResponse<Void> createNewMileage(@RequestBody YMBufferDto ymBufferDto, @PathVariable Long id) {
        productService.createNewMileage(ymBufferDto, id);
        return new BaseResponse<>(BaseResponseCode.OK);
    }

    // 8. 보증금 생성
    @PostMapping("/new-deposit/{id}") // product_id
    public BaseResponse<Void> createNewDeposit(@RequestBody DepositBufferDto depositBufferDto, @PathVariable Long id) {
        productService.createNewDeposit(depositBufferDto, id);
        return new BaseResponse<>(BaseResponseCode.OK);
    }

    // 9. 선납금 생성
    @PostMapping("/new-payment/{id}") // product_id
    public BaseResponse<Void> createNewPayment(@RequestBody PrepaymentBufferDto prepaymentBufferDto, @PathVariable Long id) {
        productService.createNewPayment(prepaymentBufferDto, id);
        return new BaseResponse<>(BaseResponseCode.OK);
    }

}
