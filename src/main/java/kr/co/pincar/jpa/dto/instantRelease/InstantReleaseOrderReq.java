package kr.co.pincar.jpa.dto.instantRelease;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class InstantReleaseOrderReq {
    @ApiModelProperty(value = "즉시출고 노출 순서 변경 요청 리스트", required = true)
    List<DisplayOrderList> displayOrderList;
}
