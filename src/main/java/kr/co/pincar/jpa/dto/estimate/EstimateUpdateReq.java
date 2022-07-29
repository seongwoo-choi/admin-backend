package kr.co.pincar.jpa.dto.estimate;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EstimateUpdateReq {
    @ApiModelProperty(value = "이름", example = "홍길동",required = true)
    @NotBlank(message = "이름을 입력하세요.")
    private String customerName;

    @ApiModelProperty(value = "전화번호", example = "010-1234-1234",required = true)
    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "올바른 형식으로 전화번호를 입력하세요.")
    @NotBlank(message = "전화번호를 입력하세요.")
    private String customerPhone;

    @ApiModelProperty(value = "구매 타입", example = "RENT")
    private String purchaseType;

    @ApiModelProperty(value = "상담 가능 시간", example = "9시")
    private String contTime;

    @ApiModelProperty(value = "상품", example = "SUV")
    private String locationType;

    @ApiModelProperty(value = "문의 내용", example = "투싼 차량 문의드립니다.")
    private String content;

    @ApiModelProperty(value = "상담 방식", example = "KAKAO")
    private String consultType;

    @ApiModelProperty(value = "개인정보취급방침 동의 유무", example = "false")
    private Boolean personalInfoCheck;

    @ApiModelProperty(value = "마케팅 동의 유무", example = "false")
    private Boolean marketingInfoCheck;

    @ApiModelProperty(value = "배분 유무", example = "false")
    private Boolean division;
}
