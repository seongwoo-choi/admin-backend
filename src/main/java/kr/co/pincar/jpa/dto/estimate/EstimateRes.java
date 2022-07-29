package kr.co.pincar.jpa.dto.estimate;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@AllArgsConstructor
@Getter
@Builder
public class EstimateRes {
    @ApiModelProperty(value = "견적문의 인덱스", example = "1", required = true)
    private Long id;

    @ApiModelProperty(value = "접수일자", example = "2021-12-06 16:31:03", required = true)
    private String createdAt;

    @ApiModelProperty(value = "이름", example = "홍길동", required = true)
    private String customerName;

    @ApiModelProperty(value = "전화번호", example = "010-1234-1234", required = true)
    private String customerPhone;

    @ApiModelProperty(value = "구매 타입", example = "RENT", required = true)
    private String purchaseType;

    @ApiModelProperty(value = "상담 가능 시간", example = "9시", required = true)
    private String contTime;

    @ApiModelProperty(value = "차량 타입", example = "SUV", required = true)
    private String locationType;

    @ApiModelProperty(value = "문의 내용", example = "투싼 차량 문의드립니다.", required = true)
    private String content;

    @ApiModelProperty(value = "상담 방식", example = "KAKAO", required = true)
    private String consultType;

    @ApiModelProperty(value = "개인정보취급방침 동의 유무", example = "동의", required = true)
    private String personalInfoCheck;

    @ApiModelProperty(value = "마케팅 동의 유무", example = "미동의", required = true)
    private String marketingInfoCheck;

    @ApiModelProperty(value = "배분 유무", example = "완료", required = true)
    private String divisionComplete;

    @ApiModelProperty(value = "배분 일자", example = "2021-12-06", required = true)
    private String divisionAt;
}
