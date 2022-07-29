package kr.co.pincar.config.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static kr.co.pincar.config.response.ResponseStatus.OK;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"message", "code", "result"})
public class ResponseEntity<T> {

    private String message;
    private int code;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    // 요청에 성공한 경우
    public ResponseEntity(T result) {
        this.message = OK.getMessage();
        this.code = OK.getCode();
        this.result = result;
    }

    // 요청에 실패한 경우
    public ResponseEntity(ResponseStatus status) {
        this.message = status.getMessage();
        this.code = status.getCode();
    }

}