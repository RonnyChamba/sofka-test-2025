package com.cursos.ec.mssofkapersona.messages.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
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
