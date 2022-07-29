package kr.co.pincar.jpa.dto.ownProduct;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModelProperty;
import kr.co.pincar.jpa.entity.enums.PeriodType;
import kr.co.pincar.jpa.entity.enums.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class OwnProductListRes {
    @ApiModelProperty(value = "보유 상품 리스트 id", example = "1", required = true)
    private Long ownProductListId;

    @ApiModelProperty(value = "차량 타입", example = "SEDAN", required = true)
    private String carType;

    @ApiModelProperty(value = "차량명", example = "벤츠차", required = true)
    private String productName;

    @ApiModelProperty(value = "차량가", example = "10000", required = true)
    private int productPrice;

    @ApiModelProperty(value = "차량 이미지", example = "https", required = true)
    private String productImage;

    @ApiModelProperty(value = "계약 기간", example = "48개월", required = true)
    private String contractPeriod;

    @ApiModelProperty(value = "연간 주행 거리", example = "2만km", required = true)
    private String yearMileage;

    @ApiModelProperty(value = "보증금", example = "10%", required = true)
    private String deposit;

    @ApiModelProperty(value = "선납금", example = "10%", required = true)
    private String prepayment;

    @QueryProjection // TeamPincarPick 조회용으로 쓴거라 prepayment 뺄 수도 있음
    public OwnProductListRes(Long ownProductListId, VehicleType vehicleType, String productName, int productPrice,
                             PeriodType periodType, String yearMileage, String deposit, String prepayment) {
        this.ownProductListId = ownProductListId;
        this.carType = vehicleType.name();
        this.productName = productName;
        this.productPrice = productPrice;
        this.contractPeriod = periodType.getPeriod();
        this.yearMileage = yearMileage;
        this.deposit = deposit;
        this.prepayment = prepayment;
    }
}
