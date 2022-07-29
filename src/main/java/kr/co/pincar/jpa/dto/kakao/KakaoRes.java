package kr.co.pincar.jpa.dto.kakao;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class KakaoRes {
    @ApiModelProperty(value = "카카오톡 상담 idx", example = "1", required = true)
    private Long id;

    @ApiModelProperty(value = "접수일자", example = "2021-12-06 16:31:03", required = true)
    private String createdAt;

    @ApiModelProperty(value = "카카오톡 아이디", example = "kim12323", required = true)
    private String kakaoId;

    @ApiModelProperty(value = "문의차량", example = "벤츠", required = true)
    private String carName;

    @ApiModelProperty(value = "배분 유무", example = "완료", required = true)
    private String divisionComplete;

    @ApiModelProperty(value = "배분 일자", example = "2021-12-06", required = true)
    private String divisionAt;
}
