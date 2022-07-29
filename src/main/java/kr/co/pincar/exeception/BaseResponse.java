package kr.co.pincar.exeception;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import static kr.co.pincar.exeception.BaseResponseCode.OK;

@Getter
public class BaseResponse<T> {

    @ApiModelProperty(example = "OK")
    private final String code; // 상태 코드 메시지
    @ApiModelProperty(example = "요청 성공하였습니다.")
    private final String message; // 에러 설명
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    // 요청 실패
    public BaseResponse(BaseResponseCode baseResponseCode) {
        this.code = baseResponseCode.getStatus().name();
        this.message = baseResponseCode.getMessage();
    }


    // 요청에 성공한 경우
    public BaseResponse(T result) {
        this.code = OK.getStatus().name();
        this.message = OK.getMessage();
        this.result = result;
    }

    public BaseResponse(String error, String message) {
        this.code = error;
        this.message = message;
    }
}
