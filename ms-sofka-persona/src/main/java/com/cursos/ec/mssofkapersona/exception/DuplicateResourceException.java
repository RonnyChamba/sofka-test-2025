package com.cursos.ec.mssofkapersona.exception;

public class DuplicateResourceException extends GenericException {

    private static final long serialVersionUID = 1L;

    public DuplicateResourceException(String detail) {
        super(detail);
    }
}
