package com.cursos.ec.mssofkatransaction.mapper;

import com.cursos.ec.mssofkatransaction.entity.Account;
import com.cursos.ec.mssofkatransaction.message.request.AccountReqDTO;
import com.cursos.ec.mssofkatransaction.message.response.AccountRespDTO;
import com.cursos.ec.mssofkatransaction.message.response.ReportRespDTO;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface IAccountMap {
    IAccountMap INSTANCE = Mappers.getMapper(IAccountMap.class);

    Account toEntity(AccountReqDTO accountDTO);

    @Mapping(ignore = true, target = "transactions")
    @Named("toDTOSinTransaction")
    AccountRespDTO toDTOSinTransaction(Account account);

    @IterableMapping(qualifiedByName = "toDTOSinTransaction")
    List<AccountRespDTO> toDTOSinTransactions(List<Account> account);

    AccountRespDTO toDTO(Account account);

    ReportRespDTO toReportDTO(Account account);
}
