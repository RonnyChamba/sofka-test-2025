package com.cursos.ec.mssofkatransaction.util;

public enum DateFormatEnum {
    DATE_TIME_FORMAT("yyyy-MM-dd hh:mm:ss"),
    DATE_FORMAT("yyyy-MM-dd"),
    TIME_FORMAT("hh:mm:ss");

    private String format;

    private DateFormatEnum(String format) {
        this.format = format;
    }

    public String getFormat() {
        return this.format;
    }
}
