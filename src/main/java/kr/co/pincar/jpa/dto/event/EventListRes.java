package kr.co.pincar.jpa.dto.event;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class EventListRes {
    @ApiModelProperty(value = "이벤트 idx", example = "1", required = true)
    private Long eventIdx;

    @ApiModelProperty(value = "제목", example = "이벤트 제목", required = true)
    private String title;

    @ApiModelProperty(value = "내용", example = "이벤트 내용입니다.", required = true)
    private String content;

    @ApiModelProperty(value = "시작일자", example = "2021-12-01", required = true)
    private String startDate;

    @ApiModelProperty(value = "종료일자", example = "2021-12-31", required = true)
    private String endDate;

    @ApiModelProperty(value = "상태", example = "진행중", required = true)
    private String eventStatus;

    @ApiModelProperty(value = "저장상태", example = "SAVE", required = true)
    private String saveType;
}
