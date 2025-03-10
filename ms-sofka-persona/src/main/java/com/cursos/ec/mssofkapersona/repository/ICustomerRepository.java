package com.cursos.ec.mssofkapersona.repository;

import com.cursos.ec.mssofkapersona.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ICustomerRepository extends JpaRepository<Customer, Integer> {

    boolean existsByIdentification(String identification);

    boolean existsByIdentificationAndStatusRecord(String identification, String statusRecord);

    Optional<Customer> findByPersonIdAndStatusRecord(Integer personId, String statusRecord);

    Optional<Customer> findByIdentificationAndStatusRecord(String identification, String statusRecord);


    List<Customer> findAllByStatusRecord(String statusRecord);
}
