package kr.co.pincar.jpa.dto.teamPincarPick;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamPincarPickDisplayReq {

    @ApiModelProperty(value = "화면노출 팀핀카픽 id 리스트", example = "[1, 2, 3]", required = true)
    @NotNull(message = "화면에 노출하기로 한 팀핀카픽 id의 리스트를 입력하세요")
    private List<Long> pickIdList = new ArrayList<>();

}
