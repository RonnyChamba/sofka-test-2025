package com.cursos.ec.mssofkapersona.controller;

import com.cursos.ec.mssofkapersona.exception.GenericException;
import com.cursos.ec.mssofkapersona.messages.request.CustomerReqDTO;
import com.cursos.ec.mssofkapersona.messages.request.GenericReqDTO;
import com.cursos.ec.mssofkapersona.messages.request.UpdateCustomerReqDTO;
import com.cursos.ec.mssofkapersona.messages.response.CustomerRespDTO;
import com.cursos.ec.mssofkapersona.messages.response.GenericRespDTO;
import com.cursos.ec.mssofkapersona.service.ICustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
@Tag(name = "Customer", description = "Customer management system")
public class CustomerController {

    private final ICustomerService customerService;

    @PostMapping
    @Operation(
            summary = "Save customer",
            description = "Save a new customer in the system."
    )
    public GenericRespDTO<String> saveCustomer(
            @Valid @RequestBody GenericReqDTO<CustomerReqDTO> requestDTO) throws GenericException {
        return customerService.saveCustomer(requestDTO);
    }

    @PutMapping("/{ide}")
    @Operation(
            summary = "Update customer",
            description = "Update a  customer in the system."
    )
    public GenericRespDTO<String> updateCustomer(
            @PathVariable Integer ide,
            @Valid @RequestBody GenericReqDTO<UpdateCustomerReqDTO> requestDTO) throws GenericException {
        return customerService.updateCustomer(requestDTO, ide);
    }

    @GetMapping
    @Operation(
            summary = "Get all customers",
            description = "Returns a list of all registered people in the system."
    )
    public GenericRespDTO<List<CustomerRespDTO>> findCustomers() {
        return customerService.findAllCustomer();
    }

    @Operation(
            summary = "Find customer by ID",
            description = "Retrieves a customer's details using their unique identifier."
    )
    @GetMapping("/{ide}")
    public GenericRespDTO<CustomerRespDTO> findByIdeCustomer(@PathVariable Integer ide) throws GenericException {
        return customerService.findById(ide);
    }

    @Operation(
            summary = "Delete customer by ID",
            description = "Deletes a customer record using their unique identifier."
    )

    @DeleteMapping("/{ide}")
    public GenericRespDTO<String> deleteCustomer(@PathVariable("ide") Integer ide) throws GenericException {
        return customerService.deleteCustomer(ide);
    }

}
