package com.cursos.ec.mssofkatransaction.service;

import com.cursos.ec.mssofkatransaction.message.response.CustomerRespDTO;
import com.cursos.ec.mssofkatransaction.message.response.GenericRespDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-person", url = "${feign.client.config.ms-person.url}/clientes")
public interface IPersonFeignClient {

    @GetMapping("/{ide}")
    GenericRespDTO<CustomerRespDTO> findCustomerByIde(@PathVariable Integer ide);
}
