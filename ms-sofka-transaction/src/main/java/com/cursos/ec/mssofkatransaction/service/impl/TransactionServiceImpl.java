package com.cursos.ec.mssofkatransaction.service.impl;


import com.cursos.ec.mssofkatransaction.entity.Account;
import com.cursos.ec.mssofkatransaction.entity.Transaction;
import com.cursos.ec.mssofkatransaction.exception.BadRequestException;
import com.cursos.ec.mssofkatransaction.exception.GenericException;
import com.cursos.ec.mssofkatransaction.mapper.ITransactionMap;
import com.cursos.ec.mssofkatransaction.message.request.GenericReqDTO;
import com.cursos.ec.mssofkatransaction.message.request.TransactionReqDTO;
import com.cursos.ec.mssofkatransaction.message.response.GenericRespDTO;
import com.cursos.ec.mssofkatransaction.repository.IAccountRepository;
import com.cursos.ec.mssofkatransaction.repository.ITransactionRepository;
import com.cursos.ec.mssofkatransaction.service.ITransactionService;
import com.cursos.ec.mssofkatransaction.util.ConstantTransactionMs;
import com.cursos.ec.mssofkatransaction.util.GeneralUtil;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements ITransactionService {

    private final ITransactionRepository transactionRepository;

    private final IAccountRepository accountRepository;

    private static final Logger LOGGER = LogManager.getLogger(TransactionServiceImpl.class);


    @Override
    @Transactional
    public GenericRespDTO<String> saveTransaction(GenericReqDTO<TransactionReqDTO> reqDTO) throws GenericException {

        LOGGER.info("moveMoney: {}", reqDTO);

        var dataPayload = reqDTO.payload();
        var accountFrom = accountRepository.findByNumber(dataPayload.getAccountNumber())
                .orElseThrow(() -> new BadRequestException(String.format("Account number  %s not found", dataPayload.getAccountNumber())));

        var amountCurrent = accountFrom.getBalance();
        validExistMovementType(dataPayload);
        updateBalance(dataPayload, accountFrom);
        accountFrom.addTransaction(getTransaction(dataPayload, accountFrom, amountCurrent));
        accountFrom = accountRepository.save(accountFrom);

        LOGGER.info("Account update: {}", accountFrom.getIde());

        return GeneralUtil.buildGenericSuccessResp(accountFrom.getIde().toString(), "Transaction success");
    }

    private Transaction getTransaction(TransactionReqDTO transactionReqDTO, Account account, BigDecimal amountCurrent) {
        var transaction = ITransactionMap.INSTANCE.toEntity(transactionReqDTO);
        transaction.setBalance(amountCurrent);
        transaction.setCreatedAt(new Date());
        transaction.setBalanceAvailable(account.getBalance());
        transaction.setDescription(String.format("%s of %s", transactionReqDTO.getMovementType().toLowerCase(), transaction.getValue()));
        return transaction;
    }

    private void updateBalance(TransactionReqDTO transactionReqDTO, Account account) throws GenericException {

        switch (transactionReqDTO.getMovementType()) {
            case "DEPOSIT" -> account.setBalance(account.getBalance().add(transactionReqDTO.getValue()));
            case "WITHDRAW" -> {
                validAmountWithdraw(account.getBalance(), transactionReqDTO.getValue());
                account.setBalance(account.getBalance().subtract(transactionReqDTO.getValue()));
            }
        }
    }

    private void validAmountWithdraw(BigDecimal currentBalance, BigDecimal newValance) throws GenericException {

        if (newValance.compareTo(currentBalance) > 0) {
            throw new BadRequestException("The account does not have enough balance");
        }
    }

    private void validExistMovementType(TransactionReqDTO transactionReqDTO) throws GenericException {

        if (!ConstantTransactionMs.OPERATION_TYPE.containsKey(transactionReqDTO.getMovementType())) {
            String typeOperationString = String.join(", ", ConstantTransactionMs.OPERATION_TYPE.keySet());

            throw new BadRequestException(String.format("Type operation %s not found, valid types are: %s",
                    transactionReqDTO.getMovementType(),
                    typeOperationString));
        }


        if (transactionReqDTO.getValue().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Value must be greater than 0");
        }


    }

}
