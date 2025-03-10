package com.cursos.ec.mssofkatransaction.service;


import com.cursos.ec.mssofkatransaction.exception.GenericException;
import com.cursos.ec.mssofkatransaction.message.request.GenericReqDTO;
import com.cursos.ec.mssofkatransaction.message.request.TransactionReqDTO;
import com.cursos.ec.mssofkatransaction.message.response.GenericRespDTO;

public interface ITransactionService {

    GenericRespDTO<String> saveTransaction(GenericReqDTO<TransactionReqDTO> request) throws GenericException;

}
