package com.cursos.ec.mssofkatransaction.repository;

import com.cursos.ec.mssofkatransaction.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IAccountRepository extends JpaRepository<Account, Integer> {

    boolean existsByNumberAndStatusRecord(String accountNumber, String statusRecord);

    List<Account> findAllByStatusRecord(String statusRecord);

    Optional<Account> findByNumber(String number);

    List<Account> findAllByCustomerId(Integer customerId);
}
