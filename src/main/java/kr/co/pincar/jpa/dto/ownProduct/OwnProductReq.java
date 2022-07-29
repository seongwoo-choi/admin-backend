package kr.co.pincar.jpa.dto.ownProduct;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OwnProductReq {
    @ApiModelProperty(value = "차량 타입", example = "SEDAN", required = true)
    @NotBlank(message = "차량 타입을 입력하세요.")
    private String carType;

    @ApiModelProperty(value = "상품 id", example = "1", required = true)
    @NotNull(message = "상품 id를 입력하세요.")
    private Long productId;

    @ApiModelProperty(value = "게약 기간 id", example = "1", required = true)
    @NotNull(message = "계약 기간 id을 입력하세요.")
    private Long contractPeriodId;

    @ApiModelProperty(value = "주행 거리 id", example = "1", required = true)
    @NotNull(message = "주행거리 id를 입력하세요.")
    private Long yearMileageId;

    @ApiModelProperty(value = "보증금 id", example = "1", required = true)
    @NotNull(message = "보증금 id를 입력하세요.")
    private Long depositId;

    @ApiModelProperty(value = "선납금 id", example = "1", required = true)
    @NotNull(message = "선납금 id를 입력하세요.")
    private Long prepaymentId;
}
