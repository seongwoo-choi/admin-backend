package kr.co.pincar.jpa.dto.releaseVehicle;

import io.swagger.annotations.ApiModelProperty;
import kr.co.pincar.jpa.dto.teamPincarPick.TeamPincarPickRes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
@Builder
public class ReleaseVehiclePageRes {
    @ApiModelProperty(value = "전체 페이지 수", example = "10", required = true)
    private int totalPage;
    @ApiModelProperty(value = "조회 리스트", required = true)
    private List<ReleaseVehicleRes> releaseVehicleResList;
}
