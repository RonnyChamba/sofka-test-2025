package com.cursos.ec.mssofkatransaction.mapper;

import com.cursos.ec.mssofkatransaction.entity.Transaction;
import com.cursos.ec.mssofkatransaction.message.request.TransactionReqDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ITransactionMap {
    ITransactionMap INSTANCE = Mappers.getMapper(ITransactionMap.class);

    Transaction toEntity(TransactionReqDTO transactionReqDTO);
}
