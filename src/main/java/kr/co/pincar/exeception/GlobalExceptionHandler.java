package kr.co.pincar.exeception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.UnexpectedTypeException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    /*
     * Developer Custom Exception
     */
    @ExceptionHandler(BaseException.class)
    protected ResponseEntity<BaseResponse> handleBaseException(final BaseException e) {
        return ResponseEntity
                .status(e.getBaseResponseCode().getStatus().value())
                .body(new BaseResponse(e.getBaseResponseCode()));
    }

    /**
     * enum type 일치하지 않아 binding 못할 경우 발생
     * 주로 @RequestParam enum으로 binding 못했을 경우 발생
     */
    // date
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<BaseResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST.value())
                .body(new BaseResponse(BaseResponseCode.INVALID_DATE));
    }

    /**
     * javax.validation.Valid or @Validated 으로 binding error 발생시 발생한다.
     * HttpMessageConverter 에서 등록한 HttpMessageConverter binding 못할경우 발생
     * 주로 @RequestBody, @RequestPart 어노테이션에서 발생
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<BaseResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder builder = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            builder.append(fieldError.getDefaultMessage());
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST.value())
                .body(new BaseResponse(HttpStatus.BAD_REQUEST.name(), builder.toString()));
    }

    // Path variable 입력 안할 때
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<BaseResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST.value())
                .body(new BaseResponse(BaseResponseCode.EMPTY_PATH_VARIABLE));
    }

    /*
     * HTTP 405 Exception
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<BaseResponse> handleHttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST.value())
                .body(new BaseResponse(BaseResponseCode.METHOD_NOT_ALLOWED));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<BaseResponse> handleMissingServletRequestParameterException(final MissingServletRequestParameterException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST.value())
                .body(new BaseResponse(BaseResponseCode.EMPTY_DATE));
    }

    @ExceptionHandler(UnexpectedTypeException.class)
    protected ResponseEntity<BaseResponse> handleUnexpectedTypeException(final UnexpectedTypeException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST.value())
                .body(new BaseResponse(BaseResponseCode.INVALID_ENUM_TYPE));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    protected ResponseEntity<BaseResponse> handleUnexpectedTypeException(final UsernameNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST.value())
                .body(new BaseResponse(BaseResponseCode.INVALID_ADMIN));
    }

}
