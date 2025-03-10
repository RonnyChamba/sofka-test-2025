package com.cursos.ec.mssofkapersona.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.util.Objects;

@Getter
public class GenericException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;
    private  Integer errorCode = HttpStatus.BAD_REQUEST.value();

    public GenericException(String detail, Integer errorCode) {
        super(detail);
        if (Objects.nonNull(errorCode)) {
            this.errorCode = errorCode;
        }

    }

    public GenericException(String detail) {
        super(detail);

    }

    public GenericException(String detail, Exception ex) {
        super(detail, ex);

    }
}
