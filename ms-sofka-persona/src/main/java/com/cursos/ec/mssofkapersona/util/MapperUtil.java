package com.cursos.ec.mssofkapersona.util;

import com.cursos.ec.mssofkapersona.entity.Customer;
import com.cursos.ec.mssofkapersona.messages.request.UpdateCustomerReqDTO;

public class MapperUtil {


    public static void setNewValuesCustomer(Customer customer, UpdateCustomerReqDTO customerDto) {
        customer.setAddress(customerDto.getAddress());
        customer.setFullName(customerDto.getFullName());
        customer.setGender(customerDto.getGender());
        customer.setPhone(customerDto.getPhone());
        customer.setAge(customerDto.getAge());
    }
}
