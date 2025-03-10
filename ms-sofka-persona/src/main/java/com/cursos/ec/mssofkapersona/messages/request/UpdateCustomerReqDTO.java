package com.cursos.ec.mssofkapersona.messages.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class UpdateCustomerReqDTO implements Serializable {

    @NotBlank(message = "Field is required")
    private String fullName;

    @NotNull(message = "Field is required")
    private Integer age;

    @NotBlank(message = "Field is required")
    private String gender;

    @NotBlank(message = "Field is required")
    private String address;

    @NotBlank(message = "Field is required")
    private String phone;

}
