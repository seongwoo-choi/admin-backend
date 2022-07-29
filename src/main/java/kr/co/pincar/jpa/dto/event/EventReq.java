package kr.co.pincar.jpa.dto.event;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EventReq {
    @ApiModelProperty(value = "제목", example = "이벤트 제목",required = true)
    @NotBlank(message = "제목을 입력하세요.")
    private String title;

    @ApiModelProperty(value = "내용", example = "이벤트 내용입니다.",required = true)
    @NotBlank(message = "내용을 입력하세요.")
    private String content;

    @ApiModelProperty(value = "썸네일", example = "https://",required = true)
    @NotBlank(message = "썸네일을 입력하세요.")
    private String mainImagePath;

    @ApiModelProperty(value = "내용 이미지", example = "https://",required = true)
    @NotBlank(message = "내용 이미지를 입력하세요.")
    private String detailImagePath;

    @ApiModelProperty(value = "시작일자", example = "2021-12-01",required = true)
    @NotBlank(message = "시작일자를 입력하세요.")
    @Pattern(regexp = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$", message = "yyyy-MM-dd 형식으로 시작일자를 입력하세요.")
    private String startAt;

    @ApiModelProperty(value = "종료일자", example = "2021-12-31",required = true)
    @NotBlank(message = "종료일자를 입력하세요.")
    @Pattern(regexp = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$", message = "yyyy-MM-dd 형식으로 종료일자를 입력하세요.")
    private String endAt;

    @ApiModelProperty(value = "링크", example = "https://",required = true)
    @NotBlank(message = "링크를 입력하세요.")
    private String link;

    @ApiModelProperty(value = "이벤트 상태 타입", example = "STAND_BY",required = true)
    @NotBlank(message = "이벤트 상태[STAND_BY, PROCEEDING]를 입력하세요.")
    private String eventStatus;

    @ApiModelProperty(value = "저장 타입", example = "SAVE",required = true)
    @NotBlank(message = "이벤트 저장 타입[SAVE, TEMPORARY]를 입력하세요.")
    private String saveType;
}
