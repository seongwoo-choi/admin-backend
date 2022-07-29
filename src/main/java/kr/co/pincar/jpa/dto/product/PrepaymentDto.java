package kr.co.pincar.jpa.dto.product;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class PrepaymentDto {
    @ApiModelProperty(value = "선납금 id", example = "1", required = true)
    private Long id;

    @ApiModelProperty(value = "선납금", example = "10%", required = true)
    private String viewText;
}
