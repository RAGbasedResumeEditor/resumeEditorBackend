package com.team2.resumeeditorproject.common.util;

import java.sql.Date;
import java.time.LocalDate;

public class DateUtils {
    public static Date toSqlDate(LocalDate localDate) {
        return Date.valueOf(localDate);
    }
}
