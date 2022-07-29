package kr.co.pincar.jpa.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JoinRes {
    @ApiModelProperty(value = "아이디 인덱스", required = true, example = "1")
    private Long idx;

    @ApiModelProperty(value = "이메일", required = true, example = "test1234@gmail.com")
    private String email;
}
