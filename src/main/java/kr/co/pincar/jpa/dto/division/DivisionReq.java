package kr.co.pincar.jpa.dto.division;

import io.swagger.annotations.ApiModelProperty;
import kr.co.pincar.jpa.entity.enums.DivisionStatus;
import kr.co.pincar.jpa.entity.newEntity.Question;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DivisionReq {
    @ApiModelProperty(value = "문의 id 리스트", example = "[1, 2, 3]", required = true)
    private List<Long> id = new ArrayList<>();

    @ApiModelProperty(value = "배분 완료 여부", example = "true", required = true)
    private String divisionComplete;

    @ApiModelProperty(value = "직원 id", example = "seo", required = true)
    private String staffId;
}
