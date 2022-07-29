package kr.co.pincar.controller;

import io.swagger.annotations.*;
import kr.co.pincar.exeception.BaseResponse;
import kr.co.pincar.exeception.BaseResponseCode;
import kr.co.pincar.jpa.dto.user.UserLoginReq;
import kr.co.pincar.jpa.dto.user.UserReq;
import kr.co.pincar.jpa.dto.user.JoinRes;
import kr.co.pincar.jpa.dto.user.LoginRes;
import kr.co.pincar.jpa.entity.User;
import kr.co.pincar.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = {"회원가입, 로그인"})
@RestController
@RequiredArgsConstructor
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    // 회원가입
    @PostMapping("/join")
    @ApiOperation(value = "회원가입 API", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userReq", value = "어드민 DTO", required = true),
    })
    public BaseResponse<JoinRes> join(@RequestBody @Valid UserReq userReq) {

        // 이미 존재하는 이메일
        if (userService.existsAdmin(userReq.getEmail())) {
            return new BaseResponse<>(BaseResponseCode.EXISTENT_EMAIL);
        }

        JoinRes joinRes = userService.createAdmin(userReq);
        return new BaseResponse<>(joinRes);

    }

    // 로그인
    @PostMapping("/login")
    @ApiOperation(value = "로그인 API", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userLoginReq", value = "어드민 dto", required = true),
    })
    public BaseResponse<LoginRes> login(@RequestBody @Valid UserLoginReq userLoginReq) {

        Boolean existAdmin = userService.existsAdmin(userLoginReq.getEmail());
        if (!existAdmin) {
            return new BaseResponse<>(BaseResponseCode.NON_EXISTENT_EMAIL);
        }

        User admin = userService.readAdmin(userLoginReq.getEmail());
        if (!passwordEncoder.matches(userLoginReq.getPassword(), admin.getPassword())) {
            return new BaseResponse<>(BaseResponseCode.WRONG_PWD);
        }

        LoginRes loginRes = userService.login(userLoginReq.getEmail());

        return new BaseResponse<>(loginRes);
    }


}
