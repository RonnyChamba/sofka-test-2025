package com.cursos.ec.mssofkapersona.messages.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class PersonReqDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Field is required")
    private String fullName;

    @NotNull(message = "Field is required")
    @Positive(message = "Value is incorrect")
    private Integer age;

    @NotBlank(message = "Field is required")
    private String gender;

    @NotBlank(message = "Field is required")
    private String identification;

    @NotBlank(message = "Field is required")
    private String address;

    @NotBlank(message = "Field is required")
    private String phone;
}