package kr.co.pincar.jpa.dto.product;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class DepositDto {
    @ApiModelProperty(value = "보증금 id", example = "1", required = true)
    private Long id;

    @ApiModelProperty(value = "보증금", example = "48개월", required = true)
    private String viewText;
}
