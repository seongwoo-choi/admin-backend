package kr.co.pincar.jpa.dto.instantRelease;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class DisplayList {
    @ApiModelProperty(value = "즉시 출고 순서", example = "1", required = true)
    private Integer displayOrder;

    @ApiModelProperty(value = "즉시 출고 id", example = "1", required = true)
    private Long instantReleaseId;

    @ApiModelProperty(value = "즉시 출고 이미지", example = "http", required = true)
    private String image;

    @ApiModelProperty(value = "차량가", example = "100000", required = true)
    private int price;

    @ApiModelProperty(value = "계약기간", example = "48개월", required = true)
    private String contractPeriod;

    @ApiModelProperty(value = "보증금", example = "무보증", required = true)
    private String deposit;

    @ApiModelProperty(value = "선납금", example = "10%", required = true)
    private String prepayment;

    @ApiModelProperty(value = "렌탈료", example = "1000000", required = true)
    private int rentalFee;

    @ApiModelProperty(value = "출고상태", example = "ACTIVE or CLOSED", required = true)
    private String releaseStatus;
}
