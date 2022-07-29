package kr.co.pincar.jpa.dto.releaseVehicle;

import io.swagger.annotations.ApiModelProperty;
import kr.co.pincar.jpa.entity.enums.DepositType;
import kr.co.pincar.jpa.entity.enums.MileageType;
import kr.co.pincar.jpa.entity.enums.PeriodType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class ReleaseVehicleRes {
    @ApiModelProperty(value = "즉시출고 차량 idx", example = "1", required = true)
    private Long releaseVehicleIdx;

    @ApiModelProperty(value = "차량 idx", example = "1", required = true)
    private Long vehicleIdx;

    @ApiModelProperty(value = "차량 이미지", example = "https://", required = true)
    private String vehicleImage;

    @ApiModelProperty(value = "차량가", example = "1000000", required = true)
    private int vehiclePrice;

    @ApiModelProperty(value = "차량명", example = "벤츠", required = true)
    private String vehicleName;

    @ApiModelProperty(value = "기간", example = "48개월", required = true)
    private String periodType;

    @ApiModelProperty(value = "보증금", example = "무보증", required = true)
    private String depositType;

    @ApiModelProperty(value = "렌탈료", example = "533000", required = true)
    private int rentalFee;

    @ApiModelProperty(value = "주행거리", example = "10000km", required = true)
    private String mileageType;

    @ApiModelProperty(value = "출고상태", example = "CLOSED", required = true)
    private String releaseStatus;
}
