package com.cursos.ec.mssofkatransaction.message.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRespDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer ide;
    private BigDecimal balance;
    private BigDecimal value;
    private String movementType;
    private String description;
    private BigDecimal balanceAvailable;
    private String createdAt;
}
