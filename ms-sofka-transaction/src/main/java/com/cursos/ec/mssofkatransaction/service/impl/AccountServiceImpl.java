package com.cursos.ec.mssofkatransaction.service.impl;

import com.cursos.ec.mssofkatransaction.exception.BadRequestException;
import com.cursos.ec.mssofkatransaction.exception.GenericException;
import com.cursos.ec.mssofkatransaction.exception.GenericExceptionUncheked;
import com.cursos.ec.mssofkatransaction.exception.NotFoundException;
import com.cursos.ec.mssofkatransaction.mapper.IAccountMap;
import com.cursos.ec.mssofkatransaction.message.request.AccountReqDTO;
import com.cursos.ec.mssofkatransaction.message.request.GenericReqDTO;
import com.cursos.ec.mssofkatransaction.message.request.UpdateAccountReqDTO;
import com.cursos.ec.mssofkatransaction.message.response.AccountRespDTO;
import com.cursos.ec.mssofkatransaction.message.response.CustomerRespDTO;
import com.cursos.ec.mssofkatransaction.message.response.GenericRespDTO;
import com.cursos.ec.mssofkatransaction.repository.IAccountRepository;
import com.cursos.ec.mssofkatransaction.repository.ITransactionRepository;
import com.cursos.ec.mssofkatransaction.service.IAccountService;
import com.cursos.ec.mssofkatransaction.service.IPersonFeignClient;
import com.cursos.ec.mssofkatransaction.util.FileUtil;
import com.cursos.ec.mssofkatransaction.util.GeneralUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private static final Logger LOGGER = LogManager.getLogger(AccountServiceImpl.class);

    private final IAccountRepository accountRepository;
    private final ITransactionRepository transactionRepository;

    private final IPersonFeignClient personFeignClient;

    @Transactional
    @Override
    public CompletableFuture<GenericRespDTO<String>> createAccount(GenericReqDTO<AccountReqDTO> reqDTO) throws GenericExceptionUncheked {

        LOGGER.info("Req createAccount: {}", reqDTO);
        return CompletableFuture.supplyAsync(() -> fetchCustomer(reqDTO))
                .thenApply(item -> saveAccount(reqDTO))
                .exceptionally(ex -> {
                    throw manageExceptionSaveAccount(ex);
                });
    }

    private GenericRespDTO<CustomerRespDTO> fetchCustomer(GenericReqDTO<AccountReqDTO> reqDTO) {
        var dataToPersist = reqDTO.payload();
        return personFeignClient.findCustomerByIde(dataToPersist.getCustomerId());
    }


    private GenericRespDTO<String> saveAccount(GenericReqDTO<AccountReqDTO> reqDTO) {

        var dataToPersist = reqDTO.payload();

        var account = IAccountMap.INSTANCE.toEntity(dataToPersist);

        if (accountRepository.existsByNumberAndStatusRecord(account.getNumber(), "Activo")) {
            throw new GenericExceptionUncheked(String.format("Account with account number %s already exists", account.getNumber()));
        }

        account.setStatusRecord("Activo");
        var customerCreated = accountRepository.save(account);
        LOGGER.info("Account created: {}", customerCreated.getIde());

        return GeneralUtil.buildGenericSuccessResp(account.getIde().toString(), "Account created success");
    }

    private GenericExceptionUncheked manageExceptionSaveAccount(Throwable throwable) {

        if (throwable.getCause() instanceof GenericExceptionUncheked exceptionUnchecked) {
            return exceptionUnchecked;
        }
        if (throwable.getCause() instanceof FeignException feignException) {
            return handleFeignException(feignException);
        }
        return new GenericExceptionUncheked("Error al crear la cuenta");

    }


    private GenericExceptionUncheked handleFeignException(FeignException feignException) {
        if (feignException.responseBody().isPresent()) {
            try {
                byte[] byteArray = FileUtil.getByteArrayFromResponseBody(feignException);
                var genericResp = new ObjectMapper().readValue(byteArray, GenericRespDTO.class);
                return new GenericExceptionUncheked(genericResp.message(), genericResp.code());
            } catch (IOException e) {
                LOGGER.error("Error deserializando la respuesta de Feign", e);
                return new GenericExceptionUncheked(feignException.toString(), feignException.status());
            }
        }

        return new GenericExceptionUncheked("No response body in FeignException");
    }

    @Override
    @Transactional(readOnly = true)
    public GenericRespDTO<List<AccountRespDTO>> listAccount() throws GenericException {

        var accounts = accountRepository.findAllByStatusRecord("Activo");

        LOGGER.info("Size accounts: {}", accounts.size());

        var accountsDTO = IAccountMap.INSTANCE.toDTOSinTransactions(accounts);

        return GeneralUtil.buildGenericSuccessResp(accountsDTO, accountsDTO.isEmpty() ? "no results found" : "information obtained correctly");
    }

    @Override
    @Transactional
    public GenericRespDTO<String> updateAccount(Integer id, GenericReqDTO<UpdateAccountReqDTO> accountReqDTO) throws GenericException {

        LOGGER.info("Updating account: {} {}", id, accountReqDTO);

        var account = accountRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(String.format("Account with id %s not found", id)));
        account.setType(accountReqDTO.payload().getType());

        var accountUpdated = accountRepository.save(account);

        LOGGER.info("Account updated: {}", accountUpdated.getIde());

        return GeneralUtil.buildGenericSuccessResp(account.getIde().toString(), "Account updated success");
    }

    @Override
    @Transactional
    public GenericRespDTO<String> deleteAccount(Integer id) throws GenericException {

        LOGGER.info("Id Account: {}", id);

        if (!accountRepository.existsById(id)) {
            throw new NotFoundException(String.format("Account %s not exists", id));
        }

        accountRepository.deleteById(id);

        return GeneralUtil.buildGenericSuccessResp("", "Account deleted success");
    }


}
