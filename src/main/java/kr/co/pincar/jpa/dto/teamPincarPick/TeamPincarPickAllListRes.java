package kr.co.pincar.jpa.dto.teamPincarPick;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamPincarPickAllListRes {
    @ApiModelProperty(value = "화면노출 팀핀카픽 리스트", example = "[1, 2, 3]", required = true)
    private List<TeamPincarPickRes> displayList = new ArrayList<>();

    @ApiModelProperty(value = "미사용 팀핀카픽 리스트", example = "[4, 5, 6]", required = true)
    private List<TeamPincarPickRes> noDisplayList = new ArrayList<>();
}
