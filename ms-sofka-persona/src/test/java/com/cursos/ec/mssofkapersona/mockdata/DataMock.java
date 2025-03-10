package com.cursos.ec.mssofkapersona.mockdata;


import com.cursos.ec.mssofkapersona.entity.Customer;
import com.cursos.ec.mssofkapersona.messages.request.CustomerReqDTO;

public class DataMock {

    public static CustomerReqDTO customerReqDTO = new CustomerReqDTO();
    public static Customer customer = new Customer();

    static {
        customerReqDTO.setFullName("Juan Pérez");
        customerReqDTO.setGender("M");
        customerReqDTO.setAge(30);
        customerReqDTO.setIdentification("1234567890");
        customerReqDTO.setAddress("Av. Principal 123");
        customerReqDTO.setPhone("0987654321");
        customerReqDTO.setPassword("securePassword");
        customerReqDTO.setStatus("Activo");


        customer.setPersonId(1);
        customer.setFullName(customerReqDTO.getFullName());
        customer.setGender(customerReqDTO.getGender());
        customer.setAge(customerReqDTO.getAge());
        customer.setIdentification(customerReqDTO.getIdentification());
        customer.setAddress(customerReqDTO.getAddress());
        customer.setPhone(customerReqDTO.getPhone());
        customer.setPassword(customerReqDTO.getPassword());
        customer.setStatus(customerReqDTO.getStatus());
    }


}
