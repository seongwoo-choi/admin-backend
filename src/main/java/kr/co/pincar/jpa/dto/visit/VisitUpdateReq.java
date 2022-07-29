package kr.co.pincar.jpa.dto.visit;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class VisitUpdateReq {
    @ApiModelProperty(value = "이름", example = "홍길동", required = true)
    @NotBlank(message = "이름을 입력하세요.")
    private String customerName;

    @ApiModelProperty(value = "전화번호", example = "010-1234-1234", required = true)
    @NotBlank(message = "전화번호를 입력하세요.")
    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "올바른 형식으로 전화번호를 입력하세요.")
    private String customerPhone;

    @ApiModelProperty(value = "주소", example = "서울특별시 서초구 서초중앙로 38", required = true)
    @NotBlank(message = "주소를 입력하세요.")
    private String address;

    @ApiModelProperty(value = "개인정보취급방침 동의 유무", example = "false")
    private Boolean personalInfoCheck;

    @ApiModelProperty(value = "마케팅 동의 유무", example = "false")
    private Boolean marketingInfoCheck;

    @ApiModelProperty(value = "배분 유무", example = "true")
    private Boolean division;
}
