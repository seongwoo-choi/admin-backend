package kr.co.pincar.jpa.dto.promotion;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PromotionProductRes {
    @ApiModelProperty(value = "브랜드 명", example = "현대", required = true)
    private String brand;

    @ApiModelProperty(value = "차량명", example = "그랜져", required = true)
    private String product_name;

    @ApiModelProperty(value = "차량가", example = "100000000", required = true)
    private int product_price;

    @ApiModelProperty(value = "계약 기간", example = "TF_MONTH", required = true)
    private String periodType;

    @ApiModelProperty(value = "보증금", example = "NO_DEPOSIT", required = true)
    private String depositType;

    @ApiModelProperty(value = "주행 거리", example = "T_DISTANCE", required = true)
    private String mileageType;

    @ApiModelProperty(value = "렌탈/리스 요금", example = "1000000", required = true)
    private int monthly_fee;
}
