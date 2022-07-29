package kr.co.pincar.jpa.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginReq {

    @ApiModelProperty(value = "이메일", example = "test1234@gmail.com", required = true)
    @NotBlank(message = "이메일을 입력하세요.")
    private String email;

    @ApiModelProperty(value = "비밀번호", example = "password1234@", required = true)
    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;
}
