package com.cursos.ec.mssofkatransaction.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static Date convertToDate(String currentDate, DateFormatEnum formatDateEnum) {
        try {
            return currentDate == null ? null : (new SimpleDateFormat(formatDateEnum.getFormat())).parse(currentDate);
        } catch (Exception var3) {
            return null;
        }
    }

}
