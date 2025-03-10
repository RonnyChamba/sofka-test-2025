package com.cursos.ec.mssofkatransaction.message.response;

import lombok.Builder;

@Builder
public record GenericRespDTO<T>(
        Integer code,
        String status,
        String message,
        T data
) {
}