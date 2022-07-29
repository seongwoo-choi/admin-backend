package kr.co.pincar.jpa.dto.releaseVehicle;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ReleaseVehicleReq {
    @ApiModelProperty(value = "차량 idx", example = "1", required = true)
    @NotNull(message = "차량인덱스를 입력하세요.")
    private Long vehicleIdx;
}
