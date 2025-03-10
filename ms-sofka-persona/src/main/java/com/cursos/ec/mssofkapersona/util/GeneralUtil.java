package com.cursos.ec.mssofkapersona.util;

import com.cursos.ec.mssofkapersona.messages.response.GenericRespDTO;
import org.springframework.http.HttpStatus;

import java.util.Objects;

public class GeneralUtil {

    public static <T> GenericRespDTO<T> buildGenericSuccessResp(T data, String message) {
        return GenericRespDTO.<T>
                        builder()
                .status(ConstantPersonaMsTest.STATUS_OK)
                .code(HttpStatus.OK.value())
                .data(data)
                .message((Objects.isNull(message) ? "Peticion procesada correctamente" : message))
                .build();
    }
}
