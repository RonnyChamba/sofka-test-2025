package com.cursos.ec.mssofkatransaction.service;


import com.cursos.ec.mssofkatransaction.exception.GenericException;
import com.cursos.ec.mssofkatransaction.exception.GenericExceptionUncheked;
import com.cursos.ec.mssofkatransaction.message.request.AccountReqDTO;
import com.cursos.ec.mssofkatransaction.message.request.GenericReqDTO;
import com.cursos.ec.mssofkatransaction.message.request.UpdateAccountReqDTO;
import com.cursos.ec.mssofkatransaction.message.response.AccountRespDTO;
import com.cursos.ec.mssofkatransaction.message.response.GenericRespDTO;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IAccountService {

    CompletableFuture<GenericRespDTO<String>> createAccount(GenericReqDTO<AccountReqDTO> reqDTO) throws GenericExceptionUncheked;

    GenericRespDTO<List<AccountRespDTO>> listAccount() throws GenericException;

    GenericRespDTO<String> updateAccount(Integer id, GenericReqDTO<UpdateAccountReqDTO> accountReqDTO) throws GenericException;

    GenericRespDTO<String> deleteAccount(Integer id) throws GenericException;
}
