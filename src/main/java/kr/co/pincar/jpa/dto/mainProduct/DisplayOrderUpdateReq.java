package kr.co.pincar.jpa.dto.mainProduct;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class DisplayOrderUpdateReq {
    @ApiModelProperty(value = "메인 상품 리스트 노출 순서 변경 요청 리스트", required = true)
    List<OrderUpdateList> orderUpdateList;
}
