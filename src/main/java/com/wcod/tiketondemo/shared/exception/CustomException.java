package com.wcod.tiketondemo.shared.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException {
    private final String message;
    private final HttpStatus httpStatus;
}

