package com.cursos.ec.mssofkapersona.service;


import com.cursos.ec.mssofkapersona.exception.GenericException;
import com.cursos.ec.mssofkapersona.messages.request.CustomerReqDTO;
import com.cursos.ec.mssofkapersona.messages.request.GenericReqDTO;
import com.cursos.ec.mssofkapersona.messages.request.UpdateCustomerReqDTO;
import com.cursos.ec.mssofkapersona.messages.response.CustomerRespDTO;
import com.cursos.ec.mssofkapersona.messages.response.GenericRespDTO;

import java.util.List;

public interface ICustomerService {

    GenericRespDTO<String> saveCustomer(GenericReqDTO<CustomerReqDTO> requestDTO) throws GenericException;

    GenericRespDTO<String> deleteCustomer(Integer ide) throws GenericException;

    GenericRespDTO<String> updateCustomer(GenericReqDTO<UpdateCustomerReqDTO> requestDTO, Integer customerIde) throws GenericException;

    GenericRespDTO<List<CustomerRespDTO>> findAllCustomer();

    GenericRespDTO<CustomerRespDTO> findById(Integer customerIde) throws GenericException;

}
