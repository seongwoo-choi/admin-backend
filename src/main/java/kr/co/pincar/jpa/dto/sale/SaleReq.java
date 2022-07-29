package kr.co.pincar.jpa.dto.sale;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SaleReq {

    @ApiModelProperty(value = "판매된 차량 가격", example = "10000", required = true)
    @NotNull(message = "가격을 입력하세요.")
    private int price;

    @ApiModelProperty(value = "차량 id", example = "1", required = true)
    @NotNull(message = "차량 id를 입력하세요.")
    private Long product_id;

    @ApiModelProperty(value = "배분 id", example = "1", required = true)
    @NotNull(message = "배분 id를 입력하세요.")
    private Long division_id;

    @ApiModelProperty(value = "구매 유형", example = "RENT", required = true)
    @NotNull(message = "구매유형을 입력하세요.")
    private String purchase_type;
}
