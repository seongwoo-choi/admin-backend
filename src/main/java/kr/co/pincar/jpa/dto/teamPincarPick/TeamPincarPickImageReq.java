package kr.co.pincar.jpa.dto.teamPincarPick;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamPincarPickImageReq {

    @ApiModelProperty(value = "팀핀카픽 메인이미지", example = "https://", required = true)
    @NotBlank(message = "이미지를 첨부하세요.")
    private String imagePath;
}
