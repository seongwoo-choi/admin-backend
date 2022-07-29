package kr.co.pincar.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.pincar.exeception.BaseResponse;
import kr.co.pincar.exeception.BaseResponseCode;
import kr.co.pincar.jpa.dto.sale.SaleReq;
import kr.co.pincar.jpa.dto.sale.SalePageRes;
import kr.co.pincar.jpa.dto.sale.SaleRes;
import kr.co.pincar.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = {"판매"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SaleController {
    private final SaleService saleService;

    // 판매 등록
    @PostMapping("/sales")
    @ApiOperation(value = "판매 등록 API", notes = "판매를 등록합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "saleCreateReq", value = "판매 Request DTO", required = true),
    })
    public BaseResponse<Void> postSale(@RequestBody @Valid SaleReq saleCreateReq) {

        saleService.createSale(saleCreateReq);
        return new BaseResponse<>(BaseResponseCode.OK);
    }

    // 판매 전체 조회
    @GetMapping("/sales")
    @ApiOperation(value = "판매 조회 API", notes = "판매를 조회합니다.")
    public BaseResponse<SalePageRes> postSale(@PageableDefault(size = 10, sort="createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return new BaseResponse<>(saleService.readSales(pageable));
    }

    // 판매 상세 조회
    @GetMapping("/sales/{id}")
    @ApiOperation(value = "판매 상세 조회 API", notes = "판매 상세 조회합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "판매 id", required = true),
    })
    public BaseResponse<SaleRes> postSale(@PathVariable Long id) {

        return new BaseResponse<>(saleService.readSale(id));
    }

    // 판매 수정
    @PatchMapping("/sales/{id}")
    @ApiOperation(value = "판매 수정 API", notes = "판매 수정합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "판매 id", required = true),
            @ApiImplicitParam(name = "saleReq", value = "판매 수정 DTO", required = true),
    })
    public BaseResponse<Void> patchSale(@PathVariable Long id, @RequestBody @Valid SaleReq saleReq) {

        saleService.updateSale(id, saleReq);
        return new BaseResponse<>(BaseResponseCode.OK);
    }

    // 판매 삭제
    @PatchMapping("/sales")
    @ApiOperation(value = "판매 삭제 API", notes = "판매를 삭제합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "idList", value = "판매 id 리스트", required = true),
    })
    public BaseResponse<Void> patchSale(@RequestBody List<Long> idList) {
        saleService.deleteSale(idList);

        return new BaseResponse<>(BaseResponseCode.OK);
    }
}
