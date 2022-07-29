package kr.co.pincar.jpa.dto.instantRelease;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
@Builder
public class InstantReleaseRes {
    @ApiModelProperty(value = "즉시 출고 노출 리스트", required = true)
    private List<DisplayList> displayList;
    @ApiModelProperty(value = "즉시 출고 임시저장 리스트", required = true)
    private List<InstantReleaseList> instantReleaseList;
}
