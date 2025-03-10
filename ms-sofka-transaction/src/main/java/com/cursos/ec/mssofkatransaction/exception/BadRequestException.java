package com.cursos.ec.mssofkatransaction.exception;

public class BadRequestException extends GenericException {

    private static final long serialVersionUID = 1L;

    public BadRequestException(String detail) {
        super(detail);
    }
}
