package kr.co.pincar.jpa.dto.product;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
@Builder
public class ProductListRes {
    @ApiModelProperty(value = "국산/수입 리스트", required = true)
    private List<String> countryList;

    @ApiModelProperty(value = "브랜드 리스트", required = true)
    private List<BrandDto> brandDto;

    @ApiModelProperty(value = "상품 리스트", required = true)
    private List<ProductDto> productDto;

    @ApiModelProperty(value = "계약기간 리스트", required = true)
    private List<PeriodDto> periodDto;

    @ApiModelProperty(value = "연간주행거리 리스트", required = true)
    private List<YearMileageDto> yearMileageDto;

    @ApiModelProperty(value = "보증금 리스트", required = true)
    private List<DepositDto> depositDto;

    @ApiModelProperty(value = "선납금 리스트", required = true)
    private List<PrepaymentDto> prepaymentDto;


}
