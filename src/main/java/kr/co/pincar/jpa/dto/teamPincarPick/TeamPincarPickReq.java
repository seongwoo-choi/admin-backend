package kr.co.pincar.jpa.dto.teamPincarPick;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TeamPincarPickReq {

    @ApiModelProperty(value = "렌탈료", example = "628910", required = true)
    @NotNull(message = "리스/렌탈료를 입력하세요.")
    private Integer rentalFee;

    @ApiModelProperty(value = "보유차량 idx", example = "1", required = true)
    @NotNull(message = "보유차량 인덱스를 입력하세요.")
    private Long ownProductListId;

}
