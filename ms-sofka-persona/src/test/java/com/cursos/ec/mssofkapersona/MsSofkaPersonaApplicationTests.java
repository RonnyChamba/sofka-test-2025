package com.cursos.ec.mssofkapersona;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cursos.ec.mssofkapersona.messages.request.CustomerReqDTO;
import com.cursos.ec.mssofkapersona.messages.request.GenericReqDTO;
import com.cursos.ec.mssofkapersona.mockdata.DataMock;
import com.cursos.ec.mssofkapersona.repository.ICustomerRepository;
import com.cursos.ec.mssofkapersona.repository.IPersonRepository;
import com.cursos.ec.mssofkapersona.util.ConstantPersonaMsTest;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;

import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@AutoConfigureMockMvc
class MsSofkaPersonaApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ICustomerRepository customerRepository;

    @Autowired
    private IPersonRepository personRepository;

    @Container
    @ServiceConnection
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0");


    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
        personRepository.deleteAll();
        restartIncrementIdes();
    }

    void restartIncrementIdes() {
        var jdbcUrl = mysql.getJdbcUrl();
        var username = mysql.getUsername();
        var password = mysql.getPassword();

        var dataSource = new DriverManagerDataSource(jdbcUrl, username, password);
        var jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.execute("ALTER TABLE customers AUTO_INCREMENT = 1");
        jdbcTemplate.execute("ALTER TABLE people AUTO_INCREMENT = 1");
    }

    @Test
    void contextLoads() {
        assertTrue(mysql.isRunning());
    }

    @Test
    void shouldCreateAndGetCustomer() throws Exception {
        var reqCustomer = DataMock.customerReqDTO;
        var genericReq = GenericReqDTO.<CustomerReqDTO>
                        builder()
                .payload(reqCustomer)
                .origin("test")
                .ipRequest("198.123.12")
                .userRequest("rchamba")
                .build();

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(genericReq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.status").value(ConstantPersonaMsTest.STATUS_OK))
                .andExpect(jsonPath("$.message").value("Customer register"))
                .andExpect(jsonPath("$.data").value("1"));

        mockMvc.perform(get("/clientes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data[0].fullName").value(reqCustomer.getFullName()))
                .andExpect(jsonPath("$.data[0].age").value(reqCustomer.getAge()))
                .andExpect(jsonPath("$.data[0].gender").value(reqCustomer.getGender()))
                .andExpect(jsonPath("$.data[0].identification").value(reqCustomer.getIdentification()))
                .andExpect(jsonPath("$.data[0].address").value(reqCustomer.getAddress()))
                .andExpect(jsonPath("$.data[0].phone").value(reqCustomer.getPhone()))
                .andExpect(jsonPath("$.data[0].status").value(reqCustomer.getStatus()));

    }


    @Test
    void shouldCreateAndThrowExceptionDuplicated() throws Exception {

        var reqCustomer = DataMock.customerReqDTO;
        var genericReq = GenericReqDTO.<CustomerReqDTO>
                        builder()
                .payload(reqCustomer)
                .origin("test")
                .ipRequest("198.123.12")
                .userRequest("rchamba")
                .build();

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(genericReq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.status").value(ConstantPersonaMsTest.STATUS_OK))
                .andExpect(jsonPath("$.message").value("Customer register"))
                .andExpect(jsonPath("$.data").value("1"));

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(genericReq)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.status").value(ConstantPersonaMsTest.STATUS_ERROR))
                .andExpect(jsonPath("$.message").value(String.format("Dni %s already exists", reqCustomer.getIdentification())));
    }

    @Test
    void shouldGetCustomerByIde() throws Exception {

        var reqCustomer = DataMock.customerReqDTO;
        var genericReq = GenericReqDTO.<CustomerReqDTO>
                        builder()
                .payload(reqCustomer)
                .origin("test")
                .ipRequest("198.123.12")
                .userRequest("rchamba")
                .build();

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(genericReq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.status").value(ConstantPersonaMsTest.STATUS_OK))
                .andExpect(jsonPath("$.message").value("Customer register"))
                .andExpect(jsonPath("$.data").value("1"));

        mockMvc.perform(get("/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.status").value(ConstantPersonaMsTest.STATUS_OK))
                .andExpect(jsonPath("$.message").value("Customer retrieve success"))
                .andExpect(jsonPath("$.data.age").value(reqCustomer.getAge()))
                .andExpect(jsonPath("$.data.gender").value(reqCustomer.getGender()))
                .andExpect(jsonPath("$.data.identification").value(reqCustomer.getIdentification()))
                .andExpect(jsonPath("$.data.address").value(reqCustomer.getAddress()))
                .andExpect(jsonPath("$.data.phone").value(reqCustomer.getPhone()));
    }

    @Test
    void shouldDeleteByIde() throws Exception {

        var reqCustomer = DataMock.customerReqDTO;
        var genericReq = GenericReqDTO.<CustomerReqDTO>
                        builder()
                .payload(reqCustomer)
                .origin("test")
                .ipRequest("198.123.12")
                .userRequest("rchamba")
                .build();

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(genericReq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.status").value(ConstantPersonaMsTest.STATUS_OK))
                .andExpect(jsonPath("$.message").value("Customer register"))
                .andExpect(jsonPath("$.data").value("1"));

        mockMvc.perform(delete("/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.status").value(ConstantPersonaMsTest.STATUS_OK))
                .andExpect(jsonPath("$.message").value("Customer deleted"));
    }
}
