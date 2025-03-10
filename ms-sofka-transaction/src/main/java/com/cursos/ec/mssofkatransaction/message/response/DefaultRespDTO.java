package com.cursos.ec.mssofkatransaction.message.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class DefaultRespDTO implements Serializable {

    private String message;
    private String code;
    private String status;
    private Object data;
}
