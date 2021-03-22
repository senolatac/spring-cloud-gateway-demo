package com.example.gatewayredis.redis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

/**
 * @author sa
 * @date 22.03.2021
 * @time 15:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "ApiKey", timeToLive = 86400) //1 day
public class CreditRedis
{
    @Id
    private String id;//apiKey
    private Integer remainingQuote;
}
