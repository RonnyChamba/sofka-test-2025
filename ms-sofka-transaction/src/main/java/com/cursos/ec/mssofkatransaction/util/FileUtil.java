package com.cursos.ec.mssofkatransaction.util;

import feign.FeignException;

public class FileUtil {


    public static byte[] getByteArrayFromResponseBody(FeignException feignException) {
        var byteBuffer = feignException.responseBody().orElseThrow();
        byte[] byteArray = new byte[byteBuffer.remaining()];
        byteBuffer.get(byteArray);
        return byteArray;
    }
}
