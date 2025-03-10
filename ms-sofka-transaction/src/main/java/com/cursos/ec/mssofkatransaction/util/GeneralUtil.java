package com.cursos.ec.mssofkatransaction.util;


import com.cursos.ec.mssofkatransaction.message.response.GenericRespDTO;
import org.springframework.http.HttpStatus;

import java.util.Objects;

public class GeneralUtil {

    public static <T> GenericRespDTO<T> buildGenericSuccessResp(T data, String message) {
        return GenericRespDTO.<T>
                        builder()
                .status(ConstantTransactionMs.SUCCESS_STATUS)
                .code(HttpStatus.OK.value())
                .data(data)
                .message((Objects.isNull(message) ? "Peticion procesada correctamente" : message))
                .build();
    }
}
