package kr.co.pincar.jpa.dto.vehicle;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class VehicleReq {
    @ApiModelProperty(value = "차량 이미지", example = "https://", required = true)
    @NotBlank(message = "차량 이미지를 입력하세요.")
    private String vehicleImage;

    @ApiModelProperty(value = "차량가", example = "1000000", required = true)
    @NotNull(message = "차량가를 입력하세요.")
    private Integer vehiclePrice;

    @ApiModelProperty(value = "차량명", example = "벤츠", required = true)
    @NotBlank(message = "차량명을 입력하세요.")
    private String vehicleName;

    @ApiModelProperty(value = "기간", example = "48개월", required = true)
    @NotBlank(message = "기간을 입력하세요.")
    private String periodType;

    @ApiModelProperty(value = "보증금", example = "무보증", required = true)
    @NotBlank(message = "보증금을 입력하세요.")
    private String depositType;

    @ApiModelProperty(value = "렌탈료", example = "533000", required = true)
    @NotNull(message = "렌탈료를 입력하세요.")
    private Integer rentalFee;

    @ApiModelProperty(value = "주행거리", example = "10000", required = true)
    @NotBlank(message = "주행거리를 입력하세요.")
    private String mileageType;

    @ApiModelProperty(value = "차량 타입", example = "SEDAN", required = true)
    @NotBlank(message = "차량 타입을 입력하세요.")
    private String vehicleType;
}
