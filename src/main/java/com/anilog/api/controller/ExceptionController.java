package com.anilog.api.controller;

import com.anilog.api.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;



@Slf4j
@ControllerAdvice
public class ExceptionController {

    private ErrorResponse response;

    @ResponseStatus(HttpStatus.BAD_REQUEST)  //상태
    @ExceptionHandler(MethodArgumentNotValidException.class)  //어떤exception사용할지
    @ResponseBody //결과값쓰려고
    public ErrorResponse invalidRequestHandler(MethodArgumentNotValidException e){
            ErrorResponse response = ErrorResponse.builder()
                    .code("400")
                    .message("잘못된 요청입니다.")
                    .build();

            for(FieldError fieldError : e.getFieldErrors()){
                response.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
            }

            return response;
    }

}
