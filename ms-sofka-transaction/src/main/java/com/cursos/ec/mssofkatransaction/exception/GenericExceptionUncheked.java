package com.cursos.ec.mssofkatransaction.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.util.Objects;

@Getter
public class GenericExceptionUncheked extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;
    private  Integer errorCode = HttpStatus.BAD_REQUEST.value();

    public GenericExceptionUncheked(String detail, Integer errorCode) {
        super(detail);
        if (Objects.nonNull(errorCode)) {
            this.errorCode = errorCode;
        }

    }

    public GenericExceptionUncheked(String detail) {
        super(detail);

    }

    public GenericExceptionUncheked(String detail, Exception ex) {
        super(detail, ex);

    }
}
