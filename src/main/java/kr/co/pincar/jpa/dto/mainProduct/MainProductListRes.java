package kr.co.pincar.jpa.dto.mainProduct;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class MainProductListRes {
    @ApiModelProperty(value = "노출 순서", example = "1", required = true)
    private Integer displayOrder;

    @ApiModelProperty(value = "메인 상품 리스트 id", example = "1", required = true)
    private Long mainProductListId;

    @ApiModelProperty(value = "메인 상품 이미지", example = "https://", required = true)
    private String image;

    @ApiModelProperty(value = "차량명", example = "벤츠", required = true)
    private String name;

    @ApiModelProperty(value = "차량가", example = "1,000,000원", required = true)
    private int price;

    @ApiModelProperty(value = "기간", example = "48개월", required = true)
    private String contractPeriod;

    @ApiModelProperty(value = "보증금", example = "무보증", required = true)
    private String  deposit;

    @ApiModelProperty(value = "선납금", example = "0원", required = true)
    private String  prepayment;

    @ApiModelProperty(value = "렌탈료", example = "10000", required = true)
    private int  rentalFee;

    @ApiModelProperty(value = "차량 상태", example = "ACTIVE", required = true)
    private String vehicleStatus;
}
