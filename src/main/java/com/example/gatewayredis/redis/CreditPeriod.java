package com.example.gatewayredis.redis;

import com.example.gatewayredis.util.DateUtils;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @author sa
 * @date 23.03.2021
 * @time 10:58
 */
@Getter
public enum CreditPeriod
{
    SECONDS_30(TimeUnit.SECONDS, 30L),
    MINUTES_30(TimeUnit.MINUTES, 30L),
    HOURS_1(TimeUnit.HOURS, 1L),
    HOURS_12(TimeUnit.HOURS, 12L),
    DAYS_1(TimeUnit.DAYS, 1L);

    private TimeUnit timeUnit;
    private Long duration;

    CreditPeriod(TimeUnit timeUnit, Long duration)
    {
        this.timeUnit = timeUnit;
        this.duration = duration;
    }

    public long seconds()
    {
        return timeUnit.toSeconds(duration);
    }

    public long expiration()
    {
        return DateUtils.localDateTimeToSeconds(LocalDateTime.now()) + seconds();
    }
}
