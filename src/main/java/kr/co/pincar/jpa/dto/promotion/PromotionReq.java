package kr.co.pincar.jpa.dto.promotion;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PromotionReq {
    @ApiModelProperty(value = "차량 id", example = "1", required = true)
    @NotNull(message = "차량인덱스를 입력하세요.")
    private Long id;

    @ApiModelProperty(value = "제목", example = "프로모션 제목", required = true)
    @NotBlank(message = "제목을 입력하세요.")
    private String title;

    @ApiModelProperty(value = "내용", example = "프로모션 내용입니다.", required = true)
    @NotBlank(message = "내용을 입력하세요.")
    private String content;

    @ApiModelProperty(value = "시작일자", example = "2021-12-01", required = true)
    @NotBlank(message = "시작일자를 입력하세요.")
    @Pattern(regexp = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$", message = "yyyy-MM-dd 형식으로 시작일자를 입력하세요.")
    private String start_at;

    @ApiModelProperty(value = "종료일자", example = "2021-12-31", required = true)
    @NotBlank(message = "종료일자를 입력하세요.")
    @Pattern(regexp = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])", message = "yyyy-MM-dd 형식으로 종료일자를 입력하세요.")
    private String end_at;

    @ApiModelProperty(value = "저장 유형", example = "SAVE / TEMPORARY", required = true)
    @NotBlank(message = "저장 유형을 입력하세요.")
    private String saveType;

    @ApiModelProperty(value = "썸네일", example = "https://", required = true)
    @NotBlank(message = "썸네일을 입력하세요.")
    private String main_image;

    @ApiModelProperty(value = "내용 이미지", example = "https://", required = true)
    @NotBlank(message = "내용 이미지를 입력하세요.")
    private String detail_image;

    @ApiModelProperty(value = "보유 차량 id 리스트", example = "[1, 2, 3]", required = true)
    @NotNull(message = "보유 차량 id 를 입력하세요.")
    private List<Long> owner_product_id;

}
