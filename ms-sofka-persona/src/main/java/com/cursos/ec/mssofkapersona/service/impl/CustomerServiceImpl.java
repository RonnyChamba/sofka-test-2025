package com.cursos.ec.mssofkapersona.service.impl;

import com.cursos.ec.mssofkapersona.exception.BadRequestException;
import com.cursos.ec.mssofkapersona.exception.DuplicateResourceException;
import com.cursos.ec.mssofkapersona.exception.GenericException;
import com.cursos.ec.mssofkapersona.exception.NotFoundException;
import com.cursos.ec.mssofkapersona.mapper.ICustomerMapper;
import com.cursos.ec.mssofkapersona.messages.request.CustomerReqDTO;
import com.cursos.ec.mssofkapersona.messages.request.GenericReqDTO;
import com.cursos.ec.mssofkapersona.messages.request.UpdateCustomerReqDTO;
import com.cursos.ec.mssofkapersona.messages.response.CustomerRespDTO;
import com.cursos.ec.mssofkapersona.messages.response.GenericRespDTO;
import com.cursos.ec.mssofkapersona.repository.ICustomerRepository;
import com.cursos.ec.mssofkapersona.service.ICustomerService;
import com.cursos.ec.mssofkapersona.util.GeneralUtil;
import com.cursos.ec.mssofkapersona.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements ICustomerService {


    private final ICustomerRepository customerRepository;
    private static final Logger LOGGER = LogManager.getLogger(CustomerServiceImpl.class);


    @Override
    @Transactional(rollbackFor = Exception.class)
    public GenericRespDTO<String> saveCustomer(GenericReqDTO<CustomerReqDTO> requestDTO) throws GenericException {

        LOGGER.info("Request saveCustomer: {}", requestDTO);

        var dataToPersist = requestDTO.payload();

        if (customerRepository.existsByIdentificationAndStatusRecord(dataToPersist.getIdentification(), "Activo")) {
            throw new DuplicateResourceException(String.format("Dni %s already exists", dataToPersist.getIdentification()));
        }

        var customerToPersist = ICustomerMapper.INSTANCE.toEntity(dataToPersist);
        customerToPersist.setStatusRecord("Activo");
        customerToPersist = customerRepository.save(customerToPersist);

        LOGGER.info("Entity save success, ide: {}", customerToPersist.getPersonId());
        return GeneralUtil.buildGenericSuccessResp(customerToPersist.getPersonId().toString(), "Customer register");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public GenericRespDTO<String> deleteCustomer(Integer ide) throws GenericException {

        LOGGER.info("Delete customer: {}", ide);

        var customerToDelete = customerRepository.findByPersonIdAndStatusRecord(ide, "Activo")
                .orElseThrow(() -> new NotFoundException(String.format("Customer with ide %s not exists", ide)));

        customerToDelete.setStatusRecord("Eliminado");
        customerToDelete = customerRepository.save(customerToDelete);
        return GeneralUtil.buildGenericSuccessResp("", "Customer deleted");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public GenericRespDTO<String> updateCustomer(GenericReqDTO<UpdateCustomerReqDTO> requestDTO, Integer customerIde)
            throws GenericException {

        LOGGER.info("Request to update customer: {}", requestDTO);

        var dataToUpdate = requestDTO.payload();


        var customer = customerRepository.findByPersonIdAndStatusRecord(customerIde, "Activo")
                .orElseThrow(() -> new NotFoundException("Customer not found with id: " + customerIde));
        MapperUtil.setNewValuesCustomer(customer, dataToUpdate);

        customer = customerRepository.save(customer);

        return GeneralUtil.buildGenericSuccessResp(customer.getPersonId().toString(), "Customer updated success");
    }

    @Override
    @Transactional(readOnly = true)
    public GenericRespDTO<List<CustomerRespDTO>> findAllCustomer() {

        LOGGER.info("List Customers");

        var listCustomer = customerRepository.findAllByStatusRecord("Activo");

        LOGGER.info("Size customer: {}", listCustomer.size());

        var listDTOCustomer = ICustomerMapper.INSTANCE.toListDTO(listCustomer);

        return GeneralUtil.buildGenericSuccessResp(listDTOCustomer,
                listDTOCustomer.isEmpty() ? "no results found" : "Successfully obtained clients");
    }

    @Override
    @Transactional(readOnly = true)
    public GenericRespDTO<CustomerRespDTO> findById(Integer customerIde) throws GenericException {

        LOGGER.info("Req findById : {}", customerIde);

        if (Objects.isNull(customerIde)) {
            throw new BadRequestException("Param Id is required");
        }

        var customerDTO = customerRepository.findByPersonIdAndStatusRecord(customerIde, "Activo")
                .map(ICustomerMapper.INSTANCE::toDTO)
                .orElseThrow(() -> new NotFoundException(String.format("Customer with ide %s no exists", customerIde)));

        return GeneralUtil.buildGenericSuccessResp(customerDTO, "Customer retrieve success");
    }

}
