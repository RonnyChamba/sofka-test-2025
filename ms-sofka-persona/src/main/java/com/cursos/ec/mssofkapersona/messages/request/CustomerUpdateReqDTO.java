package com.cursos.ec.mssofkapersona.messages.request;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerUpdateReqDTO {
    private String identification;
    private UpdateCustomerReqDTO dataToUpdate;
}
