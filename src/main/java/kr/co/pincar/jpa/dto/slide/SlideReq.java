package kr.co.pincar.jpa.dto.slide;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SlideReq {

    @ApiModelProperty(value = "제목", example = "슬라이드 제목", required = true)
    @NotBlank(message = "제목을 입력하세요.")
    private String title;

    @ApiModelProperty(value = "내용", example = "슬라이드 내용입니다.", required = true)
    @NotBlank(message = "내용을 입력하세요.")
    private String content;

    @ApiModelProperty(value = "링크", example = "https://", required = true)
    @NotBlank(message = "링크를 입력하세요.")
    private String link;

    @ApiModelProperty(value = "시작일자", example = "2021-12-01", required = true)
    @NotBlank(message = "시작일자를 입력하세요.")
    @Pattern(regexp = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$", message = "yyyy-MM-dd 형식으로 시작일자를 입력하세요.")
    private String startAt;

    @ApiModelProperty(value = "종료일자", example = "2021-12-31", required = true)
    @NotBlank(message = "종료일자를 입력하세요.")
    @Pattern(regexp = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$", message = "yyyy-MM-dd 형식으로 종료일자를 입력하세요.")
    private String endAt;

    @ApiModelProperty(value = "이미지", example = "https://", required = true)
    @NotBlank(message = "이미지를 입력하세요.")
    private String image;

    @ApiModelProperty(value = "저장 타입", example = "SAVE", required = true)
    @NotBlank(message = "저장타입을 입력하세요.")
    private String status;
}
