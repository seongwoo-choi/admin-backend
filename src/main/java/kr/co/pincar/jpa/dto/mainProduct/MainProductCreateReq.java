package kr.co.pincar.jpa.dto.mainProduct;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MainProductCreateReq {
    @ApiModelProperty(value = "보유 상품 id", example = "1", required = true)
    @NotNull(message = "보유 상품 id를 입력하세요.")
    private Long ownProductListId;

    @ApiModelProperty(value = "메인 상품 타입(SUV, SEDAN, ECHO, IMMEDIATE, PROMOTION)", example = "SUV", required = true)
    @NotBlank(message = "메인 상품 타입을 입력하세요.")
    private String mainProductType;

    @ApiModelProperty(value = "메인 상품 이미지", example = "https", required = true)
    @NotBlank(message = "메인 상품 이미지를 입력하세요.")
    private String mainProductImage;
}
