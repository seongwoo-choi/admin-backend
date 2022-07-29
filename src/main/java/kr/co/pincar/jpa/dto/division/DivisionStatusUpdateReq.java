package kr.co.pincar.jpa.dto.division;

import io.swagger.annotations.ApiModelProperty;
import kr.co.pincar.jpa.entity.enums.DivisionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DivisionStatusUpdateReq {
    @ApiModelProperty(value = "id", example = "1", required = true)
    private Long id;

    @ApiModelProperty(value = "배분 상태", example = "COMPLETE", required = true)
    private String divisionStatus;

    @ApiModelProperty(value = "상태 변경 사유", example = "상담 완료", required = true)
    private String reason;
}
