package br.com.test.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by Eduardo on 11/06/2017.
 */
public class DateUtil {

    public static Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static Date plusDays(Date date, int days) {
        return toDate(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).toLocalDate().plusDays(days));
    }

    public static LocalDate toLocalDate(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).toLocalDate();
    }


    public static Date now() {
        return toDate(LocalDate.now());
    }
}