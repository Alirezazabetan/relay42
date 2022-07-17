package com.relay42.sensor.service.util;


import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@NoArgsConstructor
public class DateUtil {
    private static final String[] formats = new String[]{"yyyy-MM-dd", "yyyy/MM/dd", "yyyy-MM-dd hh:mm:ss",
            "yyyy-MM-dd hh:mm", "dd MMM yyyy", "dd MMM yyyy hh:mm", "dd MMM yyyy HH:mm", "dd MMM yyyy hh:mm:ss",
            "dd MMM yyyy HH:mm:ss", "MM/yy", "dd MMM yyyy, hh:mm:ss", "E MMM dd HH:mm:ss Z yyyy", "dd mmm yyyy, hh:mm a",
            "yyyy-MM-dd'T'hh:mm:ss"};

    public static Optional<Date> toDate(String dateAsString) {
        if (StringUtil.isEmpty(dateAsString))
            return null;
        Date result = null;
        for (String format : formats) {
            try {
                return Optional.of(new SimpleDateFormat(format).parse(dateAsString));
            } catch (ParseException e) {
                //DO NOTHING
            }
        }
        return Optional.empty();

    }


}