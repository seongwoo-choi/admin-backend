package kr.co.pincar.jpa.dto.event;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.joda.time.DateTime;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@Getter
@Builder
public class EventRes {
    @ApiModelProperty(value = "이벤트 idx", example = "1",required = true)
    private Long eventId;

    @ApiModelProperty(value = "제목", example = "이벤트 제목",required = true)
    private String title;

    @ApiModelProperty(value = "내용", example = "이벤트 내용",required = true)
    private String content;

    @ApiModelProperty(value = "시작일자", example = "2021-12-01",required = true)
    private String startAt;

    @ApiModelProperty(value = "종료일자", example = "2021-12-02",required = true)
    private String endAt;

    @ApiModelProperty(value = "링크", example = "이벤트 링크",required = true)
    private String link;

    @ApiModelProperty(value = "메인 이미지", example = "https://",required = true)
    private String mainImage;

    @ApiModelProperty(value = "상세 이미지", example = "https://",required = true)
    private String detailImage;

    @QueryProjection
    public EventRes(Long eventId, String title, String content, LocalDate startAt, LocalDate endAt, String link, String mainImage, String detailImage) {
        this.eventId = eventId;
        this.title = title;
        this.content = content;
        this.startAt = startAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.endAt = endAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.link = link;
        this.mainImage = mainImage;
        this.detailImage = detailImage;
    }
}
