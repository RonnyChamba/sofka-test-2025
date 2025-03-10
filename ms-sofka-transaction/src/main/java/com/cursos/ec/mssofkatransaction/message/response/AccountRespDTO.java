package com.cursos.ec.mssofkatransaction.message.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRespDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private Integer ide;
    private String number;
    private String type;
    private Double balance;
    private String createdAt;
    private Integer customerId;
    private List<TransactionRespDTO> transactions;
}
