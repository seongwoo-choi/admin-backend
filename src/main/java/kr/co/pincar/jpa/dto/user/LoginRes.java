package kr.co.pincar.jpa.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRes {

    @ApiModelProperty(value = "아이디 인덱스", required = true, example = "1")
    private Long idx;

    @ApiModelProperty(value = "jwt 토큰", required = true, example = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEsImlhdCI6MTYzODc2OTM3Mn0.J2aNNvo7CI714PYuI_4bm3BLfpuDr_7xDxAOTGexiNc")
    private String jwtToken;
}
