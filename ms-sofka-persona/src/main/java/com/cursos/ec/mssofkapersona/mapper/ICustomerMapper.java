package com.cursos.ec.mssofkapersona.mapper;

import com.cursos.ec.mssofkapersona.entity.Customer;
import com.cursos.ec.mssofkapersona.messages.request.CustomerReqDTO;
import com.cursos.ec.mssofkapersona.messages.response.CustomerRespDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ICustomerMapper {

    ICustomerMapper INSTANCE = Mappers.getMapper(ICustomerMapper.class);

    Customer toEntity(CustomerReqDTO customerReqDTO);

    CustomerRespDTO toDTO(Customer customer);

    List<CustomerRespDTO> toListDTO(List<Customer> customers);
}

