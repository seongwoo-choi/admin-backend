package kr.co.pincar.jpa.dto.instantRelease;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class InstantReleaseReq {
    @ApiModelProperty(value = "즉시출고 이미지", example = "http", required = true)
    private String image;
    @ApiModelProperty(value = "보유 상품 리스트 id", example = "1", required = true)
    private Long ownProductListId;

}
