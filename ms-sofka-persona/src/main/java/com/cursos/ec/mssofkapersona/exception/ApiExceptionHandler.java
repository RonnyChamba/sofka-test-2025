package com.cursos.ec.mssofkapersona.exception;

import com.cursos.ec.mssofkapersona.messages.response.GenericRespDTO;
import com.cursos.ec.mssofkapersona.util.ConstantPersonaMsTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    private static final Logger LOGGER = LogManager.getLogger(ApiExceptionHandler.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({GenericException.class})
    public GenericRespDTO<String> genericException(GenericException exception) {
        return createResponse(exception);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NotFoundException.class})
    public GenericRespDTO<String> NotFoundException(GenericException exception) {
        return createResponse(exception);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public GenericRespDTO<String> serverException(Exception exception) {
        LOGGER.error("Catch error:", exception);

        return GenericRespDTO.<String>
                        builder()
                .status(ConstantPersonaMsTest.STATUS_ERROR)
                .message(exception.getMessage())
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public GenericRespDTO<Map<String, String>> httpRequestNotReadable(MethodArgumentNotValidException exception) {

        LOGGER.error("Invalid field:", exception);

        var errors = new HashMap<String, String>();
        exception.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return GenericRespDTO.<Map<String, String>>
                        builder()
                .status(ConstantPersonaMsTest.STATUS_ERROR)
                .data(errors)
                .message("Invalid Field")
                .code(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    private GenericRespDTO<String> createResponse(GenericException exception) {

        LOGGER.error("Catch error:", exception);

        return GenericRespDTO.<String>
                        builder()
                .status(ConstantPersonaMsTest.STATUS_ERROR)
                .message(exception.getMessage())
                .code(exception.getErrorCode())
                .build();
    }
}
