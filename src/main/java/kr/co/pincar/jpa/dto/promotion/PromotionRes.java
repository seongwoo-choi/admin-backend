package kr.co.pincar.jpa.dto.promotion;

import io.swagger.annotations.ApiModelProperty;
import kr.co.pincar.jpa.entity.enums.DepositType;
import kr.co.pincar.jpa.entity.enums.MileageType;
import kr.co.pincar.jpa.entity.enums.PeriodType;
import kr.co.pincar.jpa.entity.newEntity.OwnProductList;
import kr.co.pincar.jpa.entity.newEntity.PromotionProductList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class PromotionRes {
    @ApiModelProperty(value = "프로모션 id", example = "1", required = true)
    private Long id;

    @ApiModelProperty(value = "제목", example = "프로모션 제목", required = true)
    private String title;

    @ApiModelProperty(value = "내용", example = "프로모 내용입니다.", required = true)
    private String content;

    @ApiModelProperty(value = "시작일자", example = "2021-12-01", required = true)
    private String start_at;

    @ApiModelProperty(value = "종료일자", example = "2021-12-31", required = true)
    private String end_at;

    @ApiModelProperty(value = "내용 이미지", example = "https://", required = true)
    private String detail_image;

    @ApiModelProperty(value = "차량 정보", example = "차량 정보", required = true)
    private List<PromotionProductRes> promotionProductRes;

    @ApiModelProperty(value = "저장타입", example = "SAVE", required = true)
    private String saveType;
}
