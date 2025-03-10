package com.cursos.ec.mssofkapersona.repository;


import com.cursos.ec.mssofkapersona.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPersonRepository extends JpaRepository<Person, Integer> {
}
