package com.cursos.ec.mssofkatransaction.message.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GenericReqDTO<T>(

        @NotBlank(message = "Field is required")
        String origin,

        @NotBlank(message = "Field is required")
        String userRequest,

        @NotBlank(message = "Field is required")
        String ipRequest,

        @NotNull(message = "Field is required")
        @Valid
        T payload
) {
}
