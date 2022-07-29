package kr.co.pincar.jpa.dto.mainProduct;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class OrderUpdateList {
    @ApiModelProperty(value = " id", example = "1", required = true)
    private Long mainProductListId;
    @ApiModelProperty(value = "노출 순서", example = "1", required = true)
    private Integer displayOrder;
}
