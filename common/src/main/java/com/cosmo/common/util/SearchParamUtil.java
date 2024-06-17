package com.cosmo.common.util;

import com.cosmo.common.model.SearchParam;

import java.time.Instant;
import java.util.Date;

public class SearchParamUtil {

    public static String getString(SearchParam searchParam, String keyName) {
        return (String) searchParam.getParam().get(keyName);
    }

    public static Date getDate(SearchParam searchParam, String keyName, String dateFormat) {
        String stringDate = getString(searchParam, keyName);
        if (stringDate != null && !stringDate.isBlank()) {
            Instant instant = Instant.parse(stringDate);

            Date date = Date.from(instant);

            return DateUtility.getDateFromDate(date, dateFormat);
        }
        return null;
    }
}
