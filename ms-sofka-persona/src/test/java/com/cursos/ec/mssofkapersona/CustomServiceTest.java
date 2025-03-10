package com.cursos.ec.mssofkapersona;

import com.cursos.ec.mssofkapersona.entity.Customer;
import com.cursos.ec.mssofkapersona.exception.BadRequestException;
import com.cursos.ec.mssofkapersona.exception.DuplicateResourceException;
import com.cursos.ec.mssofkapersona.exception.GenericException;
import com.cursos.ec.mssofkapersona.exception.NotFoundException;
import com.cursos.ec.mssofkapersona.messages.request.CustomerReqDTO;
import com.cursos.ec.mssofkapersona.messages.request.GenericReqDTO;
import com.cursos.ec.mssofkapersona.mockdata.DataMock;
import com.cursos.ec.mssofkapersona.repository.ICustomerRepository;
import com.cursos.ec.mssofkapersona.service.impl.CustomerServiceImpl;
import com.cursos.ec.mssofkapersona.util.ConstantPersonaMsTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import static org.mockito.Mockito.*;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class CustomServiceTest {

    @Mock
    ICustomerRepository customerRepo;


    @InjectMocks
    CustomerServiceImpl customerService;


    @DisplayName("Test to check if a customer is saved successfully")
    @Test
    void testSaveCustomer() {
        when(customerRepo.existsByIdentification(DataMock.customerReqDTO.getIdentification())).thenReturn(false);
        when(customerRepo.save(any(Customer.class))).thenReturn(DataMock.customer);
        try {

            var genericResp = customerService.saveCustomer(GenericReqDTO.<CustomerReqDTO>builder()
                    .payload(DataMock.customerReqDTO)
                    .build());

            assertEquals(ConstantPersonaMsTest.STATUS_OK, genericResp.status());
            assertEquals(200, genericResp.code());
            assertEquals(DataMock.customer.getPersonId().toString(), genericResp.data());

        } catch (GenericException ex) {
            fail("Fail test");
        }
    }

    @DisplayName("Test testExistCustomer() - should return false if customer does not exist in the database")
    @Test
    void testExistCustomer() {

        when(customerRepo.existsByIdentification(DataMock.customerReqDTO.getIdentification())).thenReturn(true);

        var duplicateException = assertThrows(DuplicateResourceException.class, () -> customerService.saveCustomer(GenericReqDTO.<CustomerReqDTO>
                        builder()
                .payload(DataMock.customerReqDTO)
                .build()));

        assertEquals(String.format("Dni %s already exists", DataMock.customerReqDTO.getIdentification()), duplicateException.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST.value(), duplicateException.getErrorCode());
    }

    @Test
    void testDeleteCustomer_success() throws GenericException {

        when(customerRepo.findByPersonIdAndStatusRecord(DataMock.customer.getPersonId(), "Activo"))
                .thenReturn(Optional.of(DataMock.customer));

        var response = customerService.deleteCustomer(DataMock.customer.getPersonId());

        verify(customerRepo).save(DataMock.customer);
        assertEquals("Customer deleted", response.message());
        assertEquals(200, response.code());
        assertEquals("", response.data());
        assertEquals("Eliminado", DataMock.customer.getStatusRecord());
    }


    @Test
    void testFindById_success() throws GenericException {

        when(customerRepo.findById(DataMock.customer.getPersonId())).thenReturn(Optional.of(DataMock.customer));

        var response = customerService.findById(DataMock.customer.getPersonId());

        // Assert: verificar que la respuesta es la esperada
        assertNotNull(response);
        assertEquals(200, response.code());
        assertEquals("Customer retrieve success", response.message());
        assertEquals(DataMock.customerReqDTO.getIdentification(), response.data().getIdentification());
    }

    @Test
    void testFindById_customerNotFound() {

        when(customerRepo.findById(DataMock.customer.getPersonId())).thenReturn(Optional.empty());

        var exception = assertThrows(NotFoundException.class, () -> {
            customerService.findById(DataMock.customer.getPersonId());
        });

        assertEquals("Customer with ide 1 no exists", exception.getMessage());
    }

    @Test
    void testFindById_invalidId() {

        var exception = assertThrows(BadRequestException.class, () -> {
            customerService.findById(null);
        });

        assertEquals("Param Id is required", exception.getMessage());
    }


}
