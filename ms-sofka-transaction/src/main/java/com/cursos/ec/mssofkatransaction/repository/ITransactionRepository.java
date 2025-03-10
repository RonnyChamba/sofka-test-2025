package com.cursos.ec.mssofkatransaction.repository;

import com.cursos.ec.mssofkatransaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ITransactionRepository extends JpaRepository<Transaction, Integer> {


    @Query("SELECT t FROM Transaction t " +
            "WHERE t.account.ide = :accountId " +
            "AND   DATE(t.createdAt) BETWEEN DATE(:dateFrom) AND DATE(:dateTo) " +
            "ORDER BY t.createdAt DESC")
    List<Transaction> findAllSomeFilters(
            @Param("dateFrom") Date dateFrom,
            @Param("dateTo") Date dateTo,
            @Param("accountId") Integer accountId);
}
