package com.cosmo.common.util;

import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class DateUtility {

    public static String convertDateToString(Date date, String dateFormat) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
            return simpleDateFormat.format(date);
        } catch (Exception e) {
            log.error("Exception :: " + e.getMessage());
            return null;
        }
    }

    public static String utilDateToSqlDateInString(Date uDate) {
        try {
            DateFormat sqlDateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            return java.sql.Date.valueOf(sqlDateFormatter.format(uDate)).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static Date getDateFromDate(Date date, String pattern) {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            String format = sdf.format(date);
            return sdf.parse(format);
        } catch (ParseException e) {
            log.error("Exception :: " + e.getMessage());
            return null;
        }
    }
}
