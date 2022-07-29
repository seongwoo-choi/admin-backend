package kr.co.pincar.jpa.dto.product;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class YearMileageDto {
    @ApiModelProperty(value = "연간 주행 거리 id", example = "1", required = true)
    private Long id;

    @ApiModelProperty(value = "연간 주행 거리", example = "2만km", required = true)
    private String viewText;
}
