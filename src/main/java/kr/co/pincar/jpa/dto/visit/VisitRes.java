package kr.co.pincar.jpa.dto.visit;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@AllArgsConstructor
@Getter
@Builder
public class VisitRes {
    @ApiModelProperty(value = "방문 상담 idx", example = "1", required = true)
    private Long id;

    @ApiModelProperty(value = "접수일자", example = "2021-12-06 16:31:03", required = true)
    private String createdAt;

    @ApiModelProperty(value = "이름", example = "홍길동", required = true)
    private String customerName;

    @ApiModelProperty(value = "전화번호", example = "010-1234-1234", required = true)
    private String customerPhone;

    @ApiModelProperty(value = "주소", example = "서울특별시 서초구 서초중앙로 38", required = true)
    private String address;

    @ApiModelProperty(value = "개인정보취급방침 동의 유무", example = "false", required = true)
    private String personalInfoCheck;

    @ApiModelProperty(value = "마케팅 동의 유무", example = "false", required = true)
    private String marketingInfoCheck;

    @ApiModelProperty(value = "배분 유무", example = "true", required = true)
    private String divisionComplete;

    @ApiModelProperty(value = "배분 일자", example = "2021-12-06", required = true)
    private String divisionAt;
}
