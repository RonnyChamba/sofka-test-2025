package com.cursos.ec.mssofkapersona.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends GenericException {

    private static final long serialVersionUID = 1L;

    public NotFoundException(String detail) {
        super(detail, HttpStatus.NOT_FOUND.value());
    }
}
