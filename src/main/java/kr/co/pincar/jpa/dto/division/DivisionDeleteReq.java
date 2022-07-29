package kr.co.pincar.jpa.dto.division;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DivisionDeleteReq {
    @ApiModelProperty(value = "아이디 리스트", example = "[1, 2, 3]", required = true)
    private List<Long> id = new ArrayList<>();

    @ApiModelProperty(value = "배분완료 여부", example = "false", required = true)
    private String divisionComplete;

    @ApiModelProperty(value = "취소 사유", example = "직원 퇴사", required = true)
    private String reason;
}
