package com.cursos.ec.mssofkatransaction.message.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountReqDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Field is required")
    private String number;

    @NotBlank(message = "Field is required")
    private String type;

    @NotNull(message = "Field is required")
    private Double balance;

    @NotNull(message = "Field is required")
    private Integer customerId;
}
