package kr.co.pincar.jpa.dto.slide;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class SlideRes {
    @ApiModelProperty(value = "슬라이드 id", example = "1", required = true)
    private Long id;

    @ApiModelProperty(value = "제목", example = "슬라이드 제목", required = true)
    private String title;

    @ApiModelProperty(value = "저장 타입", example = "SAVE", required = true)
    private String status;

    @ApiModelProperty(value = "링크", example = "https://", required = true)
    private String link;

    @ApiModelProperty(value = "내용", example = "슬라이드 내용입니다.", required = true)
    private String content;

    @ApiModelProperty(value = "시작일자", example = "2021-12-01", required = true)
    private String startAt;

    @ApiModelProperty(value = "종료일자", example = "2021-12-31", required = true)
    private String endAt;

    @ApiModelProperty(value = "이미지", example = "https://", required = true)
    private String image;
}
