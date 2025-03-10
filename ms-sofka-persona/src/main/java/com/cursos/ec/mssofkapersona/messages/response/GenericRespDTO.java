package com.cursos.ec.mssofkapersona.messages.response;

import lombok.Builder;

@Builder
public record GenericRespDTO<T>(
        Integer code,
        String status,
        String message,
        T data
) {
}