package com.cursos.ec.mssofkatransaction.message.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportRespDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String fullName;
    private String number;
    private String type;
    private BigDecimal balance;
    private List<TransactionRespDTO> transactions = new ArrayList<>();
}
