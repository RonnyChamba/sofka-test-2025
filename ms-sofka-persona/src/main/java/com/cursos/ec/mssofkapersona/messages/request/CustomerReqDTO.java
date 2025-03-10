package com.cursos.ec.mssofkapersona.messages.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class CustomerReqDTO extends PersonReqDTO {

    @NotBlank(message = "Field is required")
    private String password;

    @NotBlank(message = "Field is required")
    private String status;
}
