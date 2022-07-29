package kr.co.pincar.jpa.dto.promotion;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class PromotionProductListRes {
    @ApiModelProperty(value = "프로모션 상품 리스트 id", example = "1", required = true)
    private Long promotionProductListId;

    @ApiModelProperty(value = "프로모션 id", example = "1", required = true)
    private Long promotionId;

    @ApiModelProperty(value = "보유 상품 리스트 id", example = "1", required = true)
    private Long ownProductListId;

    @ApiModelProperty(value = "차량명", example = "벤츠차", required = true)
    private String productName;

    @ApiModelProperty(value = "차량가", example = "10000", required = true)
    private int productPrice;

    @ApiModelProperty(value = "차량 이미지", example = "https", required = true)
    private String productImage;

    @ApiModelProperty(value = "계약 기간", example = "48개월", required = true)
    private String contractPeriod;

    @ApiModelProperty(value = "연간 주행 거리", example = "2만km", required = true)
    private String yearMileage;

    @ApiModelProperty(value = "보증금", example = "10%", required = true)
    private String deposit;

    @ApiModelProperty(value = "선납금", example = "10%", required = true)
    private String prepayment;
}
