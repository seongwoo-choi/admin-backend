package kr.co.pincar.jpa.dto.kakao;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class KakaoUpdateReq {
    @ApiModelProperty(value = "카카오톡 아이디", example = "kim12323", required = true)
    @NotBlank(message = "카카오톡 아이디를 입력하세요.")
    private String kakaoId;

    @ApiModelProperty(value = "문의차량", example = "벤츠", required = true)
    @NotBlank(message = "문의차량을 입력하세요.")
    private String carName;
}
