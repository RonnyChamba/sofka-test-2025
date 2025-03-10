package com.cursos.ec.mssofkatransaction.controller;

import com.cursos.ec.mssofkatransaction.exception.GenericException;
import com.cursos.ec.mssofkatransaction.message.request.AccountReqDTO;
import com.cursos.ec.mssofkatransaction.message.request.GenericReqDTO;
import com.cursos.ec.mssofkatransaction.message.request.UpdateAccountReqDTO;
import com.cursos.ec.mssofkatransaction.message.response.AccountRespDTO;
import com.cursos.ec.mssofkatransaction.message.response.GenericRespDTO;
import com.cursos.ec.mssofkatransaction.service.IAccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/cuentas")
@RequiredArgsConstructor
@Tag(name = "Account", description = "Account management system")
public class AccountController {

    private final IAccountService accountService;

    @PostMapping
    public CompletableFuture<GenericRespDTO<String>> createAccount(
            @Valid @RequestBody GenericReqDTO<AccountReqDTO> reqDTO) throws GenericException {
        return accountService.createAccount(reqDTO);
    }

    @GetMapping
    public GenericRespDTO<List<AccountRespDTO>> listAccount() throws GenericException {
        return accountService.listAccount();
    }

    @PatchMapping(value = "/{id}")
    public GenericRespDTO<String> updateAccount(
            @PathVariable Integer id,
            @Valid @RequestBody GenericReqDTO<UpdateAccountReqDTO> request) throws GenericException {

        return accountService.updateAccount(id, request);
    }

    @DeleteMapping("/{id}")
    public GenericRespDTO<String> deleteAccount(@PathVariable Integer id) throws GenericException {
        return accountService.deleteAccount(id);
    }
}
