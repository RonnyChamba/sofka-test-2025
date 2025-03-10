package com.cursos.ec.mssofkatransaction.service.impl;

import com.cursos.ec.mssofkatransaction.entity.Account;
import com.cursos.ec.mssofkatransaction.entity.Transaction;
import com.cursos.ec.mssofkatransaction.exception.GenericExceptionUncheked;
import com.cursos.ec.mssofkatransaction.mapper.IAccountMap;
import com.cursos.ec.mssofkatransaction.message.response.CustomerRespDTO;
import com.cursos.ec.mssofkatransaction.message.response.GenericRespDTO;
import com.cursos.ec.mssofkatransaction.message.response.ReportRespDTO;
import com.cursos.ec.mssofkatransaction.repository.IAccountRepository;
import com.cursos.ec.mssofkatransaction.repository.ITransactionRepository;
import com.cursos.ec.mssofkatransaction.service.IPersonFeignClient;
import com.cursos.ec.mssofkatransaction.service.IReportService;
import com.cursos.ec.mssofkatransaction.util.DateFormatEnum;
import com.cursos.ec.mssofkatransaction.util.DateUtil;
import com.cursos.ec.mssofkatransaction.util.GeneralUtil;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements IReportService {

    private static final Logger LOGGER = LogManager.getLogger(ReportServiceImpl.class);
    private final IPersonFeignClient personFeignClient;
    private final ITransactionRepository transactionRepository;
    private final IAccountRepository accountRepository;


    @Override
    public CompletableFuture<GenericRespDTO<List<ReportRespDTO>>> generateReport(Integer customerIde, String dateStart, String dateEnd) {

        LOGGER.info("Req generateReport: {}, {}, {}", customerIde, dateEnd, dateEnd);

        return CompletableFuture.supplyAsync(() -> fetchCustomer(customerIde))
                .thenApply(item -> processReport(item, dateStart, dateEnd))
                .exceptionally(ex -> {

                    LOGGER.error(ex.getCause());
                    throw new GenericExceptionUncheked("Hola como estas");
                });
    }

    private GenericRespDTO<CustomerRespDTO> fetchCustomer(Integer customerIde) {
        return personFeignClient.findCustomerByIde(customerIde);
    }

    private GenericRespDTO<List<ReportRespDTO>> processReport(GenericRespDTO<CustomerRespDTO> customerRespDTO, String dateStartParam, String dateEndParam) {

        LOGGER.info("Customer report: {}", customerRespDTO);
        var customer = customerRespDTO.data();

        var dateStart = DateUtil.convertToDate(dateStartParam, DateFormatEnum.DATE_FORMAT);
        var dateEnd = DateUtil.convertToDate(dateEndParam, DateFormatEnum.DATE_FORMAT);

        var accountsByCustomer = accountRepository.findAllByCustomerId(customer.getPersonId())
                .stream()
                .map(item -> mapReport(item, dateStart, dateEnd))
                .peek(item -> item.setFullName(customer.getFullName()))
                .toList();

        LOGGER.info("Account size: {}", accountsByCustomer.size());

        return GeneralUtil.buildGenericSuccessResp(accountsByCustomer, "report success");

    }

    private ReportRespDTO mapReport(Account account, Date startDate, Date endDate) {

        var transcion = getTransactionByRange(startDate, endDate, account.getIde());

        LOGGER.info("transacion: {}", transcion.size());
        account.setTransactions(transcion);
        var reportItem = IAccountMap.INSTANCE.toReportDTO(account);
        return reportItem;

    }

    private List<Transaction> getTransactionByRange(Date startDate, Date endDate, Integer accountId) {

        return transactionRepository.findAllSomeFilters(
                startDate,
                endDate,
                accountId
        );
    }

}
