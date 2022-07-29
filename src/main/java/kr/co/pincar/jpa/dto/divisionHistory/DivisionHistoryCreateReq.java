package kr.co.pincar.jpa.dto.divisionHistory;

import io.swagger.annotations.ApiModelProperty;
import kr.co.pincar.jpa.entity.newEntity.Division;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DivisionHistoryCreateReq {
    @ApiModelProperty(value = "직원 id", example = "kim12323", required = true)
    private String staffId;

    @ApiModelProperty(value = "배분 담당자 변경 사유", example = "휴가", required = true)
    private String reason;
}
