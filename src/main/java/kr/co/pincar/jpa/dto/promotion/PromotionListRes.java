package kr.co.pincar.jpa.dto.promotion;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PromotionListRes {
    @ApiModelProperty(value = "프로모션 id", example = "1", required = true)
    private Long id;

    @ApiModelProperty(value = "제목", example = "프로모션 제목", required = true)
    private String title;

    @ApiModelProperty(value = "메인 이미지", example = "https://asd", required = true)
    private String main_image;

    @ApiModelProperty(value = "내용", example = "프로모션 내용", required = true)
    private String content;

    @ApiModelProperty(value = "시작일자", example = "2021-12-01", required = true)
    private String start_at;

    @ApiModelProperty(value = "종료일자", example = "2021-12-31", required = true)
    private String end_at;
}
