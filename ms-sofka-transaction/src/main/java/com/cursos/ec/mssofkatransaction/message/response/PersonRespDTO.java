package com.cursos.ec.mssofkatransaction.message.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class PersonRespDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private String fullName;
    private Integer age;
    private String gender;
    private String identification;
    private String address;
    private String phone;
}