package com.cursos.ec.mssofkapersona.messages.response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRespDTO extends PersonRespDTO {

    private String status;
    private Integer personId;
}
