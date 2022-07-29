package kr.co.pincar.jpa.dto.product;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class BrandDto {
    @ApiModelProperty(value = "브랜드 id", example = "1", required = true)
    private Long id;

    @ApiModelProperty(value = "브랜드명", example = "벤츠", required = true)
    private String name;

    @ApiModelProperty(value = "브랜드 이미지", example = "http", required = true)
    private String image;
}
