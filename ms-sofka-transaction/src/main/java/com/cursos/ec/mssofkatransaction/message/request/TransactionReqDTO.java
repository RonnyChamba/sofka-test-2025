package com.cursos.ec.mssofkatransaction.message.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionReqDTO {

    @NotBlank(message = "accountNumber is required")
    private String accountNumber;

    @NotNull(message = "value is required")
    @Positive(message = "Field is incorrect")
    private BigDecimal value;

    @NotBlank(message = "movementType is required, it should be 'DEPOSIT' or 'WITHDRAWAL'")
    private String movementType;
}
