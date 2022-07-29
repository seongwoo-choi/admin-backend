package kr.co.pincar.jpa.dto.teamPincarPick;

import com.querydsl.core.annotations.QueryProjection;
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
public class TeamPincarPickRes {
    @ApiModelProperty(value = "팀핀카픽 id", example = "1", required = true)
    private Long teamPincarPickId;

    @ApiModelProperty(value = "사용되는 팀핀카픽 정렬순서", example = "1", required = true)
    private Integer displayOrder;

    @ApiModelProperty(value = "팀핀카픽 idx", example = "1", required = true)
    private Long ownProductId;

    @ApiModelProperty(value = "차량 이미지", example = "https://", required = true) // product의 이미지
    private String vehicleImage;

    @ApiModelProperty(value = "차량가", example = "1000000", required = true)
    private int productPrice;

    @ApiModelProperty(value = "주행거리", example = "10000km", required = true)
    private String yearMileage;

    @ApiModelProperty(value = "기간", example = "48개월", required = true)
    private String periodType;

    @ApiModelProperty(value = "보증금", example = "무보증", required = true)
    private String depositType;

    @ApiModelProperty(value = "렌탈료", example = "533000", required = true)
    private int rentalFee;

    @QueryProjection
    public TeamPincarPickRes(Long teamPincarPickId, Integer displayOrder, Long ownProductId, String vehicleImage,
                             int productPrice, String yearMileage, PeriodType periodType, String depositType, int rentalFee) {
        this.teamPincarPickId = teamPincarPickId;
        this.displayOrder = displayOrder;
        this.ownProductId = ownProductId;
        this.vehicleImage = vehicleImage;
        this.productPrice = productPrice;
        this.yearMileage = yearMileage;
        this.periodType = periodType.name();
        this.depositType = depositType;
        this.rentalFee = rentalFee;
    }
}
