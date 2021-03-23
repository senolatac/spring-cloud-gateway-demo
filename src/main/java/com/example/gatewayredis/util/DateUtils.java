package com.example.gatewayredis.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @author sa
 * @date 23.03.2021
 * @time 11:06
 */
public class DateUtils
{
    public static Long localDateTimeToSeconds(LocalDateTime time)
    {
        return time.toEpochSecond(ZoneOffset.UTC);
    }

    public static boolean isUpToDate(Long expirationTime)
    {
        Long now = localDateTimeToSeconds(LocalDateTime.now());
        return expirationTime.compareTo(now) > 0;
    }

    public static Long currentExpirationTime(Long expirationTime, Long period)
    {
        Long now = localDateTimeToSeconds(LocalDateTime.now());
        //Expiration-time should be in future
        while (expirationTime.compareTo(now) < 0)
        {
            expirationTime = expirationTime + period;
        }
        return expirationTime;
    }
}
