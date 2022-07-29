package kr.co.pincar.jpa.dto.sale;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SaleRes {

    @ApiModelProperty(value = "1000000", example = "판매된 차량 가격", required = true)
    private int price;

    @ApiModelProperty(value = "차량 이름", example = "그랜져", required = true)
    private String product_name;

    @ApiModelProperty(value = "구매 유형", example = "RENT", required = true)
    private String purchase_type;

    @ApiModelProperty(value = "브랜드 이름", example = "현대", required = true)
    private String brand;

    @ApiModelProperty(value = "국산 / 수입", example = "DOMESTIC", required = true)
    private String countryType;

    @ApiModelProperty(value = "분배 id", example = "1", required = true)
    private Long division_id;

    @ApiModelProperty(value = "직원 아이디", example = "seo", required = true)
    private String staff_id;

}
