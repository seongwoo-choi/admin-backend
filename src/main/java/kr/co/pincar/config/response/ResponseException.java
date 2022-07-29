package kr.co.pincar.config.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseException extends Exception {
    private ResponseStatus status;
}