package com.cursos.ec.mssofkatransaction.controller;

import com.cursos.ec.mssofkatransaction.exception.GenericException;
import com.cursos.ec.mssofkatransaction.message.request.GenericReqDTO;
import com.cursos.ec.mssofkatransaction.message.request.TransactionReqDTO;
import com.cursos.ec.mssofkatransaction.message.response.GenericRespDTO;
import com.cursos.ec.mssofkatransaction.service.ITransactionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movimientos")
@RequiredArgsConstructor
@Tag(name = "Transactions", description = "Transaction management system")
public class TransactionController {

    private final ITransactionService transactionService;

    @PostMapping
    public GenericRespDTO<String> createTransaction(
            @Valid @RequestBody GenericReqDTO<TransactionReqDTO> request) throws GenericException {
        return transactionService.saveTransaction(request);

    }
}
