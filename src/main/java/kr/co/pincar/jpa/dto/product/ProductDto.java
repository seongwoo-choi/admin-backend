package kr.co.pincar.jpa.dto.product;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class ProductDto {
    @ApiModelProperty(value = "상품 id", example = "1", required = true)
    private Long id;

    @ApiModelProperty(value = "상품명", example = "벤츠차", required = true)
    private String name;

    @ApiModelProperty(value = "상품 이미지", example = "http", required = true)
    private String image;
}
