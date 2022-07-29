package kr.co.pincar.jpa.dto.instantRelease;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class DisplayOrderList {
    @ApiModelProperty(value = "즉시 출고 id", example = "1", required = true)
    private Long instantReleaseId;
    @ApiModelProperty(value = "즉시 출고 순서", example = "1", required = true)
    private Integer displayOrder;
}
